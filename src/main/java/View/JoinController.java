package View;

import Connection.Operation;
import Connection.SessionData;
import Model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class JoinController {

    @FXML
    private AnchorPane root;

    @FXML
    private Button createButton;

    @FXML
    private Button joinButton;

    @FXML
    private TextField roomName;

    @FXML
    private TextField roomSize;

    @FXML
    private ListView<String> roomList;


    @FXML
    private Button showButton;

    private Player currentPlayer;

    private String userName;

    @FXML
    void createSession(ActionEvent event) throws IOException {
        this.currentPlayer = new Player(userName);
        currentPlayer.getClientHandler().sendObject(Operation.CREATE_SESSION);
        currentPlayer.getClientHandler().sendObject(roomName.getText());
        SessionData data = new SessionData(currentPlayer.getName(),Operation.CREATE_SESSION,Integer.parseInt(roomSize.getText()));
        currentPlayer.getClientHandler().sendObject(data);
//        Stage secondStage = new Stage();
//        Label label = new Label("Waiting for players...");
//        StackPane pane = new StackPane(label);
//        Scene secondScene = new Scene(pane,200,150);
//        secondStage.show();
        changeScene();
    }

    @FXML
    void joinSession(ActionEvent event) throws IOException {
        System.out.println(currentPlayer.getName());
        currentPlayer.getClientHandler().sendObject(Operation.JOIN_SESSION);
        System.out.println("join session");
        currentPlayer.getClientHandler().sendObject(roomList.getSelectionModel().getSelectedItem());
        currentPlayer.getClientHandler().sendObject(userName);
        changeScene();
    }


    @FXML
    void showRoom(ActionEvent event) throws IOException {
        this.currentPlayer = new Player(userName);
        currentPlayer.getClientHandler().sendObject(Operation.SHOW_ROOMS);
        System.out.println("send show rooms");
        ArrayList<String> rooms = (ArrayList<String>) currentPlayer.getClientHandler().readObject();
        ObservableList<String> roomItems = FXCollections.observableArrayList(rooms);
        roomList.setItems(roomItems);
    }

    public void changeScene() throws IOException {
//        ArrayList<Object> playersList = currentPlayer.getClientHandler().startListeningOrders();
//        if(playersList != null){
//            secondStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
            Parent main = loader.load();
            Scene scene = new Scene(main);
            MainView controller = loader.getController();
            controller.setPlayer(currentPlayer);
            Stage previous = (Stage)root.getScene().getWindow();
            previous.setResizable(false);
            previous.setScene(scene);
            previous.show();
//        }

    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}