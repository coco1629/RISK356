package Model;

import Connection.ClientHandler;
import View.Country;
import View.CountryPath;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player implements Serializable {
    private static int cId=0;
    private HashMap<String,Integer> cards;
    private Color color;
    private String name;
    private ArrayList<CountryPath> countriesOwned;
    public int getTerritoryCount() {
        return territoryCount;
    }

    private int territoryCount;

    private ClientHandler clientHandler;

    public Player(String name) throws IOException {
        super();
//        this.color = color;
        this.name = name;
        this.territoryCount = 0;
        this.clientHandler = new ClientHandler(name);
        countriesOwned = new ArrayList<CountryPath>();
//        clientHandler.start();
//        color =
        cards = new HashMap<>();
        cards.put("infantry",0);
        cards.put("cavalry",0);
        cards.put("artillery",0);
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

    public void occupyCountry(Country country, int num){

    }
    public int getTotalCards(){
        int n = 0;
        for (String key : cards.keySet()){
            n += cards.get(key);
        }
        return n;
    }

    public List<Card> getPlayerCardList(){
        List<Card> playerCardList = new ArrayList<>();
        for(Map.Entry<String,Integer> entry:cards.entrySet()){
            if(entry.getKey().equals(CardType.ARTILLERY.toString().toLowerCase())){
                for(int i=0; i < entry.getValue(); i++){
                    playerCardList.add(new Card(CardType.ARTILLERY));
                }
            }
            if(entry.getKey().equals(CardType.CAVALRY.toString().toLowerCase())){
                for(int i=0; i < entry.getValue(); i++){
                    playerCardList.add(new Card(CardType.CAVALRY));
                }
            }
            if(entry.getKey().equals(CardType.INFANTRY.toString().toLowerCase())){
                for(int i=0; i < entry.getValue(); i++){
                    playerCardList.add(new Card(CardType.INFANTRY));
                }
            }
        }
        return playerCardList;
    }
    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
