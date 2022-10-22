package group.riskgame.Application.Connection;

import group.riskgame.Application.Model.GameModel;

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

    private Server server;

    private int allowedPlayers;


    private ArrayList<String> invitePlayers = new ArrayList<>();

    private final Operation[] colors = new Operation[]{
            Operation.RED, Operation.BLUE, Operation.ORANGE, Operation.PINK, Operation.YELLOW, Operation.GRAY
    };

    private String function;

    public ServerHandler(Socket socket,ServerThread thread,SessionData sessionData) throws IOException {

        this.clientSocket = socket;
        this.people.add(sessionData.getPlayer());
        serverThreads.add(thread);
        ObjectInputStream objectInputStream = thread.getObjectInputStream();
        ObjectOutputStream objectOutputStream = thread.getObjectOutputStream();
        this.objectOutputStreams.add(objectOutputStream);
        this.objectInputStreams.add(objectInputStream);
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
            this.people.add(person);
            if (this.currentCount == (this.sessionData.getNumberOfAllowedPlayers() - 1)) {
                Server.getGameModel().setPlayerList(people);
                this.setAllPlayersThreads();
                this.sendAllPlayersNamesSymbols();
                switch (people.size()) {
                    case 3 -> this.sendObjectToAll(35);
                    case 4 -> this.sendObjectToAll(30);
                    case 5 -> this.sendObjectToAll(25);
                    case 6 -> this.sendObjectToAll(20);
                }

            }
            this.currentCount+=1;
            return;
        }
        this.sendObject(Operation.JOIN_SESSION_FAILED, this.objectOutputStreams.get(this.currentCount));
    }

    @Override
    public void run() {

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

        }
    }

    private Object readObject(int currentActivePlayer) {
        try {
            return this.objectInputStreams.get(currentActivePlayer).readObject();
        } catch (IOException | ClassNotFoundException ex) {

        }
        return null;
    }


    private void sendAllPlayersNamesSymbols() {
        ArrayList<Object> finalArray = new ArrayList<>();
        for (int i = 0; i < this.sessionData.getNumberOfAllowedPlayers(); i++) {
            Object[] data = new Object[]{this.colors[i], people.get(i)};
            finalArray.add(data);
        }
        this.sendArrayListToAll(finalArray);

    }

    public void sendObjectToAll(Object object) {
        for (int i = 0; i < this.sessionData.getNumberOfAllowedPlayers(); i++) {
            this.sendObject(object, this.objectOutputStreams.get(i));
        }

    }


    public void sendArrayListToAll(ArrayList<Object> objs){
        for (int i = 0; i < this.sessionData.getNumberOfAllowedPlayers(); i++) {
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
