package Application;

import View.LoginView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;


public class RiskGame extends Application {
    private String topic;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass()
                    .getResource("/view/LoginView.fxml"));
            ComboBox theme = (ComboBox)root.lookup("#theme");
            theme.getItems().addAll("Dark Theme", "Light Theme");
            theme.setOnAction((event -> {
//                topic = (String) theme.getValue();
            System.out.println(theme.getValue());}));

            primaryStage.setResizable(false);
            primaryStage.setTitle("Risk Game");
            Scene scene = new Scene(root);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }




    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
