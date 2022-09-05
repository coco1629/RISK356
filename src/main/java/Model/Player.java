package Model;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class Player implements Serializable {
    private Color color;
    private String name;
    private int territoryCount;


    public Player(Color color,String name){
        super();
        this.color = color;
        this.name = name;
        this.territoryCount = 0;
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

}
