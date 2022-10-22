package group.riskgame.Application.View;

import group.riskgame.Application.Model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InviteController implements Initializable {


    @FXML
    private Button acceptButton;

    @FXML
    private ListView<String> inviteList;

    @FXML
    private Button invite;

    @FXML
    private Button next;

    @FXML
    private ListView<String> userList;

    private Player currentplayer;

    private ArrayList<String> currentPlayers;

    private String userName;

    @FXML
    private Button refreshButton;


    public InviteController(){

    }


    @FXML
    void sendInvitation(ActionEvent event) {
        currentplayer.getClientHandler().sendObject("send_Invite," + userList.getSelectionModel().getSelectedItem());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    void acceptInvite(ActionEvent event) {

    }

    @FXML
    void createGame(ActionEvent event) {

    }

    @FXML
    void checkInvite(ActionEvent event) {
        currentplayer.getClientHandler().readObject();
        ObservableList<String> invites = FXCollections.observableArrayList(currentplayer.getClientHandler().getInvitePlayers());
        inviteList.setItems(invites);
    }


    public void showLists(){
        this.currentPlayers = currentplayer.getClientHandler().getCurrentPlayers();
        ObservableList<String> items = FXCollections.observableArrayList(this.currentPlayers);
        ObservableList<String> invites = FXCollections.observableArrayList(currentplayer.getClientHandler().getInvitePlayers());
        userList.setItems(items);
        inviteList.setItems(invites);

    }

    @FXML
    void updateList(ActionEvent event) throws IOException {
        currentplayer.getClientHandler().sendObject("request_current_players,"+currentplayer.getClientHandler().getUsername());
        currentplayer.getClientHandler().readObject();
        showLists();
    }

    public Player getCurrentplayer() {
        return currentplayer;
    }

    public void setCurrentplayer(Player currentplayer) {
        this.currentplayer = currentplayer;
    }

}
