package Model;

import Connection.ClientHandler;
import Connection.Server;
import javafx.scene.paint.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.net.ServerSocket;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {
    private ClientHandler clientHandler;
    private Player player;

    @Before
    public void setUp() throws Exception {
        player = new Player("testPlayer");
        player.setColor(Color.BLACK);
        player.addTerritory();

    }

    @Test
    public void getTerritories(){
        assertEquals(1,player.getTerritoryCount());
    }
    @Test
    public void getTotalCards(){
        assertEquals(0,player.getTotalCards());
    }
    @Test
    public void getCardList(){

        assertEquals(0,player.getPlayerCardList().size());
    }
    @Test
    public void getColor() {
        assertEquals(Color.BLACK,player.getColor());
    }

    @Test
    public void getName() {
        assertEquals("testPlayer",player.getName());
    }

//    @Test
//    public void getClientHandler() {
//        assertEquals("testPlayer",clientHandler.getUsername());
//    }
//
//    @After
//    public void tearDown(){
//        server.closeServer();
//    }

}