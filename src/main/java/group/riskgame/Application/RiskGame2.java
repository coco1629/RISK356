package group.riskgame.Application;

import group.riskgame.Application.View.LoginView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;


public class RiskGame2 extends Application {
    private String topic;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Read file fxml and draw interface.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/group.riskgame.Application/LoginView.fxml"));
            Parent root = loader.load();
            primaryStage.setResizable(false);
            primaryStage.setTitle("Risk Game");
            LoginView loginController = loader.getController();
            final String[] style = {"light"};
            Scene scene = new Scene(root);
            ComboBox<String> theme =loginController.theme;
//            System.out.println(getClass().getResource("/group.riskgame.Application/view/css/LoginViewLightCss.css").getPath());
            scene.getStylesheets().setAll(getClass().getResource("/group.riskgame.Application/css/LoginViewLightCss.css").toExternalForm());
            theme.setOnAction((event -> {
                String topic = (String) theme.getValue();
                if(topic.equals("Dark Theme")){
                    style[0] = "dark";
                    scene.getStylesheets().setAll(getClass().getResource("/group.riskgame.Application/css/LoginViewCss.css").toExternalForm());
                    loginController.setStyle("dark");
                }
                if(topic.equals("Light Theme")){
                    style[0] = "light";
                    scene.getStylesheets().removeAll();
                    scene.getStylesheets().setAll(getClass().getResource("/group.riskgame.Application/css/LoginViewLightCss.css").toExternalForm());
                    loginController.setStyle("light");
                }
            }));
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
