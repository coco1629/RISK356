package View;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

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

    @FXML
    void LoginGame(ActionEvent event) throws IOException {
        //check the user name, if success
        Parent main = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
        Scene scene = new Scene(main);
        Stage previous = (Stage)loginRoot.getScene().getWindow();
        previous.setScene(scene);
        previous.show();

    }

    @FXML
    void register(ActionEvent event) {
        // if success, set 'success' visible then
        success.setVisible(true);
    }

}
