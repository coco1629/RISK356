package View;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;


public class CardView implements Observer{

    private static CardView cardView;

    private AnchorPane anchorPane;
    private CardController cardController;
    private Stage cardStage;


    /**
     * Ctor for CardView
     */
    private CardView() {
        FXMLLoader menuFxmlLoader = new FXMLLoader(getClass().getResource("/view/Card.fxml"));
        try {
            anchorPane = menuFxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("CardView.constructor(): " + exception.getMessage());
        }
        cardController = menuFxmlLoader.getController();
        cardStage = new Stage();
        cardStage.setTitle("Card Exchange");
        cardStage.setScene(new Scene(anchorPane,500,300));
        cardStage.setResizable(false);
        cardStage.sizeToScene();
        cardStage.initStyle(StageStyle.UNDECORATED);
        cardStage.setOnCloseRequest((WindowEvent event1) -> cardController.closeRequest());
    }



    public static CardView getInstance() {
        if (null == cardView) cardView = new CardView();
        return cardView;
    }



    CardController getCardController() { return cardController; }



    public void show() { cardStage.show(); }

    public void hide() { cardStage.hide(); }

    public void close() { cardStage.close(); }



    @Override
    public void update(Observable obs, Object obj) {
        cardController.autoInitializeController();
        show();
    }
}
