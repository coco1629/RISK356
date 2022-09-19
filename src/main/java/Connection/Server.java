package Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {


    private ServerSocket serverSocket;
    private Socket connection;

    ObjectOutputStream output;
    ObjectInputStream input;

    public static ArrayList<ServerHandler> currentServerThreads = new ArrayList<>();

    public static ArrayList<String> currentClients = new ArrayList<>();

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try{

            while(true){
                waitForConnection();
//                setUpConnection();
//                communicate();
//                Socket socket = serverSocket.accept();
                System.out.println("new client");
//                Client client = new Client(socket);
//                Thread thread = new Thread(client);
//                thread.start();

            }
        }catch (Exception e){
            e.printStackTrace();

        }
    }

//    private void communicate() throws IOException, ClassNotFoundException {
//        String message = "from server";
//        while(!message.equals("END")){
//            message = (String) input.readObject();
//            String[] str = message.split(",");
//            System.out.println(message);
////            if(str[0].equals("username")){
////                clientName = str[1];
////                ArrayList<String> players = new ArrayList<>();
////                for(ServerHandler serverHandler:Server.currentServerThreads){
////                    players.add(serverHandler.getClientName());
////                }
////                sendObject(players);
////            }
////            if(str[0].equals("send_invite")){
////////                Socket target =(Socket) objectInputStream.readObject();
////                input.sendObject("receive_invite,"+str[1]+","+clientName);
////            }
////            if(str[0].equals("receive_invite")){
////                invitePlayers.add(str[2]);
////            }
//            if(str[0].equals("request_current_players")){
//                System.out.println("update!!!");
//                ArrayList<String> players = new ArrayList<>();
//                for(ServerHandler serverHandler:Server.currentServerThreads){
//                    players.add(serverHandler.getClientName());
//                }
//                for(ServerHandler serverHandler : currentServerThreads){
//                    if(serverHandler.getClientName().equals(str[1]))
//                        serverHandler.sendObject(players);
//                }
//            }
//        }
//
//    }

    private void setUpConnection() throws IOException {
//        output = new ObjectOutputStream(connection.getOutputStream());
//        output.flush();
//        input = new ObjectInputStream(connection.getInputStream());
//        System.out.println("stream set up!");
    }

    private void waitForConnection() throws IOException{
        System.out.println("wait for connection");
        connection = serverSocket.accept();
        ServerHandler serverThread=new ServerHandler(connection);
        currentServerThreads.add(serverThread);
//        System.out.println(currentServerThreads.size());
        serverThread.start();
//        executorService.execute(serverThread);
        System.out.println("connenct to "+ connection.getInetAddress().getHostName());

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

    public ArrayList<ServerHandler> getCurrentServerThreads() {
        return currentServerThreads;
    }

    public void setCurrentServerThreads(ArrayList<ServerHandler> currentServerThreads) {
        Server.currentServerThreads = currentServerThreads;
    }
}
