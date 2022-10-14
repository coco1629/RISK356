package Model;

import Connection.ClientHandler;
import Connection.Operation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private ArrayList<Territory> territories = new ArrayList<>();

    private int allowedTroops;

    private int leftTroops;

    private currentProcess phase = currentProcess.Preparation;

    private HashMap<String,Integer> cards;

    private int cardsArmy;

    private int numberOccupy;

//    private ArrayList<Territory> ownedTerritories = new ArrayList<>();


    public Player(String name) throws IOException {
        super();
//        this.color = color;
        this.name = name;
        this.territoryCount = 0;
        this.clientHandler = new ClientHandler(name);
        cards = new HashMap<>();

        cards.put("infantry",0);
        cards.put("cavalry",0);
        cards.put("artillery",0);
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
//        System.out.println(playersColorMap.toString());
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

    public ArrayList<Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(ArrayList<Territory> territories) {
        this.territories = territories;
    }

    //    public ArrayList<Territory> getOwnedTerritories() {
//        return ownedTerritories;
//    }
//
//    public void setOwnedTerritories(ArrayList<Territory> ownedTerritories) {
//        this.ownedTerritories = ownedTerritories;
//    }
    public int getTotalCards(){
        int n = 0;
        for (String key : cards.keySet()){
            n += cards.get(key);
        }
        return n;
    }


    /**
     * Get Player Card List
     * @return playerCardList
     */
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
    public HashMap<String,Integer> getCards(){
        return cards;
    }
    public void handleCards(String card1, String card2, String card3){
        cards.put(card1,cards.get(card1) - 1);
        cards.put(card2,cards.get(card2) - 1);
        cards.put(card3,cards.get(card3) - 1);
        CardModel.getInstance().update();

    }

    /**
     * player change cards for armies
     */
    public void exchangeForArmy(){
        cardsArmy += Model.cardsValue;
        Model.cardsValue += 5;
        this.allowedTroops += 5;
//        this.getClientHandler().sendObject(Operation.EXCHANGE);
    }
    public void autoTradeCard() {

        for (String card : cards.keySet()) {

            if (cards.get(card) >= 3) {
                handleCards(card, card, card);
                exchangeForArmy();
                return;
            }
        }

        handleCards("infantry","cavalry","artillery");
        exchangeForArmy();
    }
    public void getDefenderCards(Player attacker, Player defender){
        for(String key : defender.cards.keySet()){
            attacker.cards.put(key, attacker.cards.get(key) + defender.cards.get(key));
            defender.cards.put(key,0);
        }
    }
    public int getNumberOccupy() {
        return numberOccupy;
    }

    /**
     * Set number of occupy
     * @param numberOccupy number of occupy
     */
    public void setNumberOccupy(int numberOccupy) {
        this.numberOccupy = numberOccupy;
    }

    public void addRandomCard(String newCard) {
//        if (numberOccupy > 0) {
            int value = cards.get(newCard) + 1;
            cards.put(newCard, value);
//        }
        //reset the number of occupy
//        numberOccupy = 0;
    }

    /**
     * Add Random card without param
     */
    public void addRandomCard() {

//        if (getNumberOccupy() > 0) {
            Random random = new Random();
            int num = random.nextInt(3);
            String newCard = Model.cards[num];
            addRandomCard(newCard);
//        }
    }


}
