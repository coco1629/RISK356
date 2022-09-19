package Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerHandler extends Thread {

    private String clientName = "";

    private Socket clientSocket;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    private ArrayList<String> invitePlayers = new ArrayList<>();

    private String function;

    public ServerHandler(Socket socket) throws IOException {
//        this.clientName = name;
        this.clientSocket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        System.out.println("new connection accept" + clientSocket.getInetAddress());
//        String message = "from server";
//        while(!message.equals("END")){
//        try {
////            if(function.equals("send_invite")){
        String message = (String) readObject();
        String[] str = message.split(",");
        if(str[0].equals("username")){
            clientName = str[1];
        }
        if(str[0].equals("send_invite")){
//////                Socket target =(Socket) objectInputStream.readObject();
            sendObject("receive_invite,"+str[1]+","+clientName);
        }
        if(str[0].equals("receive_invite")){
            invitePlayers.add(str[2]);
        }

////            if(message.equals("receive_invite")){
////            System.out.println("receive invite!!!!!");
////                UserDao dao = new UserDao();
////                ArrayList<Player> players = new ArrayList<>();
////            }
////            System.out.println("server receive"+ message);
////            String send = "server: send to client";
////            objectOutputStream.writeObject(send);
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    private void sendObject(Object object) {
        try {
            ObjectOutputStream targetStream = this.objectOutputStream;
            String message = (String)object;
            String[] str = message.split(",");
            for(ServerHandler serverHandler: Server.currentServerThreads){
                if(serverHandler.clientName.equals(str[1])){
//                    if(str[])
                    System.out.println("target stream"+str[1]);
                    targetStream = serverHandler.objectOutputStream;
                    break;
                }
            }
//            ObjectOutputStream targetStream = new ObjectOutputStream(target.getOutputStream());
            targetStream.writeObject(object);
        } catch (IOException ex) {
            ex.printStackTrace();
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
}
