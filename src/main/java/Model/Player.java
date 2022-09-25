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

    public void occupyCountry(Country country, int num, String roomName){
        this.clientHandler.sendObject(Operation.OCCUPY);
        this.clientHandler.sendObject(roomName);
        this.clientHandler.sendObject(new Object[]{country, num});
    }

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
                if(Objects.equals(name, this.name)){
                    switch (operation) {
                        case RED -> {
//                            System.out.println("red");
                            this.color = Color.RED;
                            playersColorMap.put(name, Color.RED);
                        }
                        case BLUE -> {
                            this.color = Color.BLUE;
                            playersColorMap.put(name, Color.BLUE);
                        }
                        case PINK -> {
                            this.color = Color.PINK;
                            playersColorMap.put(name, Color.PINK);
                        }
                        case YELLOW -> {
                            this.color = Color.YELLOW;
                            playersColorMap.put(name, Color.YELLOW);
                        }
                        case ORANGE -> {
                            this.color = Color.ORANGE;
                            playersColorMap.put(name, Color.ORANGE);
                        }
                        case GRAY -> {
                            this.color = Color.GRAY;
                            playersColorMap.put(name, Color.GRAY);
                        }
                    }
                }
            }
        }
    }
}
