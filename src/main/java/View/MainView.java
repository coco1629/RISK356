package View;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

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
    private BorderPane borderPane;
    @FXML
    private StackPane centerStackPane;
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
}
