package Model;

import java.util.List;

public class GameModel {

    private currentProcess phase;
    private Map map;
    private List<Player> playerList;
    private int initTroops = 10;

    public GameModel(){
       phase = currentProcess.Reinforcement;
    }

    public currentProcess getPhase(){
        return phase;
    }
    /**
     * Reinforce method
     *
     * @param territoryId
     * @return boolean
     * @throws Exception
     */
    public boolean reinforce(int territoryId) throws Exception{
        switch (phase){
            case Reinforcement:{
                Territory territory = map.getTerritory(territoryId);
                territory.setUnits(territory.getUnits() + initTroops);
                return true;
            }
            default:
                throw new Exception("You are not in a reinforcement phase!");
        }
    }

    public Territory getTerritory(int id){
        return map.getTerritory(id);
    }

    public void addPlayerToPlayerList(Player player) throws Exception{
        if (phase != currentProcess.Resgistration)
            throw new Exception("Wrong phase");
        playerList.add(player);
    }
}
