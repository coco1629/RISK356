package Connection;

import Model.Continent;
import Model.Country;
import Model.Territory;
import Model.currentProcess;
import View.SvgUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class ServerThread extends Thread{
    Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private ArrayList<ServerThread> serverThreads;
    private ArrayList<Territory> territories = new ArrayList<>();
    private Server server;
    private HashMap<String,ServerHandler> handlerHashMap = new HashMap<>();
    private String roomName = "";
    HashMap<String, Continent> countryMap = new HashMap<>();

    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        this.objectInputStream =new ObjectInputStream(socket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//        for (Country country : Country.values()){
//           territories.add(new Territory(country.getName(),null,country.getPopulation()));
//        }
    }

    @Override
    public void run() {
        while (true){
            try {
                Operation operation = (Operation) objectInputStream.readObject();
                switch (operation){
                    case OCCUPY :
                        String name = (String) objectInputStream.readObject();
                        this.setName(name);
//                        System.out.println("serverthread name" + this.getName() + "player name" + name);
                        ArrayList<Country> countries = new ArrayList<>();
                        int num = (int) objectInputStream.readObject();
                        for(int i = 0; i < num; i++){
                            Country country =(Country)objectInputStream.readObject();
                            int troops = (int) objectInputStream.readObject();
                            country.setPopulation(troops);
                            countries.add(country);
                        }
                        ArrayList<Territory> input = this.territories;
                        for(Country country:countries){
                            Territory territory = new Territory(country.getName(), name, country.getPopulation());
                            boolean add = true;
                            for(Territory t: this.territories){
                                if(territory.getOwner().equals(t.getOwner()) && territory.getName().equals(t.getName())){
                                    t.setNum(territory.getNum());
                                    add = false;
                                }

                            }
                            if(add)
                                input.add(territory);
                        }
                        int current = server.getUpdateplayers()+1;
                        server.setUpdateplayers(current);
                        server.updateTerritories(input,this.getName());
                        this.territories = server.getTerritories();
                        sendObjectToAll(true);
                        sendObjectToAll(territories.size());
                        for(int i = 0; i < territories.size(); i++){
                            sendObjectToAll(this.territories.get(i));
                        }
                        if(territories.size() >= 42){
                            Server.getGameModel().nextPhase();
                            server.setUpdateplayers(0);
                        }
                        sendObjectToAll(false);
                        break;
                    case ATTACK,REINFORCE:
                        ArrayList<Territory> territoryArrayList = (ArrayList<Territory>) readObject();
                        this.territories = territoryArrayList;
                        server.setTerritories(territoryArrayList);
                        sendObjectToAll(true);
                        sendObjectToAll(territories.size());
                        for(int i = 0; i < territories.size(); i++){
                            sendObjectToAll(this.territories.get(i));
                        }
                        break;
                    case FORTIFY:
                        this.territories = (ArrayList<Territory>) readObject();
                        server.setTerritories(this.territories);
                        sendObjectToAll(true);
                        sendObjectToAll(territories.size());
                        for(int i = 0; i < territories.size(); i++){
                            sendObjectToAll(this.territories.get(i));
                        }
                        break;
                    case NEXT_PHASE:
                        if(Server.getGameModel().getPhase() == currentProcess.Attack){
                            this.territories = server.getTerritories();
                            server.setUpdateplayers(server.getUpdateplayers()+1);
    //                        System.out.println("update players" + server.getUpdateplayers());
                            if(server.getUpdateplayers()==this.serverThreads.size()+1){
                                sendObjectToAll(true);
                                sendObjectToAll(territories.size());
                                for(int i = 0; i < territories.size(); i++){
                                    sendObjectToAll(this.territories.get(i));
                                }
                                sendObjectToAll(true);
    //                            System.out.println("finish update");
                                server.setUpdateplayers(0);
                                Server.getGameModel().nextPhase();
                            }
                        }
                        else {
                            if(Server.getGameModel().getPhase() == currentProcess.Fortify){
                                this.territories = server.getTerritories();
                                server.setUpdateplayers(server.getUpdateplayers()+1);
                                countryMap = (HashMap<String, Continent>) readObject();
                                sendObject(false);
                                int allocateTroops = allocateTroops(this.getName());
                                sendObject(allocateTroops);
//                                int allocateTroops = allocateTroops(this.getName());
//                                sendObject(allocateTroops);
                                if(server.getUpdateplayers()==this.serverThreads.size()+1){
                                    sendObjectToAll(true);
                                    sendObjectToAll(territories.size());
                                    for(int i = 0; i < territories.size(); i++){
                                        sendObjectToAll(this.territories.get(i));
                                    }
                                    sendObjectToAll(true);
                                    server.setUpdateplayers(0);
                                    Server.getGameModel().nextPhase();
                                }
                            }
                        }
                        break;

                    case CREATE_SESSION:
                        String string = (String) objectInputStream.readObject();
                        SessionData sessionData = new SessionData();
                        sessionData = (SessionData) objectInputStream.readObject();
                        this.setName(string);
                        ServerHandler serverThread=new ServerHandler(socket,this,sessionData);
                        serverThread.setSessionData(sessionData);
                        serverThread.fixRoomOwnerName(sessionData.getPlayer());
//                        currentServerThreads.add(serverThread);
                        this.roomName = string;
                        handlerHashMap.put(string,serverThread);
                        Server.setHandlerHashMap(handlerHashMap);
                        serverThread.start();
                        break;
                    case JOIN_SESSION:
                        String str = (String) objectInputStream.readObject();
                        String person = (String) objectInputStream.readObject();
                        this.setName(person);
                        this.handlerHashMap = Server.getHandlerHashMap();
//                        System.out.println(string+"m"+person);
                        if (handlerHashMap.containsKey(str)) {  // either the key the server sent is right or wrong!!
                            this.roomName = str;
                            handlerHashMap.get(str).addStreams(this, person);
//                            output.writeObject(handlerHashMap.get(string));
//                            System.out.println("add streams");
                            break;
                        } else {
//                            System.out.println("add failed");
                            objectOutputStream.writeObject(Operation.JOIN_SESSION_FAILED);
                        }
                        break;
                    case SHOW_ROOMS:
                        this.handlerHashMap = Server.getHandlerHashMap();
                        Set<String> set = handlerHashMap.keySet();
                        ArrayList<String> rooms = new ArrayList<>(set);
                        objectOutputStream.writeObject(rooms);
                        System.out.println("server send rooms");
                        break;

                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int allocateTroops(String name){
        int num = 0;
        int totalCountry = 0;
        int extra = 0;
        int cards = 0;

//        System.out.println(countryMap);
//        System.out.println(this.getName());
        HashMap<Continent,ArrayList<String>> playerMap = new HashMap<>();

        for(Territory territory: this.territories){
            if(territory.getOwner().equals(this.getName())){
                Continent continent = countryMap.get(territory.getName());
                if(playerMap.containsKey(continent)){
                    ArrayList<String> temp = playerMap.get(continent);
                    temp.add(territory.getName());
                    playerMap.put(continent,temp);
                }
                else {
                    ArrayList<String> countries = new ArrayList<>();
                    countries.add(territory.getName());
                    playerMap.put(continent,countries);
                }
                totalCountry++;
                System.out.println(continent.toString() + playerMap.get(continent).toString());

            }
        }
        for(Continent continent : playerMap.keySet()){
            switch (continent){
                case ASIA -> {
                    if(playerMap.get(continent).size() == 12){
                        extra += 7;
                    }
                }
                case NORTH_AMERICA-> {
                    if(playerMap.get(continent).size() == 9){
                        extra += 5;
                    }
                }
                case EUROPE -> {
                    if(playerMap.get(continent).size() == 7){
                        extra += 5;
                    }
                }
                case AFRICA -> {
                    if(playerMap.get(continent).size() == 6){
                        extra += 3;
                    }
                }
                case SOUTH_AMERICA, AUSTRALIA -> {
                    if(playerMap.get(continent).size() == 4){
                        extra += 2;
                    }
                }
            }
        }
        System.out.println("countries" + totalCountry);
        System.out.println("extra" + extra);
        num = totalCountry / 3 + extra + cards;
        return num;
    }


    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public void sendObject(Object object) {
        try {
            this.objectOutputStream.writeObject(object);
        } catch (IOException ex) {
            System.out.println("Error Occurred in sendObject in ClientHandler: " + ex.toString());
        }
    }

    public Object readObject() {
        try {
            return this.objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error Occurred in readObject in ClientHandler: " + ex.toString());
        }
        return null;
    }

    public ArrayList<ServerThread> getServerThreads() {
        return serverThreads;
    }

    public void setServerThreads(ArrayList<ServerThread> serverThreads) {
        this.serverThreads = serverThreads;
//        System.out.println(serverThreads);
//        System.out.println("set other server threads");
    }


    private void sendArrayListToAll(ArrayList<Object> objs,Operation operation){
        for (int i = 0; i < this.serverThreads.size(); i++) {
//            System.out.println(i);
            this.sendObject(Operation.UPDATE,this.serverThreads.get(i).getObjectOutputStream());
            this.sendObject(objs, this.serverThreads.get(i).getObjectOutputStream());
        }
    }

    void sendObject(Object object,ObjectOutputStream objectOutputStream) {
        try {

            objectOutputStream.writeObject(object);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Error Occurred in sendObject in ClientHandler: " + ex.toString());
        }
    }

    void sendObjectToAll(Object obj){
        sendObject(obj);
        for (int i = 0; i < this.serverThreads.size(); i++) {
            sendObject(obj,this.serverThreads.get(i).getObjectOutputStream());
        }
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
