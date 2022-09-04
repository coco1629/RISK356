package Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerHandler extends Thread {

    private Socket socket;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public ServerHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        System.out.println("new connection accept" + socket.getInetAddress());
        String message = "from server";
//        while(!message.equals("END")){
        try {
            message =(String) objectInputStream.readObject();
            System.out.println("server receive"+ message);
            String send = "server: send to client";
            objectOutputStream.writeObject(send);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void sendObject(Object object) {
        try {
            this.objectOutputStream.writeObject(object);
        } catch (IOException ex) {
            System.out.println("Error Occurred in sendObject in ClientHandler: " + ex.toString());
        }
    }

    private Object readObject() {
        try {
            return this.objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error Occurred in readObject in ClientHandler: " + ex.toString());
        }
        return null;
    }




}
