package group.riskgame.Application.Model;

import group.riskgame.Application.Model.*;
import javafx.scene.paint.Color;
import org.junit.Before;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.Assert.*;

public class PlayerTest {
    Player player1;
    Player player2;
    private ArrayList<Object> allPlayers = new ArrayList<>();
    private HashMap<String, Color> playersColorMap = new HashMap<>();
    private ArrayList<Country> occupiedCountries = new ArrayList<>();
    private currentProcess phase = currentProcess.Preparation;
    @Before
    public void before() throws IOException {
        player1 = new Player("player1");
        player2 = new Player("player2");
        allPlayers.add(player1);
        allPlayers.add(player2);
    }

    @org.junit.Test
    public void addTerritory() {
        player1.addTerritory();
        assertEquals(player1.getTerritoryCount(),1);
    }


    @org.junit.Test
    public void getName() {
        assertEquals(player1.getName(),"player1");
        assertEquals(player2.getName(),"player2");
    }

    @org.junit.Test
    public void addToOccupiedCountries() {
        assertEquals(player1.getOccupiedCountries().size(),0);
        player1.addToOccupiedCountries(Country.china);
        assertEquals(player1.getOccupiedCountries().size(),1);
    }

    @org.junit.Test
    public void setName() {
        player1.setName("setName");
        assertEquals(player1.getName(),"setName");
    }


    @org.junit.Test
    public void nextPhase() {
        player1.nextPhase();
        assertEquals(player1.getPhase(),currentProcess.Attack);
    }

    @org.junit.Test
    public void getPhase() {
        assertEquals(player1.getPhase(),currentProcess.Preparation);
    }

    @org.junit.Test
    public void getTotalCards() {
        assertEquals(player2.getTotalCards(),0);
        player2.addRandomCard("infantry");
        assertEquals(player2.getTotalCards(),1);
    }

    @org.junit.Test
    public void getPlayerCardList() {
        player1.addRandomCard("infantry");
        assertEquals(player1.getPlayerCardList().get(0).getCardType(),CardType.INFANTRY);
    }

    @org.junit.Test
    public void testAddRandomCard() {
        player1.addRandomCard("infantry");
        assertEquals(Optional.ofNullable((player1.getCards().get("infantry"))),Optional.ofNullable(1));
    }
}