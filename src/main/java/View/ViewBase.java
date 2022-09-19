package View;

import Controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewBase implements Initializable {
    protected Parent root;
    protected GameController controller;

    public ViewBase() {}

    protected void LoadFXML(String resourceName)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(resourceName));
        fxmlLoader.setController(this);

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parent getRoot() { return root; }


    public void AddControllerListener(GameController controller){
        this.controller = controller;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
}
