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
        this.username = name;
        connectToServer();
        clientArrayList.add(this);
    }


    public void connectToServer() throws IOException {
        try {
            Socket connection = new Socket(InetAddress.getByName(serverIP),1234);
            this.socket =connection;
            if (socket != null) {
                this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            }
        } catch (IOException ex) {

        }

    }

    public void sendObject(Object object) {
        try {
            this.objectOutputStream.writeObject(object);
        } catch (IOException ex) {
        }
    }

    public void sendObject(Object object, Socket target) {
        try {
            ObjectOutputStream targetStream = new ObjectOutputStream(socket.getOutputStream());
            this.objectOutputStream.writeObject(object);
        } catch (IOException ex) {
        }
    }


    public Object readObject() {
        try {
            return this.objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
        }
        return null;
    }

    public ArrayList<Object> startListeningOrders() {
        try {
            ArrayList<Object> data = (ArrayList<Object>) this.objectInputStream.readObject();
            if (data == null) {
            }
            return data;
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {

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
