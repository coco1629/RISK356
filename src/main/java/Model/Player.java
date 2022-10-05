package Model;

import Connection.ClientHandler;
import Connection.Operation;
import Functions.Card;
import Functions.CardType;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Player implements Serializable {
    private Color color;
    private String name;
    private int territoryCount;

    private ClientHandler clientHandler;

    private ArrayList<Object> allPlayers = new ArrayList<>();

    private HashMap<String,Color> playersColorMap = new HashMap<>();

    private ArrayList<Country> occupiedCountries = new ArrayList<>();

    private HashMap<String, Integer> cards;
    private int cardsArmy;


    public Player(String name) throws IOException {
        super();
//        this.color = color;
        this.name = name;
        this.territoryCount = 0;
        this.clientHandler = new ClientHandler(name);
        cards = new HashMap<>();
        cards.put("infantry", 0);
        cards.put("cavalry", 0);
        cards.put("artillery", 0);
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
        occupiedCountries.add(country);
    }

    public int getAllCards(){
        int n = 0;
        for(String key: cards.keySet()){
            n += cards.get(key);
        }
        return n;
    }
    public List<Card> getPlayerCardList(){
        List<Card> playerCards = new ArrayList<>();
        for (Map.Entry<String, Integer> entry:cards.entrySet()){
            if(entry.getKey().equals(CardType.ARTILLERY.toString().toLowerCase()))
                for (int i=0; i < entry.getValue(); i ++){
                    playerCards.add(new Card(CardType.ARTILLERY));
                }
            if(entry.getKey().equals(CardType.CAVALRY.toString().toLowerCase()))
                for (int i=0; i < entry.getValue(); i ++){
                    playerCards.add(new Card(CardType.CAVALRY));
                }
            if(entry.getKey().equals(CardType.INFANTRY.toString().toLowerCase()))
                for (int i=0; i < entry.getValue(); i ++){
                    playerCards.add(new Card(CardType.INFANTRY));
                }
        }
        return playerCards;

    }
    public HashMap<String, Integer> getCards(){
        return cards;
    }
    public int getCardsArmy(){return cardsArmy;}
    public void handle(String card1, String card2, String card3){
        cards.put(card1, cards.get(card1) - 1);
        cards.put(card2, cards.get(card2) - 1);
        cards.put(card3, cards.get(card3) - 1);
        CardModel.getInstance().update();

    }
    public void exchangeArmy(){
        cardsArmy += GameModel.cardsValue;
        GameModel.cardsValue += 5;

    }
    public void addArmy(){
        
        
    }

}
