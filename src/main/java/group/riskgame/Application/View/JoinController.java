package group.riskgame.Application.View;

import group.riskgame.Application.Connection.Operation;
import group.riskgame.Application.Connection.SessionData;
import group.riskgame.Application.Model.Player;
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

    private String style;

    private String roomNameStr;

    @FXML
    void createSession(ActionEvent event) throws IOException {
        this.currentPlayer = new Player(userName);
        currentPlayer.getClientHandler().sendObject(Operation.CREATE_SESSION);
        currentPlayer.getClientHandler().sendObject(roomName.getText());
        this.roomNameStr = roomName.getText();
        SessionData data = new SessionData(userName,Operation.CREATE_SESSION,Integer.parseInt(roomSize.getText()));
        currentPlayer.getClientHandler().sendObject(data);
        Stage secondStage = new Stage();
        Label label = new Label("Waiting for players...");
        StackPane pane = new StackPane(label);
        Scene secondScene = new Scene(pane,200,150);
        secondStage.setScene(secondScene);
        secondStage.show();
        changeScene(secondStage);
    }

    @FXML
    void joinSession(ActionEvent event) throws IOException {
        currentPlayer.getClientHandler().sendObject(Operation.JOIN_SESSION);
        this.roomNameStr = roomList.getSelectionModel().getSelectedItem();
        currentPlayer.getClientHandler().sendObject(this.roomNameStr);
        currentPlayer.getClientHandler().sendObject(userName);
        Stage secondStage = new Stage();
        Label label = new Label("Waiting for players...");
        StackPane pane = new StackPane(label);
        Scene secondScene = new Scene(pane,200,150);
        secondStage.setScene(secondScene);
        secondStage.show();
        changeScene(secondStage);
    }


    @FXML
    void showRoom(ActionEvent event) throws IOException {
        this.currentPlayer = new Player(userName);
        this.currentPlayer.getClientHandler().sendObject(Operation.SHOW_ROOMS);
        ArrayList<String> rooms = (ArrayList<String>) this.currentPlayer.getClientHandler().readObject();
        ObservableList<String> roomItems = FXCollections.observableArrayList(rooms);
        roomList.setItems(roomItems);
    }

    public void changeScene(Stage secondStage) throws IOException {
        ArrayList<Object> playersList = this.currentPlayer.getClientHandler().startListeningOrders();
        int num = (int) this.currentPlayer.getClientHandler().readObject();
        if(playersList != null) {
            secondStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/group.riskgame.Application/MainView.fxml"));
            Parent main = loader.load();
            Scene scene = new Scene(main);
            if(style.equals("light")){
                scene.getStylesheets().add(getClass().getResource("/group.riskgame.Application/css/MainCss.css").toExternalForm());
            }
            if(style.equals("dark")){
                scene.getStylesheets().add(getClass().getResource("/group.riskgame.Application/css/MainDarkCss.css").toExternalForm());
            }
            MainView controller = loader.getController();
            controller.setStyle(style);
            currentPlayer.setAllPlayers(playersList);
            currentPlayer.setAllowedTroops(num);
            controller.setPlayer(currentPlayer);
            controller.setPlayersNumOnPane();
            controller.setColor(currentPlayer.getColor());
            controller.setRoomName(this.roomNameStr);
            Stage previous = (Stage) root.getScene().getWindow();
            previous.setResizable(false);
            previous.setScene(scene);
            previous.show();
        }
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

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

}
