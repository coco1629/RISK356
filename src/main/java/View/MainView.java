package View;

import Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.awt.event.MouseEvent;
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
    private Button nextPhase;

    @FXML
    private Spinner<Integer> numBox;

    @FXML
    private Button occupyButton;

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

    private Player player;

    private int troops = 10;

    public MainView(){
        svgUtil = new SvgUtil();
        SvgUtil.setPressedColorCode("#c3d6e8");
//        player = new Player(Color.PINK,"ww");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        Group group = svgUtil.getGroup();
        group.setScaleX(1.2);
        group.setScaleY(1.2);
        group.setLayoutX(-140);
        group.setLayoutY(-50);

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,50,1);
        numBox.setValueFactory(valueFactory);

//        group.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler(){
//
//                }
//        );
//        group.getChildren().get(0).addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
//
//        });
        rootPane.getChildren().addAll(group);

    }

    @FXML
    void occupy(ActionEvent event) {

        svgUtil.getSelectedCountry().setPopulation(numBox.getValue());
//        System.out.println(svgUtil.getSelectedCountry().getPopulation());
        svgUtil.getSelectedPath().getText().setText(String.valueOf(numBox.getValue()));
        svgUtil.getSelectedPath().setOccupied(true);

    }

    @FXML
    void checkNum(MouseEvent event) {


    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public SvgUtil getSvgUtil() {
        return svgUtil;
    }

//    @Override
//    public void AddControllerListener(GameController controller){
//        super.AddControllerListener(controller);
//        this.getSvgUtil().setController(controller);
//    }


}
