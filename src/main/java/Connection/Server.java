package Connection;

import Model.Country;
import Model.GameModel;

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


                ServerThread thread = new ServerThread(connection);
//                SessionData sessionData = new SessionData();
//                ServerHandler serverThread=new ServerHandler(connection,sessionData);
//                ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
//                ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
                thread.start();
//                ServerHandler serverThread=new ServerHandler(connection,new SessionData());
//                serverThread.start();
//                ObjectOutputStream output = serverThread.getObjectOutputStream();
//                ObjectInputStream input = serverThread.getObjectInputStream();
                ObjectOutputStream output = thread.getObjectOutputStream();
                ObjectInputStream input = thread.getObjectInputStream();
//                System.out.println("new client");
                while(true){
                    Operation operation = (Operation) input.readObject();
                    System.out.println(operation);
                    if(operation == Operation.CREATE_SESSION){
//                        thread.stop();
                        String string = (String) input.readObject();
                        SessionData sessionData = new SessionData();
                        sessionData = (SessionData) input.readObject();
                        ServerHandler serverThread=new ServerHandler(connection,input,output,sessionData);
                        serverThread.setSessionData(sessionData);
                        serverThread.fixRoomOwnerName(sessionData.getPlayer());
//                        currentServerThreads.add(serverThread);
                        handlerHashMap.put(string,serverThread);
                        serverThread.start();
                        break;
                    }
                    if(operation == Operation.JOIN_SESSION){
//                        Set<String> set = handlerHashMap.keySet();
//                        ArrayList<String> rooms = new ArrayList<>(set);
//                        output.writeObject(rooms);
//                        System.out.println("server send rooms");
                        String string = (String) input.readObject();
                        String person = (String) input.readObject();
//                        System.out.println(string+"m"+person);
                        if (handlerHashMap.containsKey(string)) {  // either the key the server sent is right or wrong!!
                            handlerHashMap.get(string).addStreams(input, output, person);
//                            output.writeObject(handlerHashMap.get(string));
//                            System.out.println("add streams");
                            break;
                        } else {
//                            System.out.println("add failed");
                            output.writeObject(Operation.JOIN_SESSION_FAILED);
                        }
                    }
                    if(operation == Operation.SHOW_ROOMS){
                        Set<String> set = handlerHashMap.keySet();
                        ArrayList<String> rooms = new ArrayList<>(set);
                        output.writeObject(rooms);
//                        System.out.println("server send rooms");
                    }



                }
//                Operation operation = (Operation) input.readObject();
//                if(operation == Operation.OCCUPY){
//                    String string = (String) input.readObject();
//                    System.out.println("occupy!!!!!!!");
//                    Object[] obj = (Object[]) input.readObject();
//                    //                        System.out.println("occupy!!!!!!!");
//                    //                        Country country = (Country) obj[0];
//                    //                        int num = (int) obj[1];
//                    if (handlerHashMap.containsKey(string)) {
//                        handlerHashMap.get(string).occupyCountries(obj);
//                    }
//                }

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
