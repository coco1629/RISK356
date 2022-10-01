package Model;

import java.awt.*;
import java.io.Serializable;

public class Territory implements Serializable {


//    private int owner;//Owner of land
//    private int units;//Number of territorial troops
    private Color color;
    private String onwner;
    private String name;
    private int num=0;


    @Override
    public void finalize() throws Throwable{

    }

    public Territory(String name){
        this.name = name;
        this.onwner = null;
//        owner = -1;
//        units = -1;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getOnwner() {
        return onwner;
    }

    public void setOnwner(String onwner) {
        this.onwner = onwner;
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
