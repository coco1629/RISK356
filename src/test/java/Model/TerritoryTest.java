package Model;

import Connection.Server;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TerritoryTest extends TestCase {
    Territory territory;
    Player player;

    @Before
    public void setUp() throws Exception {
        player = new Player("player");
        territory = new Territory(123);
    }
    @Test
    public void testGetId() {
        assertEquals(123,territory.getId());
    }
    @Test
    public void testGetOwner() {
        assertEquals(-1,territory.getOwner());
        territory.setOwner(1);
        assertEquals(1,territory.getOwner());
    }
    @Test
    public void testGetUnits() {
        assertEquals(-1,territory.getUnits());
        territory.setUnits(1);
        assertEquals(1,territory.getUnits());
    }

}