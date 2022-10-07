package Model;

import Connection.ClientHandler;
import Connection.Operation;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Player implements Serializable {
    private Color color;
    private String name;
    private int territoryCount;

    private ClientHandler clientHandler;

    private ArrayList<Object> allPlayers = new ArrayList<>();

    private HashMap<String,Color> playersColorMap = new HashMap<>();

    private ArrayList<Country> occupiedCountries = new ArrayList<>();

    private int allowedTroops;

    private currentProcess phase = currentProcess.Preparation;

//    private ArrayList<Territory> ownedTerritories = new ArrayList<>();


    public Player(String name) throws IOException {
        super();
//        this.color = color;
        this.name = name;
        this.territoryCount = 0;
        this.clientHandler = new ClientHandler(name);
//        clientHandler.start();
//        color =
    }

    public void addTerritory() {
        territoryCount++;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

//    public void occupyCountries(ArrayList<Country> countries, int num, String roomName){
//        this.clientHandler.sendObject(Operation.OCCUPY);
//        this.clientHandler.sendObject(roomName);
//        this.clientHandler.sendObject(new Object[]{this.name,countries, num});
//    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public ArrayList<Object> getAllPlayers() {
        return allPlayers;
    }

    public void setAllPlayers(ArrayList<Object> allPlayers) {
        this.allPlayers = allPlayers;
        if(allPlayers != null){
            for(int i = 0; i < allPlayers.size(); i++){
                Object[] array = (Object[]) allPlayers.get(i);
                String name = (String) array[1];
//                System.out.println(name);
                Operation operation = (Operation) array[0];

                switch (operation) {
                    case RED -> {
                        if(Objects.equals(name, this.name))
//                            System.out.println("red");
                            this.color = Color.RED;
                        playersColorMap.put(name, Color.RED);
                    }
                    case BLUE -> {
                        if(Objects.equals(name, this.name))
                            this.color = Color.BLUE;
                        playersColorMap.put(name, Color.BLUE);
                    }
                    case PINK -> {
                        if(Objects.equals(name, this.name))
                            this.color = Color.PINK;
                        playersColorMap.put(name, Color.PINK);
                    }
                    case YELLOW -> {
                        if(Objects.equals(name, this.name))
                            this.color = Color.YELLOW;
                        playersColorMap.put(name, Color.YELLOW);
                    }
                    case ORANGE -> {
                        if(Objects.equals(name, this.name))
                            this.color = Color.ORANGE;
                        playersColorMap.put(name, Color.ORANGE);
                    }
                    case GRAY -> {
                        if(Objects.equals(name, this.name))
                            this.color = Color.GRAY;
                        playersColorMap.put(name, Color.GRAY);
                    }

                }
            }
        }
        System.out.println(playersColorMap.toString());
    }

    public HashMap<String, Color> getPlayersColorMap() {
        return playersColorMap;
    }

    public void setPlayersColorMap(HashMap<String, Color> playersColorMap) {
        this.playersColorMap = playersColorMap;
    }

    public ArrayList<Country> getOccupiedCountries() {
        return occupiedCountries;
    }

    public void setOccupiedCountries(ArrayList<Country> occupiedCountries) {
        this.occupiedCountries = occupiedCountries;
    }

    public void addToOccupiedCountries(Country country){
        this.occupiedCountries.add(country);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTerritoryCount() {
        return territoryCount;
    }

    public void setTerritoryCount(int territoryCount) {
        this.territoryCount = territoryCount;
    }

    public int getAllowedTroops() {
        return allowedTroops;
    }

    public void setAllowedTroops(int allowedTroops) {
        this.allowedTroops = allowedTroops;
    }

    public void nextPhase(){
        switch (phase){
            case Preparation, Reinforcement -> phase = currentProcess.Attack;
            case Attack -> phase = currentProcess.Fortify;
            case Fortify -> phase = currentProcess.Reinforcement;
        }
    }

    public currentProcess getPhase() {
        return phase;
    }

    public void setPhase(currentProcess phase) {
        this.phase = phase;
    }

//    public ArrayList<Territory> getOwnedTerritories() {
//        return ownedTerritories;
//    }
//
//    public void setOwnedTerritories(ArrayList<Territory> ownedTerritories) {
//        this.ownedTerritories = ownedTerritories;
//    }
}
