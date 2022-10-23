package group.riskgame.Application.Model;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CountryTest {
    Country country1;
    Country country2;
    @Before
    public void setUp() throws Exception {
        country1 = Country.china;
        country2 = Country.afghanistan;
    }

    @Test
    public void getName() {
        assertEquals(country1.getName(),"china");
    }

    @Test
    public void getColor() {
        country1.setColor(Color.BLACK);
        assertEquals(country1.getColor(),Color.BLACK);
    }

    @Test
    public void getPopulation() {
        assertEquals(country1.getPopulation(),0);
        country1.setPopulation(10);
        assertEquals(country1.getPopulation(),10);
    }

    @Test
    public void valueOf() {
        assertEquals(Country.valueOf(country2.getName()),country2);
    }
}