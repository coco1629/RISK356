package View;

import Functions.Card;
import Model.CardModel;
import Model.GameModel;


import Model.Player;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;


public class CardController {

    @FXML private Button trade;
    @FXML private Button cancelCardView;
    @FXML private Button closeButton;
    @FXML private Label currentPlayerName;
    @FXML private Label textToShow;
    @FXML private VBox cardVbox;

    private List<Card> playerCards;
    private CheckBox[] cbs;
    private GameModel model;
    private CardView card;
    private Player currentPlayer;


    public void init(GameModel model, CardView card) {
        this.model = model;
        this.card = card;
    }


    @FXML
    private void cancelCardView(ActionEvent event) {
        model.quitCard();
        if(CardModel.getInstance().quit()){
            Stage stage = (Stage) cancelCardView.getScene().getWindow();
            stage.close();
        }
    }


    public void closeRequest(){
        if(!cancelCardView.isDisable()) {
            model.quitCard();
            if (CardModel.getInstance().quit()) {
                Stage stage = (Stage) cancelCardView.getScene().getWindow();
                stage.close();
            }
        }else{
            System.out.println("just close");
        }
    }


    @FXML
    private void checkTrade(ActionEvent event) {
        trade.setDisable(false);
        textToShow.setText(null);
        currentPlayer = CardModel.getInstance().getCurrentPlayer();
        List<Card> selectedCards = CardModel.getInstance().retrieveCards
                (currentPlayer.getPlayerCardList(),cbs);
        if (selectedCards.size() == 3) {
            model.trade((ArrayList<Card>) selectedCards);
        }
    }


    public void autoInitializeController() {
        cardVbox.getChildren().clear();
        currentPlayer = CardModel.getInstance().getCurrentPlayer();
        currentPlayerName.setText("All Cards Of Player : " + currentPlayer.getName());
        textToShow.setStyle("-fx-text-fill: red");
        if(CardModel.getInstance().finish()){
            textToShow.setStyle("-fx-text-fill: green");
        }
        textToShow.setText(CardModel.getInstance().getInvalidInformation());
        playerCards = currentPlayer.getPlayerCardList();

        if (playerCards.size() < 3) {
            trade.setDisable(true);
        } else {
            trade.setDisable(false);
        }
        closeButton.setVisible(false);
        loadAllCards();
    }


    public void loadAllCards() {
        int numberOfCards = playerCards.size();
        cbs = new CheckBox[numberOfCards];
        for (int i = 0; i < numberOfCards; i++) {
            cbs[i] = new CheckBox(
                    playerCards.get(i).getCardType().toString());
        }
        cardVbox.getChildren().addAll(cbs);
    }

    public void openReadOnlyCardWindow(){
        autoInitializeController();
        cancelCardView.setVisible(false);
        trade.setVisible(false);
        closeButton.setVisible(true);

    }


    public void closeReadOnlyCardWindow(){
        cancelCardView.setVisible(true);
        trade.setVisible(true);
        Stage stage = (Stage) cancelCardView.getScene().getWindow();
        stage.close();
    }
}