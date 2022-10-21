package View;


import Application.RiskGame;
import Connection.ClientHandler;
import JDBC.User;
import JDBC.UserDao;
import Model.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginView implements Initializable{


    @FXML
    private Button login;

    @FXML
    public ComboBox<String> theme;

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

    private String style = "light";

    private ArrayList<TextField> textFieldArrayList;

    UserDao dao = new UserDao();

    public LoginView() throws IOException {
    }


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
                if(style.equals("light")){
                    scene.getStylesheets().add("view/css/JoinLightCss.css");
                }
                if(style.equals("dark")){
                    scene.getStylesheets().add("view/css/JoinDarkCss.css");
                }
                JoinController controller = loader.getController();
                controller.setStyle(style);
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
        theme.getItems().addAll("Dark Theme", "Light Theme");

    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
