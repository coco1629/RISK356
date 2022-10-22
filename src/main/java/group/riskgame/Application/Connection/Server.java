package group.riskgame.Application.Connection;

import group.riskgame.Application.Model.GameModel;
import group.riskgame.Application.Model.Territory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
        for(int i = 0; i < input.size(); i++){
            Territory territory = input.get(i);
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
                        }
                        else{
                            t.setOwner(territory.getOwner());
                            t.setNum(territory.getNum());
                        }
                    }
                    add = false;
                }
            }
            if(add){
                this.territories.add(territory);
            }
            gameModel.setTerritoryArrayList(this.territories);
        }

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
