package Connection;

import Model.Player;

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

    private final ArrayList<String> people = new ArrayList<>();

    private SessionData sessionData;

    private ArrayList<String> invitePlayers = new ArrayList<>();

    private final Operation[] colors = new Operation[]{
            Operation.RED, Operation.BLUE, Operation.ORANGE, Operation.PINK, Operation.YELLOW, Operation.GRAY
    };

    private String function;

    public ServerHandler(Socket socket,SessionData sessionData) throws IOException {
//        this.clientName = name;
        this.clientSocket = socket;
        this.people.add(sessionData.getPlayer());
        this.objectOutputStreams.add(new ObjectOutputStream(socket.getOutputStream()));
        this.objectInputStreams.add(new ObjectInputStream(socket.getInputStream()));
        currentServerThreads.add(this);
        this.sessionData =sessionData;
        this.currentCount += 1;
        this.currentPlayer = this.people.size() - 1;
    }

    public void addStreams(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, String person) {
        if (this.currentCount < this.sessionData.getNumberOfAllowedPlayers()) {
            this.objectOutputStreams.add(objectOutputStream);
            this.objectInputStreams.add(objectInputStream);
            this.sendObject(new Object[]{Operation.JOIN_SESSION_SUCCESS}, this.objectOutputStreams.get(this.currentCount));
            this.people.add(person);
            if (this.currentCount == (this.sessionData.getNumberOfAllowedPlayers() - 1)) {
                this.sendAllPlayersNamesSymbols();
            }
            this.currentCount += 1;
            return;
        }
        this.sendObject(Operation.JOIN_SESSION_FAILED, this.objectOutputStreams.get(this.currentCount));
    }

    @Override
    public void run() {
//        while (true){
//        String message = (String) readObject();
//        String[] str = message.split(",");
//        System.out.println(message);
//        if(str[0].equals("username")){
//            clientName = str[1];
//            ArrayList<String> players = new ArrayList<>();
//            for(ServerHandler serverHandler:currentServerThreads){
//                players.add(serverHandler.getClientName());
//            }
//            sendObject(players);
//        }
//        if(str[0].equals("send_Invite")){
////////                Socket target =(Socket) objectInputStream.readObject();
//            for(ServerHandler serverHandler:currentServerThreads){
//                System.out.println(serverHandler.getClientName());
//                if(serverHandler.getClientName().equals(str[1])){
//                    System.out.println("send to the inviter");
//                    serverHandler.sendObject("receive_invite,"+str[1]+","+clientName);
//                }
//            }
////            sendObject("receive_invite,"+str[1]+","+clientName);
//        }
//        if(str[0].equals("receive_invite")){
//            System.out.println("receive");
//            invitePlayers.add(str[2]);
//        }
//        if(str[0].equals("request_current_players")){
//            System.out.println("update!!!");
//            ArrayList<String> players = new ArrayList<>();
//            for(ServerHandler serverHandler:currentServerThreads){
//                players.add(serverHandler.getClientName());
//            }
//            sendObject(players);
//        }
////            sendObject("",Server.currentServerThreads,);
//
//        }



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
            Object[] data = new Object[]{this.colors[i], people.get(i)};
            finalArray.add(data);
        }
        this.sendObjectToAll(finalArray);
//        this.sendMovePermissions(this.currentActivePlayer);
    }

    private void sendObjectToAll(Object object) {
        for (int i = 0; i < this.sessionData.getNumberOfAllowedPlayers(); i++) {
            this.sendObject(object, this.objectOutputStreams.get(i));
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



}
