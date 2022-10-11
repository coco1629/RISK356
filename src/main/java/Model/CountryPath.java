package Model;

import javafx.geometry.Bounds;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

public class CountryPath extends SVGPath {

    private final String name;
    private boolean isSelect;

    private boolean isOccupied = false;

    private final Tooltip tooltip;
    private final Text units;


    public CountryPath(final String name) {
        this(name,null,0);
    }

    public CountryPath(final String name,final String content,final int unit){
        super();
        this.name = name;
        this.tooltip = new Tooltip(name);
        setContent(content);

        Bounds bb = this.getLayoutBounds();
        double x = bb.getMinX() + bb.getWidth()/2.0;
        double y = bb.getMinY() + bb.getHeight()/2.0;
        this.units = new Text(x, y, Integer.toString(0));
        setUnits(unit);
    }

    public CountryPath setSelect(boolean select){
        isSelect = select;
        return this;
    }

    public String getName() {
        return name;
    }
    public Text getText() {
        return units;
    }

    public void setUnits(int u){
        int cu = (u == -1) ? 0 : u;
        units.setText(Integer.toString(cu));
        tooltip.setText(name + " (" + Integer.toString(cu) + " unit)");
        updateUnitPosition();
    }

    public void updateUnitPosition(){
        Bounds bb = this.getLayoutBounds();
        double x = bb.getMinX() + bb.getWidth()/2.0;
        double y = bb.getMinY() + bb.getHeight()/2.0;

        int l = units.getText().length();
        if (l == 1)
            units.setX(x);
        else if (l == 2)
            units.setX(x - 6);
        else {
            units.setX(x - 10);
            // world dominance
        }
        units.setY(y);
    }

    public boolean isSelect() {
        return isSelect;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

}
