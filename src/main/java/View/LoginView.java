package View;


import Connection.ClientHandler;
import JDBC.User;
import JDBC.UserDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginView{
    @FXML
    private Button login;
    @FXML
    private TextField password;
    @FXML
    private TextField user;

    @FXML
    private Button register;

    @FXML
    private ImageView success;

    @FXML
    private AnchorPane loginRoot;

    private ArrayList<TextField> textFieldArrayList;

    UserDao dao = new UserDao();

    @FXML
    void LoginGame(ActionEvent event) throws IOException {
        //check the user name, if success
        String username = user.getText();
        String passwords = password.getText();
        try{
            if(!dao.findUserbyid(username)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.titleProperty().set("Error");
                alert.headerTextProperty().set("Cannot find the account. Please register first!");
                alert.showAndWait();
                return;
            }
            if(dao.checkLogin(username,passwords)){
                Parent main = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
                Scene scene = new Scene(main);
                Stage previous = (Stage)loginRoot.getScene().getWindow();
                previous.setResizable(false);
                previous.setScene(scene);
                previous.show();
            }

        }
        catch (Exception e){
            e.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.titleProperty().set("Error");
//            alert.headerTextProperty().set("Password invalid.");
//            alert.showAndWait();
        }



//        ClientHandler clientHandler1 = new ClientHandler();
//        ClientHandler clientHandler2 = new ClientHandler();
//        ClientHandler clientHandler3 = new ClientHandler();
//        clientHandler1.sendObject("client 1 send");
//        clientHandler2.sendObject("client 2 send");
//        clientHandler3.sendObject("client 3 send");


    }

    @FXML
    void register(ActionEvent event) throws IOException {
        // if success, set 'success' visible then

        String username = user.getText();
        String passwords = password.getText();
        try {
            // this need
            dao.insertUser(new User(123,username,passwords));
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("Error");
            alert.headerTextProperty().set("Register failed.");
            alert.showAndWait();
            return;
        }
        success.setVisible(true);
    }

}
