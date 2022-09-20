package Model;

import Connection.ClientHandler;
import View.Country;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.Serializable;

public class Player implements Serializable {
    private Color color;
    private String name;
    private int territoryCount;

    private ClientHandler clientHandler;

    public Player(String name) throws IOException {
        super();
//        this.color = color;
        this.name = name;
        this.territoryCount = 0;
        this.clientHandler = new ClientHandler(name);
//        clientHandler.start();
//        color =
    }

    public void addTerritory() {
        territoryCount++;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void occupyCountry(Country country, int num){

    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }
}
