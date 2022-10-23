package group.riskgame.Application.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ModelTest {
    Player player;
    Player player2;
    Model model;

    @Before
    public void setUp() throws Exception {
        player = new Player("modelTest");
        player2 = new Player("model");
        model = new Model();
        model.setCurrentPlayer(player);
    }

    @Test
    public void getCurrentPlayer() {
        assertEquals(model.getCurrentPlayer(),player);
        model.setCurrentPlayer(player2);
        assertEquals(model.getCurrentPlayer(),player2);
    }


    @Test
    public void validCardExchange() {
        assertFalse(model.validCardExchange(null,null,null));
    }


}