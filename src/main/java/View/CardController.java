/**
 * Necessary for handling card exchange logic for reinforcement phase
 * * The JavaFx part is done by DKM
 * The logic part is done by Tianyi
 *
 * @author DKM
 * @author Tianyi
 * @version 3.0
 *
 *
 */
package View;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import Model.ActionModel;
import Model.Card;


import Model.Player;
import Model.PlayerPhaseModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class CardController extends Observable implements Initializable
{

    @FXML
    ListView<Card> yourCard;

    @FXML
    ListView<Card> tradeCard;

    private int reinforcement;

    ActionModel actions;
    Player player;

    ObservableList<Card> yourObservableList = FXCollections.observableArrayList();
    ObservableList<Card> tradeObservableList = FXCollections.observableArrayList();

    public void renderView()
    {
        yourCard.setCellFactory(param -> new ListCell<Card>()
        {
            @Override
            protected void updateItem(Card card, boolean empty)
            {
                super.updateItem(card, empty);
                if (empty || card == null || card.getCatagory() == null)
                {
                    setText(null);
                }
                else
                {
                    setText(card.getCatagory());
                }
            }
        });
        tradeCard.setCellFactory(param -> new ListCell<Card>()
        {
            @Override
            protected void updateItem(Card card, boolean empty)
            {
                super.updateItem(card, empty);
                if (empty || card == null || card.getCatagory() == null)
                {
                    setText(null);
                }
                else
                {
                    setText(card.getCatagory());
                }
            }
        });
    }


    /**
     * This method is data binding for connection between controller and UI.
     *
     * @see javafx.fxml.Initializable
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        actions = ActionModel.getActionModel();
        yourCard.setItems(yourObservableList);
        tradeCard.setItems(tradeObservableList);
        yourObservableList.addAll(PlayerPhaseModel.getPlayerModel().getCurrentPlayer().getCards());
        player = PlayerPhaseModel.getPlayerModel().getCurrentPlayer();
        renderView();
    }

    /**
     * This method is used to update the cards the player owned and the cards
     * the player chooses to exchange
     */
    @FXML
    public void yourCardHandler()
    {
        if (yourCard.getSelectionModel().getSelectedItem() != null && tradeCard.getItems().size() < 3)
        {
            Card card = yourCard.getSelectionModel().getSelectedItem();
            tradeCard.getItems().add(card);
            yourCard.getItems().remove(card);

        }
    }

    /**
     * This method is used to update the cards the player owned and the cards
     * the player chooses to exchange.
     */
    @FXML
    public void yourTradeHandler()
    {
        if (tradeCard.getSelectionModel().getSelectedItem() != null)
        {
            Card card = tradeCard.getSelectionModel().getSelectedItem();
            yourCard.getItems().add(card);
            tradeCard.getItems().remove(card);
        }
    }

    /**
     * This method is used to update the total reinforcement based on the cards
     * the player chooses And update the cards the player owned.
     */
    @FXML
    public void tradeCard()
    {
        if (tradeCard.getItems().size() == 3)
        {
            if (player.cardValidation(tradeCard.getItems()))
            {
//                reinforcement = player.calculateReinforcementFromCards();

                player.exchangeCards(tradeCard.getItems());
                tradeCard.getItems().clear();
                actions.addAction("you exchanged cards");
                setChanged();
                notifyObservers(reinforcement);
            }
            else
            {
                for (Card card : tradeCard.getItems())
                {
                    yourCard.getItems().add(card);
                }
                tradeCard.getItems().clear();
                actions.addAction("Invalid cards");
            }
        }
        else
        {
            actions.addAction("you only can exchange 3 cards once");
        }
    }

    /**
     * This method handles skipping of card exchange
     */
    @FXML
    public void skipExchangeHandler()
    {
        if (yourCard.getItems().size() + tradeCard.getItems().size() >= 5)
        {
            actions.addAction("5+ cards == you must exchange");
        }
        else if (PlayerPhaseModel.getPlayerModel().getCurrentPlayer().checkIfCardsMaximum())
        {
            actions.addAction("5+ cards == you must exchange");
        }
        else
        {
            setChanged();
            notifyObservers();
        }
    }

}
