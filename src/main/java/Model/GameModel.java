package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GameModel {

    private currentProcess phase = currentProcess.Preparation;
    private HashMap<String,Integer> troopsMap = new HashMap<>();
    private ArrayList<String> playerList;
    private ArrayList<Territory> territoryArrayList = new ArrayList<>();
    private int initTroops = 10;


    public GameModel(){

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

    public void init(){
//        this.countryArrayList = new ArrayList<>();
//        this.playerList = playerList;
        phase = currentProcess.Preparation;
        switch (playerList.size()) {
            case 3 -> setAllPlayersNumber(35);
            case 4 -> setAllPlayersNumber(30);
            case 5 -> setAllPlayersNumber(25);
            case 6 -> setAllPlayersNumber(20);
        }
    }

    public void nextPhase(){
        switch (phase){
            case Preparation, Reinforcement -> phase = currentProcess.Attack;
            case Attack -> phase = currentProcess.Fortify;
            case Fortify -> phase = currentProcess.Reinforcement;
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

    public ArrayList<Territory> getTerritoryArrayList() {
        return territoryArrayList;
    }

    public void setTerritoryArrayList(ArrayList<Territory> territoryArrayList) {
        this.territoryArrayList = territoryArrayList;
    }

    public int getInitTroops() {
        return initTroops;
    }

    public void setInitTroops(int initTroops) {
        this.initTroops = initTroops;
    }


}
