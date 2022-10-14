package Connection;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.Objects;

public class ClientHandler{

    public static ArrayList<ClientHandler> clientArrayList = new ArrayList<>();
    private Socket socket;
    private String username;
    private String serverIP = "127.0.0.1";
//    private String serverIP = "104.208.109.169";
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
//                sendObject("username,"+this.username);
//                readObject();
//                System.out.println(currentPlayers.size());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Exception Occurred in ClientThread Constructor: " + ex.toString());
        }
        System.out.println("connecting");

    }

    public void sendObject(Object object) {
//        System.out.println("send to server");
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


    public Object readObject() {
        try {
            return this.objectInputStream.readObject();
//            if(obj instanceof ArrayList){
//                this.currentPlayers = (ArrayList<String>) obj;
//                return;
//            }
//            else{
//                String message = (String) obj;
//                System.out.println(message);
//                String[] str = message.split(",");
//                if(str[0].equals("receive_invite")){
//                    System.out.println("add to invite");
//                    invitePlayers.add(str[2]);
//                }
//            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error Occurred in readObject in ClientHandler: " + ex.toString());
        }
        return null;
    }

    public ArrayList<Object> startListeningOrders() {
        try {
//            ArrayList<Object> data = new ArrayList<>();
//            while (true){
//                Object object = this.objectInputStream.readObject();
//                try{
//                    String obj = (String) object;
//                    if(obj.equals("End"))
//                        break;
//                }
//                catch (Exception e){
//                    data.add(object);
//                }
//            }
            ArrayList<Object> data = (ArrayList<Object>) this.objectInputStream.readObject();
            if (data == null) {
//                showError("");
                System.out.println("null");
            }
            return data;
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            ex.printStackTrace();
//            System.out.println("Error Occurred in startListeningOrders in ClientHandler: " + ex.toString());
            return null;
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

    public boolean receiveUpdated(){
        return (boolean) Objects.requireNonNull(this.readObject());
    }


}
