/**
 * Thihs class is necessary for deck used in gameplay
 *
 * @author DKM
 * @author Tianyi
 * @version 3.0
 *
 */
package View;

import Model.Card;
import Model.Player;

import java.util.Observable;
import java.util.Random;


public class DeckModel extends Observable
{

    private static DeckModel deckModel;
    Card card[];
    int n = 44;
    String owner = null;

    /**
     * singleton pattern for returning only once instance of object
     *
     * @return actionModel object
     */
    public static DeckModel getCardModel()
    {
        if (deckModel == null)
        {
            deckModel = new DeckModel();
        }
        return deckModel;
    }

    /**
     * This method is used to initialize and shuffle the deck with 44 cards.
     *
     */
    public void initialize()
    {
        card = new Card[n];
        String category[] =
                {
                        "Infantry", "Cavalry", "Artillery", "Wild"
                };

        for (int i = 0; i < n; i++)
        {
            if (i < 14)
            {
                card[i] = new Card(category[0]);
            }
            else if (i < 28)
            {
                card[i] = new Card(category[1]);
            }
            else if (i < 42)
            {
                card[i] = new Card(category[2]);
            }
            else
            {
                card[i] = new Card(category[3]);
            }
        }
        shuffleCard();
    }

    /**
     * This method is used to shuffle the deck randomly.
     */
    public void shuffleCard()
    {
        Random r = new Random();
        for (int i = 0; i < n; i++)
        {
            int ran = r.nextInt(n);
            Card tmp = card[i];
            card[i] = card[ran];
            card[ran] = tmp;
        }
    }

    /**
     * This method is used to get the whole card set
     *
     * @return card The whole card set
     */
    public Card[] getCards()
    {
        return card;
    }

    /**
     * This method is used to send a card to a player.
     *
     * @param player The current player
     * @return card The card that is sent to the player
     */
    public Card sendCard(Player player)
    {
        int i = 0;
        while (true)
        {

            if (card[i].getOwner() == null)
            {
                card[i].setOwner(player);
                player.addCard(card[i]);
                return card[i];
            }
            i++;
        }
    }
}
