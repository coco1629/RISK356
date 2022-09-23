package Connection;

import Model.Player;
import View.Country;

import java.io.Serializable;

public class SessionData implements Serializable {

    private String player;

    private Operation operation;

    private int numberOfAllowedPlayers;



    private Country country;

    public SessionData(String player, Operation operation, int numberOfAllowedPlayers) {
        this.player = player;
        this.operation = operation;
        this.numberOfAllowedPlayers = numberOfAllowedPlayers;
//        this.numberOfPlayers = numberOfPlayers;
    }

    public SessionData() {
    }

    @Override
    public String toString() {
        return "SessionData{" +
                "player=" + player +
                ", operation=" + operation +
                ", numberOfAllowedPlayers=" + numberOfAllowedPlayers +
                '}';
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int getNumberOfAllowedPlayers() {
        return numberOfAllowedPlayers;
    }

    public void setNumberOfAllowedPlayers(int numberOfAllowedPlayers) {
        this.numberOfAllowedPlayers = numberOfAllowedPlayers;
    }


}
