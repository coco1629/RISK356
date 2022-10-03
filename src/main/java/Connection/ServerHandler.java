package Connection;

import Model.Country;
import Model.GameModel;
import Model.Territory;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerHandler extends Thread {

    private String clientName = "";

    private Socket clientSocket;

    private int currentCount = 0;

    private int currentPlayer = 0;

    public static ArrayList<ServerHandler> currentServerThreads = new ArrayList<>();

    private final ArrayList<ObjectOutputStream> objectOutputStreams = new ArrayList<>();
    private final ArrayList<ObjectInputStream> objectInputStreams = new ArrayList<>();
    private ArrayList<ServerThread> serverThreads = new ArrayList<>();
    private GameModel gameModel;


    private final ArrayList<String> people = new ArrayList<>();

    private SessionData sessionData;

    private int allowedPlayers;


    private ArrayList<String> invitePlayers = new ArrayList<>();

    private final Operation[] colors = new Operation[]{
            Operation.RED, Operation.BLUE, Operation.ORANGE, Operation.PINK, Operation.YELLOW, Operation.GRAY
    };

    private String function;

    public ServerHandler(Socket socket,ServerThread thread,SessionData sessionData) throws IOException {
//        this.clientName = name;
        this.clientSocket = socket;
        this.people.add(sessionData.getPlayer());
        serverThreads.add(thread);
        ObjectInputStream objectInputStream = thread.getObjectInputStream();
        ObjectOutputStream objectOutputStream = thread.getObjectOutputStream();
        this.objectOutputStreams.add(objectOutputStream);
        this.objectInputStreams.add(objectInputStream);
//        this.objectOutputStreams.add(new ObjectOutputStream(socket.getOutputStream()));
//        this.objectInputStreams.add(new ObjectInputStream(socket.getInputStream()));
        currentServerThreads.add(this);
        this.sessionData =sessionData;
        this.currentCount += 1;
        this.currentPlayer = this.people.size() - 1;
    }

    public void addStreams(ServerThread thread, String person) {

        ObjectInputStream objectInputStream = thread.getObjectInputStream();
        ObjectOutputStream objectOutputStream = thread.getObjectOutputStream();
        if (this.currentCount < this.sessionData.getNumberOfAllowedPlayers()) {
            this.objectOutputStreams.add(objectOutputStream);
            this.objectInputStreams.add(objectInputStream);
            serverThreads.add(thread);
//            this.sendObject(new Object[]{Operation.JOIN_SESSION_SUCCESS}, this.objectOutputStreams.get(this.currentCount));
            this.people.add(person);
            if (this.currentCount == (this.sessionData.getNumberOfAllowedPlayers() - 1)) {
//                System.out.println(this.currentCount);
                this.gameModel = new GameModel(people);
                this.setAllPlayersThreads();
                this.sendAllPlayersNamesSymbols();

            }
            this.currentCount+=1;
            return;
        }
        this.sendObject(Operation.JOIN_SESSION_FAILED, this.objectOutputStreams.get(this.currentCount));
    }

    @Override
    public void run() {
//        while (true){
//            for(int i = 0; i < this.objectInputStreams.size();i++){
//                try {
//                    Object obj = this.objectInputStreams.get(i).readObject();
//                    System.out.println(obj);
//                    break;
//
//                } catch (IOException | ClassNotFoundException e) {
////                    throw new RuntimeException(e);
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    void setAllPlayersThreads(){

        for(ServerThread serverThread : serverThreads){
            ArrayList<ServerThread> otherPlayers = new ArrayList<>(serverThreads);
            otherPlayers.remove(serverThread);
            serverThread.setServerThreads(otherPlayers);
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

    private Object readObject(int currentActivePlayer) {
        try {
            return this.objectInputStreams.get(currentActivePlayer).readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error Occurred in readObject in ClientHandler: " + ex.toString());
        }
        return null;
    }


    private void sendAllPlayersNamesSymbols() {
        ArrayList<Object> finalArray = new ArrayList<>();
        for (int i = 0; i < this.sessionData.getNumberOfAllowedPlayers(); i++) {
//            System.out.println("allowed players: "+i);
            Object[] data = new Object[]{this.colors[i], people.get(i)};
//            System.out.println(this.colors[i].name());
//            System.out.println(people.get(i));
            finalArray.add(data);
        }
        this.sendArrayListToAll(finalArray);
//        this.sendObjectToAll(finalArray);
//        this.listenUpdate(this.currentActivePlayer);
    }

    private void sendObjectToAll(Object object) {
        for (int i = 0; i < this.sessionData.getNumberOfAllowedPlayers(); i++) {
            this.sendObject(object, this.objectOutputStreams.get(i));
        }
//        for(int i = 0; i < this.sessionData.getNumberOfAllowedPlayers(); i++){
//            this.sendObject("End", this.objectOutputStreams.get(i));
//        }

    }

    private void listenUpdate(int currentActivePlayer) {

    }


    private void sendArrayListToAll(ArrayList<Object> objs){
        for (int i = 0; i < this.sessionData.getNumberOfAllowedPlayers(); i++) {
//            System.out.println(i);
            this.sendObject(objs, this.objectOutputStreams.get(i));
        }
    }


    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public ArrayList<String> getInvitePlayers() {
        return invitePlayers;
    }

    public void setInvitePlayers(ArrayList<String> invitePlayers) {
        this.invitePlayers = invitePlayers;
    }

    public SessionData getSessionData() {
        return sessionData;
    }

    public void setSessionData(SessionData sessionData) {
        this.sessionData = sessionData;
    }

    public ObjectInputStream getObjectInputStream(){
        return this.objectInputStreams.get(currentPlayer);
    }

    public ObjectOutputStream getObjectOutputStream(){
        return this.objectOutputStreams.get(currentPlayer);
    }

    public void fixRoomOwnerName(String str){
        people.set(0,str);

    }

    public void createGameBoard(){

    }






}
