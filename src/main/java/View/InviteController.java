package View;

import Connection.Server;
import Connection.ServerHandler;
import JDBC.UserDao;
import Model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

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


    public InviteController(){

    }


    @FXML
    void sendInvitation(ActionEvent event) {
        currentplayer.getClientHandler().sendObject("sendInvite");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        try {
////            ArrayList<String> players = new UserDao().getUsers();
//            ArrayList<String> players = new ArrayList<>();
//            for(ServerHandler serverHandler: Server.currentServerThreads){
//                players.add(serverHandler.getClientName());
//            }
////            System.out.println(players);
//            ObservableList<String> items = FXCollections.observableArrayList(players);
//            System.out.println(currentplayer);
//            ObservableList<String> invites = FXCollections.observableArrayList(currentplayer.getClientHandler().getInvitePlayers());
//            userList.setItems(items);
//            inviteList.setItems(invites);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }


    @FXML
    void acceptInvite(ActionEvent event) {

    }

    @FXML
    void createGame(ActionEvent event) {

    }

    public void showLists(){
        try {
//            ArrayList<String> players = new UserDao().getUsers();
            ArrayList<String> players = new ArrayList<>();
            System.out.println(Server.currentServerThreads.size());
            for(ServerHandler serverHandler: Server.currentServerThreads){
                players.add(serverHandler.getClientName());
            }
            ObservableList<String> items = FXCollections.observableArrayList(players);
            System.out.println(currentplayer);
            ObservableList<String> invites = FXCollections.observableArrayList(currentplayer.getClientHandler().getInvitePlayers());
            userList.setItems(items);
            inviteList.setItems(invites);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Player getCurrentplayer() {
        return currentplayer;
    }

    public void setCurrentplayer(Player currentplayer) {
        this.currentplayer = currentplayer;
    }
}
