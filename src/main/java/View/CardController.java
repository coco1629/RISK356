package View;


import Model.Card;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import Model.Player;

import java.util.ArrayList;
import java.util.List;
import Model.Model;
import Model.CardModel;

/**
 * Handle event when user interact with CardView
 */
public class CardController {

    @FXML private Button trade;
    @FXML private Button cancelCardView;
    @FXML private Button closeButton;
    @FXML private Label currentPlayerName;
    @FXML private Label textToShow;
    @FXML private VBox cardVbox;

    private List<Card> playerCards = new ArrayList<Card>();
    private CheckBox[] cbs;
    private Model model;
    private CardView card;
    private Player currentPlayer;
    private String style;

    public void setStyle(String style) {
        this.style = style;
    }


    /**
     * Get corresponding reference of Model, CardView and MapController
     * @param model is the reference of Model
     * @param card is the reference of CardView
     */
    public void init(Model model, CardView card) {
        this.model = model;
        this.card = card;
    }

    /**
     * Handle cancelling CardView event
     * @param event the Action event
     */
    @FXML
    public void cancelCardView(ActionEvent event) {
        model.quitCards();
        if(CardModel.getInstance().readyToQuit()) {
            Stage stage = (Stage) cancelCardView.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * handle close card window by red button
     */
    public void closeRequest(){
        if(!cancelCardView.isDisable()) {
            model.quitCards();
            if (CardModel.getInstance().readyToQuit()) {
                Stage stage = (Stage) cancelCardView.getScene().getWindow();
                stage.close();
            }
        }else{
            System.out.println("just close");
        }
    }

    /**
     * if the exchange operation is valid
     * @param event the Action event
     */
    @FXML
    private void checkTrade(ActionEvent event) {
        trade.setDisable(false);
        textToShow.setText(null);
//        currentPlayer = CardModel.getInstance().getCurrentPlayer();
        List<Card> selectedCards = CardModel.getInstance().retrieveSelectedCardsFromCheckbox
                (this.currentPlayer.getPlayerCardList(),cbs);
        if (selectedCards.size() == 3) {
            model.trade((ArrayList<Card>) selectedCards);
        }
    }

    /**
     * initiate CardView
     */
    public void autoInitializeController() {
        cardVbox.getChildren().clear();
        currentPlayer = CardModel.getInstance().getCurrentPlayer();
//        System.out.println(currentPlayer.getPlayerCardList());
        currentPlayerName.setText("All Cards Of Player : " + currentPlayer.getName());
        textToShow.setStyle("-fx-text-fill: red");
        if(CardModel.getInstance().finishExchange()){
            textToShow.setStyle("-fx-text-fill: green");
        }
        textToShow.setText(CardModel.getInstance().getInvalidInfo());
        playerCards = currentPlayer.getPlayerCardList();
//        System.out.println(playerCards.size());

        if (playerCards.size() < 3) {
            trade.setDisable(true);
        } else {
            trade.setDisable(false);
        }
        closeButton.setVisible(false);
        loadAllCards();
    }

    /**
     * show all the cards on the CardView
     */
    public void loadAllCards() {
        int numberOfCards = playerCards.size();
        cbs = new CheckBox[numberOfCards];
        for (int i = 0; i < numberOfCards; i++) {
            cbs[i] = new CheckBox(
                    playerCards.get(i).getCardType().toString());
        }
        cardVbox.getChildren().addAll(cbs);
    }

    /**
     * card button open read only card ex window
     */
    public void openReadOnlyCardWindow(){
        autoInitializeController();
        cancelCardView.setVisible(false);
        trade.setVisible(false);
        closeButton.setVisible(true);

    }

    /**
     *close card ex window without check current card numbers
     */
    public void closeReadOnlyCardWindow(){
        cancelCardView.setVisible(true);
        trade.setVisible(true);
        Stage stage = (Stage) cancelCardView.getScene().getWindow();
        stage.close();
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(List<Card> playerCards) {
        this.playerCards = playerCards;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Button getCloseButton() {
        return closeButton;
    }

    public void setCloseButton(Button closeButton) {
        this.closeButton = closeButton;
    }

    public Button getCancelCardView() {
        return cancelCardView;
    }

    public void setCancelCardView(Button cancelCardView) {
        this.cancelCardView = cancelCardView;
    }
}