package Model;

import javafx.scene.paint.Color;

import java.util.Locale;

public enum Country {

    alaska,alberta,central_america,
    eastern_united_states,greenland,northwest_territory,ontario,
    quebec,western_united_states,
    argentina,brazil,venezuela,peru,
    great_britain,iceland,
    northern_europe,scandinavia,
    southern_europe,ukraine,
    western_europe,congo,
    east_africa,egypt,
    madagascar,north_africa,
    south_africa,afghanistan,
    china,india,
    irkutsk,japan,
    kamchatka,middle_east,
    mongolia,siam,
    siberia,ural,
    yakutsk,eastern_australia,
    new_guinea,indonesia,
    western_australia;

    private Color color;
    private int population;

    Country() {
        color = null;
        population = 0;
    }

    public String getName() {
        return name();
    }

    public Color getColor() {
        return color;
    }

    public int getPopulation() {
        return population;
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
