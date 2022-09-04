package View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainView extends ViewBase{
    @FXML
    private Button AttackPhase;

    @FXML
    private Button DefensePhase;

    @FXML
    private Pane Pane;

    @FXML
    private Label PlauerName1;

    @FXML
    private Label PlauerName2;

    @FXML
    private Label PlauerName3;

    @FXML
    private Label PlauerName4;

    @FXML
    private Button SkipPhase;

    @FXML
    private Button TransferPhase;

    @FXML
    private ImageView imageView;

    @FXML
    private Pane panePlayerColor1;

    @FXML
    private Pane panePlayerColor2;

    @FXML
    private Pane panePlayerColor3;

    @FXML
    private Pane panePlayerColor4;

    @FXML
    private AnchorPane rootPane;

    private SvgUtil svgUtil;

    public MainView(){
        svgUtil = new SvgUtil();

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Pane pane = svgUtil.getPane();
        pane.setScaleX(1.2);
        pane.setScaleY(1.2);
        pane.setLayoutX(-140);
        pane.setLayoutY(-50);
        rootPane.getChildren().addAll(pane);
    }
}
