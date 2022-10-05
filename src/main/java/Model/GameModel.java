package Model;

import Functions.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GameModel {

    private currentProcess phase;
    private HashMap<String,Integer> troopsMap = new HashMap<>();
    private ArrayList<String> playerList;
    private Player currentPlayer;
    private ArrayList<Territory> countryArrayList;
    private int initTroops = 10;
    public final static String[] cards = {"infantry","cavalry","artillery"};
    public static int cardsValue = 5;
    public static boolean disable = false;


    public GameModel(ArrayList<String> playerList){
        this.countryArrayList = new ArrayList<>();
        this.playerList = playerList;
        for (Country country : Country.values()){
            countryArrayList.add(new Territory(country.getName(),null,country.getPopulation()));
        }
        phase = currentProcess.Preparation;
        switch (playerList.size()) {
            case 3 -> setAllPlayersNumber(35);
            case 4 -> setAllPlayersNumber(30);
            case 5 -> setAllPlayersNumber(25);
            case 6 -> setAllPlayersNumber(20);
        }

    }

    public currentProcess getPhase(){
        return phase;
    }

//    public boolean occupy(String username, Country country, int num) throws Exception{
//        switch (phase){
//            case Preparation:{
////                phase = currentProcess.Attack;
//
//
////                for(Territory territory: countryArrayList){
////                    if(countryList.contains(territory.getName())){
////                        if(territory.getNum() == 0 && territory.getOnwner() == null){
////                            territory.setOnwner(username);
////                            territory.setNum(num);
////                        }
////                        else{
////                            if(territory.getNum() > num){
////                                territory.
////                            }
////
////                        }
////                    }
////                }
//
////                Territory territory = map.getTerritory(territoryId);
////                territory.setUnits(territory.getUnits() + initTroops);
////                return true;
//            }
//            default:
//                throw new Exception("You are not in a reinforcement phase!");
//        }
//    }

    public void setAllPlayersNumber(int num){
        for(String s: playerList){
            troopsMap.put(s,num);
        }
    }

    public void setPhase(currentProcess phase) {
        this.phase = phase;
    }

    public HashMap<String, Integer> getTroopsMap() {
        return troopsMap;
    }

    public void setTroopsMap(HashMap<String, Integer> troopsMap) {
        this.troopsMap = troopsMap;
    }

    public ArrayList<String> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<String> playerList) {
        this.playerList = playerList;
    }

    public ArrayList<Territory> getCountryArrayList() {
        return countryArrayList;
    }

    public void setCountryArrayList(ArrayList<Territory> countryArrayList) {
        this.countryArrayList = countryArrayList;
    }

    public int getInitTroops() {
        return initTroops;
    }

    public void setInitTroops(int initTroops) {
        this.initTroops = initTroops;
    }

    public boolean validExchange(String card1, String card2, String card3){
        if (card1.equals(card2) && card2.equals(card3)){
            return currentPlayer.getCards().get(card1) >= 1 && currentPlayer.getCards().get(card2) >= 1 && currentPlayer.getCards().get(card3) >= 1;
        }
        if(!card1.equals(card2) && !card2.equals(card3) && !card1.equals(card3)){
            return currentPlayer.getCards().get(card1) >= 1 && currentPlayer.getCards().get(card2) >= 1 &&
                    currentPlayer.getCards().get(card3) >= 1;
        }
        return false;

    }
    public void trade(ArrayList<Card> cards){
        String card1 = cards.get(0).cardType.toString().toLowerCase();
        String card2 = cards.get(0).cardType.toString().toLowerCase();
        String card3 = cards.get(0).cardType.toString().toLowerCase();
        if (!validExchange(card1, card2, card3)){
            CardModel.getInstance().setInvalidInfo(1);
            CardModel.getInstance().update();
            return;
        }
        CardModel.getInstance().setInvalidInfo(0);
        CardModel.getInstance().update();
        currentPlayer.handle(card1, card2, card3);
        currentPlayer.exchangeArmy();
        CardModel.getInstance().update();
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player p) {
        currentPlayer = p;
    }
    public void addArmy(){
        CardModel.getInstance().setCurrentPlayer(currentPlayer);
        CardModel.getInstance().update();

    }
    public void quitCard(){
        if(currentPlayer.getAllCards() >= 5){
            CardModel.getInstance().setInvalidInfo(3);
            CardModel.getInstance().update();
        }
        CardModel.getInstance().hide();
        disable = false;
//        currentPlayer.addArmy

    }
}
