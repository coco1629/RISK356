package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Map implements Serializable {

    private static Map map = new Map();
    private static List<Territory> territoryList;

    private Map(){
        territoryList = new ArrayList<Territory>();
        for (Country country : Country.values()){
            territoryList.add(new Territory(country.ordinal()));
        }
    }

    public Territory getTerritory(int id){
        return territoryList.get(id);
    }

    public static List<Territory> getTerritoryList() {
        return territoryList;
    }

    protected void setTerritoryList(List<Territory> territoryList){
        Map.territoryList = territoryList;
    }

}
