package Connection;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ClientHandler {

    public static ArrayList<ClientHandler> clientArrayList = new ArrayList<>();
    private Socket socket;
    private String username;
//    private String
    private String serverIP;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;


    public ClientHandler() throws IOException {
//        this.socket = socket;
        try {
            Socket connection = new Socket(InetAddress.getByName(serverIP),1234);
            this.socket =connection;
            if (socket != null) {

                    this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    this.objectInputStream = new ObjectInputStream(socket.getInputStream());

                return;
            }
        } catch (IOException ex) {
            System.out.println("Exception Occurred in ClientThread Constructor: " + ex.toString());
        }
        clientArrayList.add(this);
    }


    public void connectToServer() throws IOException {
//        System.out.println("attempting connection");
//        Socket connection = new Socket(InetAddress.getByName(serverIP),1234);
//        System.out.println("connecting");

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
