package Connection;

import Model.Country;
import Model.GameModel;
import Model.Territory;
import Model.currentProcess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class Server {

    private ServerSocket serverSocket;
    private Socket connection;


    static HashMap<String,ServerHandler> handlerHashMap = new HashMap<>();

    public static ArrayList<String> currentClients = new ArrayList<>();

    private HashMap<String, GameModel> gameModelHashMap = new HashMap<>();

    private static GameModel gameModel = new GameModel();

    private static ArrayList<Territory> territories = gameModel.getTerritoryArrayList();

    private int updateplayers = 0;

    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void startServer(){
        try{

            while(true){
                connection = serverSocket.accept();
                ServerThread thread = new ServerThread(connection);
                thread.setServer(this);
                thread.start();


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

    public static HashMap<String, ServerHandler> getHandlerHashMap() {
        return handlerHashMap;
    }

    public static void setHandlerHashMap(HashMap<String, ServerHandler> handlerHashMap) {
        Server.handlerHashMap = handlerHashMap;
    }

    public ArrayList<Territory> getTerritories() {
        return this.territories;
    }

    public void setTerritories(ArrayList<Territory> territories) {
        this.territories = territories;
    }

    public void updateTerritories(ArrayList<Territory> input,String playerName){
//        System.out.println("update");
//        System.out.println("input array: ");
//        for (Territory country : input){
//            System.out.println("country name: " + country.getName() + " num: " + country.getNum() + " owner: " + country.getOwner());
//        }
        for(int i = 0; i < input.size(); i++){
            Territory territory = input.get(i);
//            System.out.println(territory.getName() + "troops" + territory.getNum());
            boolean add = true;
            for(int j = 0; j < this.territories.size(); j++){
                Territory t = this.territories.get(j);
                if(t.getName().equals(territory.getName())){
                    if(t.getOwner()==null){
                        t.setOwner(territory.getOwner());
                    }
                    else if(Objects.equals(t.getOwner(), territory.getOwner())){
                        t.setNum(territory.getNum());
                    }
                    else if(!t.getOwner().equals(territory.getOwner())){
                        if(t.getNum() >= territory.getNum()){
//                            System.out.println(t.getOwner() + ">=" + territory.getOwner());
                        }
                        else{
//                            System.out.println(t.getOwner() + "<" + territory.getOwner());
                            t.setOwner(territory.getOwner());
                            t.setNum(territory.getNum());
                        }
                    }
                    add = false;
                }
            }
            if(add){
                this.territories.add(territory);
//                System.out.println("add country: " + territory.getName() + " owner: " + territory.getOwner());
            }
            gameModel.setTerritoryArrayList(this.territories);
        }
//        if(gameModel.getTerritoryArrayList().size() == 2 && gameModel.getPhase() == currentProcess.Preparation){
//            gameModel.setPhase(currentProcess.Attack);
//        }
//        System.out.println("territories list in server: ");
//        for (Territory country : this.territories){
//            System.out.println("country name: " + country.getName() + " num: " + country.getNum());
//        }
    }

    public static GameModel getGameModel() {
        return gameModel;
    }

    public static void setGameModel(GameModel gameModel) {
        Server.gameModel = gameModel;
    }

    public int getUpdateplayers() {
        return updateplayers;
    }

    public void setUpdateplayers(int updateplayers) {
        this.updateplayers = updateplayers;
    }



}
