package group.riskgame.Application.View;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;


/**
 * Responsible for visualizing the card exchange view, show useful info to user
 * Corresponding Observable subject is CardModel
 * Singleton pattern
 */
public class CardView implements Observer{
    private String style;

    private static CardView instance;

    private AnchorPane mainCardPane;
    private CardController cardController;
    private Stage cardStage;


    /**
     * Ctor for CardView
     */
    private CardView() throws IOException {
        FXMLLoader menuFxmlLoader = new FXMLLoader(getClass().getResource("/group.riskgame.Application/Card.fxml"));
        Parent main = menuFxmlLoader.load();
        try {
            mainCardPane = menuFxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("CardView.ctor(): " + exception.getMessage());
        }
        cardController = menuFxmlLoader.getController();
        cardStage = new Stage();
        cardStage.setTitle("Card Exchange");
        cardStage.setScene(new Scene(mainCardPane,500,300));
        cardStage.setResizable(false);
        cardStage.sizeToScene();
        cardStage.initStyle(StageStyle.UNDECORATED);
        cardStage.setOnCloseRequest((WindowEvent event1) -> cardController.closeRequest());
    }


    /**
     * Get or create the only instance
     * @return card view
     */
    public static CardView getInstance() throws IOException {
        if (null == instance) instance = new CardView();
        return instance;
    }


    /**
     * get the reference of cardController
     * @return the reference of cardController
     */
    CardController getCardController() { return cardController; }


    /**
     * show CardView
     */
    public void show() { cardStage.show(); }


    /**
     * hide CardView
     */
    public void hide() { cardStage.hide(); }


    /**
     * close CardView
     */
    public void close() { cardStage.close(); }


    /**
     * override update method
     * @param obs observable object
     * @param obj message from observable
     */
    @Override
    public void update(Observable obs, Object obj) {
        cardController.autoInitializeController();
        show();
    }


    public void checkTrade(ActionEvent actionEvent) {
    }

    public void cancelCardView(ActionEvent actionEvent) {
    }

    public void closeReadOnlyCardWindow(ActionEvent actionEvent) {
    }
}
