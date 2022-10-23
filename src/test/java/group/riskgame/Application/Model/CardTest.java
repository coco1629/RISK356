package group.riskgame.Application.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {
    Territory territory;
    Card card;
    CardType cardType;
    Player player;
    @Before
    public void setUp() throws Exception {
        player = new Player("cardTest");
        cardType = CardType.INFANTRY;
        card = new Card(cardType);
        territory = new Territory("china", player.getName(),10);
    }

    @Test
    public void getCardType() {
        assertEquals(card.getCardType(),CardType.INFANTRY);
    }

    @Test
    public void getTerritory() {
        assertNull(card.getTerritory());
        card.setTerritory(Country.valueOf(territory.getName()));
        assertEquals(card.getTerritory().getName(),territory.getName());
    }


    @Test
    public void testEquals() {
    }
}