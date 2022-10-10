/**
 * Necessary to create the model of the players in the game play. Important for getting current
 * player
 *
 * @author Natheepan
 * @author Tianyi
 * @author DKM
 *
 * @version 3.0
 */
package Model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class PlayerPhaseModel extends Observable implements Observer
{

    private ArrayList<Player> playerList = new ArrayList<Player>();
    public static final String[] playerName =
            {
                    "Player1", "Player2", "Player3", "Player4", "Player5", "Player6"
            };
    private int currentPlayerIndex = 0;
    private Player playerWins = null;
    private static PlayerPhaseModel playerModel;

    /**
     * Constructor for PlayerModel class
     */
    PlayerPhaseModel()
    {
    }

    /**
     * This method clears the state
     */
    public void clear()
    {
        playerList.clear();
        currentPlayerIndex = 0;
    }

    /**
     * Sets the winner of the game
     *
     * @param player player who has won
     * @return playerWins returns the player who has won
     */
    public Player setWinner(Player player)
    {
        playerWins = player;
        return playerWins;
    }

    /**
     * This method increments the current player index
     *
     * @param increment this is the index of player to increment
     */
    public void setPlayerIndex(int increment)
    {
        currentPlayerIndex = increment;
    }

    /**
     * This method is used to get the index of the current player
     *
     * @return the index of the current player
     */
    public int getPlayerIndex()
    {
        return currentPlayerIndex;
    }


    /**
     * This method sets the next player. If lost then skips.
     */

    public void setNextPlayer()
    {

        while (true)
        {
            System.out.println(playerList.get(currentPlayerIndex % playerList.size()).getName() + " isLost? = " + playerList.get(currentPlayerIndex % playerList.size()).isPlayerLost());
            if (!playerList.get(++currentPlayerIndex % playerList.size()).isPlayerLost())
            {
                playerList.get(currentPlayerIndex % playerList.size());
                break;
            }
        }
    }

    /**
     * This method is used to get the number of players
     *
     * @return number of total players
     */
    public int getNumberOfPlayer()
    {
        return playerList.size();
    }

    /**
     * This method gets the current player
     *
     * @return current player
     */
    public Player getCurrentPlayer()
    {
        return playerList.get(currentPlayerIndex % playerList.size());
    }

    /**
     * This method gets the current player
     *
     * @param index position in arraylist
     */
    public void setCurrentPlayer(int index)
    {
        currentPlayerIndex = index;
    }

    /**
     * This method adds player the player model
     *
     * @param player player to be added to the model
     */
    public void addPlayer(Player player)
    {
        setStrategy(player);
        playerList.add(player);
        player.addObserver(this);

    }

    private void setStrategy(Player player) {
    }

    /**
     * this method gets the players in the player models
     *
     * @return playersModel returns the player model
     */
    public ArrayList<Player> getPlayers()
    {
        return playerList;
    }

    public void setPlayers(ArrayList<Player> players)
    {
        playerList = players;
    }

    /**
     * This method is used to get the player phase model
     *
     * @return this PlayerModel class as a singleton
     */
    public static PlayerPhaseModel getPlayerModel()
    {
        if (playerModel == null)
        {
            playerModel = new PlayerPhaseModel();
        }
        return playerModel;
    }

    /**
     * This sets the strategy of the player
     *
     * @param player this is the player without strategy
     */


    /**
     * This method receives update from Player class This method notifies
     * GamePhaseModel, updating world domination phase
     */
    @Override
    public void update(Observable o, Object country)
    {
        setChanged();
        notifyObservers((Country) country);
    }
}
