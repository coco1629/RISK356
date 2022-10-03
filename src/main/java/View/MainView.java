package View;

import Connection.GameBoard;
import Connection.Operation;
import Model.Country;
import Model.Player;
import Model.Territory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainView implements Initializable {
    @FXML
    private Button AttackPhase;

    @FXML
    private Button DefensePhase;

    @FXML
    private Pane Pane;

    @FXML
    private Label PlayerName1;

    @FXML
    private Label PlayerName2;

    @FXML
    private Label PlayerName3;

    @FXML
    private Label PlayerName4;

    @FXML
    private Label PlayerName5;

    @FXML
    private Label PlayerName6;

    @FXML
    private Button SkipPhase;

    @FXML
    private Button TransferPhase;

    @FXML
    private ImageView imageView;

    @FXML
    private Button nextPhase;

    @FXML
    private Spinner<Integer> numBox;

    @FXML
    private Button occupyButton;

    @FXML
    private Pane panePlayerColor1;

    @FXML
    private Pane panePlayerColor2;

    @FXML
    private Pane panePlayerColor3;

    @FXML
    private Pane panePlayerColor4;

    @FXML
    private Pane panePlayerColor5;

    @FXML
    private Pane panePlayerColor6;

    @FXML
    private AnchorPane rootPane;

    private SvgUtil svgUtil;

    private Player player;

    private Color color;

    private String roomName;

//    private int playerNum;
//
//    private int currentNum = 0;

    private int troops = 10;

    public MainView(){
        svgUtil = new SvgUtil();
//        SvgUtil.setPressedColorCode("#59a869");
//        SvgUtil.setPressedColorCode(player.getColor());
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
        svgUtil.getSelectedPath().getText().setText(String.valueOf(numBox.getValue()));
        svgUtil.getSelectedPath().setOccupied(true);
        Country country = svgUtil.getSelectedCountry();
        country.setPopulation(numBox.getValue());
        this.player.addToOccupiedCountries(country);
//        this.player.occupyCountry(svgUtil.getSelectedCountry(),numBox.getValue(),roomName);

    }

    @FXML
    void nextPhase(ActionEvent event) {
//        System.out.println("next");
//        System.out.println(this.player.getClientHandler());
        this.player.getClientHandler().sendObject(Operation.OCCUPY);
//        System.out.println("send occupy");
//        this.player.getClientHandler().sendObject(roomName);
        this.player.getClientHandler().sendObject(this.player.getName());
//        for (Country country : this.player.getOccupiedCountries()){
//            System.out.println("country name: " + country.getName() + " num: " + country.getPopulation());
//        }
        this.player.getClientHandler().sendObject(this.player.getOccupiedCountries().size());
        for(int i = 0; i < this.player.getOccupiedCountries().size(); i++){
            Country country =this.player.getOccupiedCountries().get(i);
//            System.out.println(country.getName() + "troops num " + country.getPopulation());
            this.player.getClientHandler().sendObject(country);
            this.player.getClientHandler().sendObject(country.getPopulation());
//            System.out.println("country name: " + country.getName() + " num: " + country.getPopulation());
        }

//        this.player.occupyCountries(this.player.getOccupiedCountries(),numBox.getValue(),roomName);
//        this.player.getClientHandler().sendObject(roomName);
//        this.player.getClientHandler().sendObject(Operation.UPDATE);
//        Object object =  this.player.getClientHandler().readObject();
//        System.out.println(object.toString());
//        this.player.getClientHandler().sendObject(Operation.UPDATE);
        int num = (int) this.player.getClientHandler().readObject();
        ArrayList<Territory> obj = new ArrayList<>();
        for(int i = 0; i <num; i++){
            Territory territory = (Territory) this.player.getClientHandler().readObject();
            obj.add(territory);
        }
//        Object array =  this.player.getClientHandler().readObject();
//        ArrayList<Territory> obj = (ArrayList<Territory>)array;
//        String name = (String) obj[0];
//        Country country = (Country) obj[1];
//        int num = (int) obj[2];
        for(Territory territory: obj){
//            System.out.println(territory.getName() + " " + territory.getOwner() + " num: " + territory.getNum());
            Country country = Country.valueOf(territory.getName());
            country.setPopulation(territory.getNum());
//            System.out.println(this.player.getPlayersColorMap());
            svgUtil.setPathColor(this.player.getPlayersColorMap().get(territory.getOwner()),country);
            svgUtil.setCountryTroops(country,territory.getNum());
        }
    }

    @FXML
    void skipPhase(ActionEvent event) {

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

    public void setPlayersNumOnPane(){
        System.out.println(this.player.getName());
//        System.out.println(this.player.getAllPlayers().size());
        switch (this.player.getAllPlayers().size()){
            case 2:
                PlayerName3.setVisible(false);
                panePlayerColor3.setVisible(false);
                PlayerName4.setVisible(false);
                panePlayerColor4.setVisible(false);
                PlayerName5.setVisible(false);
                panePlayerColor5.setVisible(false);
                PlayerName6.setVisible(false);
                panePlayerColor6.setVisible(false);
                break;
            case 3:
                PlayerName4.setVisible(false);
                panePlayerColor4.setVisible(false);
                PlayerName5.setVisible(false);
                panePlayerColor5.setVisible(false);
                PlayerName6.setVisible(false);
                panePlayerColor6.setVisible(false);
                break;
            case 4:
                PlayerName5.setVisible(false);
                panePlayerColor5.setVisible(false);
                PlayerName6.setVisible(false);
                panePlayerColor6.setVisible(false);
            case 5:
                PlayerName6.setVisible(false);
                panePlayerColor6.setVisible(false);

        }
    }



    public SvgUtil getSvgUtil() {
        return svgUtil;
    }

//    @Override
//    public void AddControllerListener(GameController controller){
//        super.AddControllerListener(controller);
//        this.getSvgUtil().setController(controller);
//    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
//        System.out.println(String.valueOf(this.color));
        SvgUtil.setPressedColorCode(String.valueOf(this.color));
    }

    public String getRoomName() {
        return roomName;
    }


    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
