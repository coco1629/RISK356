package View;


import Connection.ClientHandler;
import JDBC.User;
import JDBC.UserDao;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginView implements Initializable {
    @FXML
    private Button login;


    @FXML
    private PasswordField password;

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/JoinView.fxml"));
                Parent main = loader.load();
                Scene scene = new Scene(main);
                scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                JoinController controller = loader.getController();
                controller.setUserName(username);
                Stage previous = (Stage)loginRoot.getScene().getWindow();
                previous.setResizable(false);
                previous.setScene(scene);
                previous.show();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    void register(ActionEvent event) throws IOException {
        // if success, set 'success' visible then

        String username = user.getText();
        String passwords = password.getText();
        try {
            // this need
            if(!dao.findUserbyid(username))
                dao.insertUser(new User(username,passwords));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login.getStyleClass().addAll("btn","btn-success");
        register.getStyleClass().addAll("btn","btn-warning");
    }
}
