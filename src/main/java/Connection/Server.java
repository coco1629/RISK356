package Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {


    private ServerSocket serverSocket;
    private Socket connection;

    ObjectOutputStream output;
    ObjectInputStream input;


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

    private void communicate() throws IOException, ClassNotFoundException {
        String message = "from server";
        while(!message.equals("END")){
            message =(String) input.readObject();
            System.out.println("server receive"+ message);
            String send = "server: send to client";
            output.writeObject(send);
        }

    }

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

}
