package Connection;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;

public class ClientHandler{

    public static ArrayList<ClientHandler> clientArrayList = new ArrayList<>();
    private Socket socket;
    private String username;
//    private String
    private String serverIP;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    private ArrayList<String> invitePlayers = new ArrayList<>();

    private ArrayList<String> currentPlayers = new ArrayList<>();


    public ClientHandler(String name) throws IOException {
//        this.socket = socket;
        this.username = name;
        connectToServer();
        clientArrayList.add(this);
    }

//    @Override
//    public void run() {
//        readObject();
//        System.out.println("wwww");
//    }

    public void connectToServer() throws IOException {
//        System.out.println("attempting connection");
        try {
            Socket connection = new Socket(InetAddress.getByName(serverIP),1234);
            this.socket =connection;
            if (socket != null) {
                this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                this.objectInputStream = new ObjectInputStream(socket.getInputStream());
                sendObject("username,"+this.username);
                readObject();
                System.out.println(currentPlayers.size());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Exception Occurred in ClientThread Constructor: " + ex.toString());
        }
//        System.out.println("connecting");

    }

    public void sendObject(Object object) {
        System.out.println("send to server");
        try {
            this.objectOutputStream.writeObject(object);
        } catch (IOException ex) {
            System.out.println("Error Occurred in sendObject in ClientHandler: " + ex.toString());
        }
    }

    public void sendObject(Object object, Socket target) {
        try {
            ObjectOutputStream targetStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectOutputStream.writeObject(object);
        } catch (IOException ex) {
            System.out.println("Error Occurred in sendObject in ClientHandler: " + ex.toString());
        }
    }


    public void readObject() {
        try {
            Object obj = this.objectInputStream.readObject();
            if(obj instanceof ArrayList){
                this.currentPlayers = (ArrayList<String>) obj;
                return;
            }
            else{
                String message = (String) obj;
                System.out.println(message);
                String[] str = message.split(",");
                if(str[0].equals("receive_invite")){
                    System.out.println("add to invite");
                    invitePlayers.add(str[2]);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error Occurred in readObject in ClientHandler: " + ex.toString());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public ArrayList<String> getInvitePlayers() {
        return invitePlayers;
    }

    public void setInvitePlayers(ArrayList<String> invitePlayers) {
        this.invitePlayers = invitePlayers;
    }

    public ArrayList<String> getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(ArrayList<String> currentPlayers) {
        this.currentPlayers = currentPlayers;
    }
}
