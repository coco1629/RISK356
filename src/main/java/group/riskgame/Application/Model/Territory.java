package group.riskgame.Application.Model;

import java.awt.*;
import java.io.Serializable;

public class Territory implements Serializable {


//    private int owner;//Owner of land
//    private int units;//Number of territorial troops
    private Color color;
    private String owner;
    private String name;
    private int num;


    @Override
    public void finalize() throws Throwable{

    }

    public Territory(String name,String owner, int num){
        this.name = name;
        this.owner = owner;
        this.num = num;

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String onwner) {
        this.owner = onwner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
