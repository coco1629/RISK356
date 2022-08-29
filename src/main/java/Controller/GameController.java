package Controller;

import Model.GameModel;
import Model.Territory;
import Model.currentProcess;
import View.Country;
import View.LoginView;
import View.MainView;
import javafx.stage.Stage;

public class GameController extends java.util.Observable{

    private Stage primaryStage;

    private MainView mainView;
    private LoginView loginView;

    private GameModel model;
    private currentProcess currentProcess = Model.currentProcess.Reinforcement;

    private int previouslySelectedTerritory = -1;

    public GameController(Stage stage){
        this.primaryStage = new Stage();
        primaryStage.setResizable(false);
    }

    public void setModel(GameModel model) {
        this.model = model;
    }

    public GameModel getModel() {
        return model;
    }

    private int convertToTerritoryID(Country country){
        return country.ordinal();
    }
    private Country converToCountry(int ID){
        return Country.values()[ID];
    }

    public void countrySelected(Country country){
        int territoryID = convertToTerritoryID(country);

        try {
            switch (model.getPhase()){
                case Reinforcement:
                    reinforcement(territoryID,country.getName());
                    break;
                case Preparation:
                    reinforcement(territoryID,country.getName());
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void reinforcement(int territoryID, String country) throws Exception{
        if (model.reinforce(territoryID)){
            Territory territory = model.getTerritory(territoryID);
            UpdateTerritoryOnMap(territoryID);
        }
    }

    private void UpdateTerritoryOnMap(int territoryID){
        Territory territory = model.getTerritory(territoryID);
    }

}
