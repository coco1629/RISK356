package Connection;

import Model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Server {

    private ServerSocket serverSocket;
    private Socket connection;


    static HashMap<String,ServerHandler> handlerHashMap = new HashMap<>();

    public static ArrayList<String> currentClients = new ArrayList<>();

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try{

            while(true){
                connection = serverSocket.accept();


//                TempThread thread = new TempThread(connection);
                SessionData sessionData = new SessionData();
                ServerHandler serverThread=new ServerHandler(connection,sessionData);
//                ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
//                ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
//                thread.start();
//                ServerHandler serverThread=new ServerHandler(connection,new SessionData());
                serverThread.start();
                ObjectOutputStream output = serverThread.getObjectOutputStream();
                ObjectInputStream input = serverThread.getObjectInputStream();
                System.out.println("new client");
                while(true){
                    Operation operation = (Operation) input.readObject();
                    if(operation == Operation.CREATE_SESSION){
//                        thread.stop();
                        String string = (String) input.readObject();
                        sessionData = (SessionData) input.readObject();
                        serverThread.setSessionData(sessionData);
//                        currentServerThreads.add(serverThread);
                        handlerHashMap.put(string,serverThread);
//                        serverThread.start();
                        break;
                    }
                    if(operation == Operation.JOIN_SESSION){
//                        Set<String> set = handlerHashMap.keySet();
//                        ArrayList<String> rooms = new ArrayList<>(set);
//                        output.writeObject(rooms);
//                        System.out.println("server send rooms");
                        String string = (String) input.readObject();
                        String person = (String) input.readObject();
                        if (handlerHashMap.containsKey(string)) {  // either the key the server sent is right or wrong!!
                            handlerHashMap.get(string).addStreams(input, output, person);
                            System.out.println("add streams");
                            break;
                        } else {
                            output.writeObject(Operation.JOIN_SESSION_FAILED);
                        }
                    }
                    if(operation == Operation.SHOW_ROOMS){
                        Set<String> set = handlerHashMap.keySet();
                        ArrayList<String> rooms = new ArrayList<>(set);
                        output.writeObject(rooms);
                        System.out.println("server send rooms");
                    }

                }

//                setUpConnection();
//                communicate();
//                Socket socket = serverSocket.accept();
//                Client client = new Client(socket);
//                Thread thread = new Thread(client);
//                thread.start();

            }
        }catch (Exception e){
            e.printStackTrace();

        }
    }


    public void closeServer(){
        try{
        if(serverSocket != null)
            serverSocket.close();}
        catch (Exception e){

        }
    }

    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(1234);
            Server server = new Server(serverSocket);
            server.startServer();
        }
        catch (IOException e){

        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Socket getConnection() {
        return connection;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

//    public ArrayList<ServerHandler> getCurrentServerThreads() {
//        return currentServerThreads;
//    }
//
//    public void setCurrentServerThreads(ArrayList<ServerHandler> currentServerThreads) {
//        Server.currentServerThreads = currentServerThreads;
//    }
}
