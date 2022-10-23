package group.riskgame.Application.Model;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class TerritoryTest {
    Player player1;
    Territory territory;

    @Before
    public void setUp() throws Exception {
        player1 = new Player("test");
        territory = new Territory("type1",player1.getName(),10);
    }

    @Test
    public void getColor() {
        assertNull(territory.getColor());
        territory.setColor(Color.BLACK);
        assertEquals(territory.getColor(),Color.BLACK);
    }

    @Test
    public void getOwner() {
        assertEquals(territory.getOwner(),player1.getName());
    }

    @Test
    public void getName() {
        assertEquals(territory.getName(),"type1");
    }

    @Test
    public void getNum() {
        assertEquals(territory.getNum(),10);
    }
}