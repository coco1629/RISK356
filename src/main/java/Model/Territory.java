package Model;

import java.io.Serializable;

public class Territory implements Serializable {


    private int owner;//Owner of land
    private int units;//Number of territorial troops
    private int id;

    @Override
    public void finalize() throws Throwable{

    }

    public Territory(int id){
        this.id = id;
        owner = -1;
        units = -1;
    }

    public int getId() {
        return id;
    }

    public int getOwner() {
        return owner;
    }

    public int getUnits() {
        return units;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
