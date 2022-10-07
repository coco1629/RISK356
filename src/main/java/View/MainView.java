package View;

import Connection.Operation;
import Model.Country;
import Model.Player;
import Model.Territory;
import Model.currentProcess;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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

    @FXML
    private Label phase;

    private SvgUtil svgUtil;

    private Player player;

    private Color color;

    private String roomName;

    @FXML
    private Label troopsNum;

    @FXML
    private Label instructions;

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
        rootPane.getChildren().addAll(group);

    }

    @FXML
    void occupy(ActionEvent event) {

        if(svgUtil.getSelectedPath() != null){
            if(numBox.getValue() <= this.player.getAllowedTroops()){
                if(this.player.getPhase() == currentProcess.Fortify || this.player.getPhase() == currentProcess.Preparation){
                    svgUtil.getSelectedCountry().setPopulation(numBox.getValue());
                    svgUtil.getSelectedPath().getText().setText(String.valueOf(numBox.getValue()));
                    svgUtil.getSelectedPath().setOccupied(true);
                    Country country = svgUtil.getSelectedCountry();
                    country.setPopulation(numBox.getValue());
                    this.player.setAllowedTroops(this.player.getAllowedTroops() - numBox.getValue());
                    troopsNum.setText(String.valueOf(this.player.getAllowedTroops()));
                    this.player.addToOccupiedCountries(country);
                }

                if(this.player.getPhase() == currentProcess.Attack){

                }

                if(this.player.getPhase() == currentProcess.Fortify){


                }

            }

        }

    }

    @FXML
    void nextPhase(ActionEvent event) {

        this.player.getClientHandler().sendObject(Operation.OCCUPY);
        this.player.getClientHandler().sendObject(this.player.getName());
        this.player.getClientHandler().sendObject(this.player.getOccupiedCountries().size());
        for(int i = 0; i < this.player.getOccupiedCountries().size(); i++){
            Country country =this.player.getOccupiedCountries().get(i);
//            System.out.println(country.getName() + "troops num " + country.getPopulation());
            this.player.getClientHandler().sendObject(country);
            this.player.getClientHandler().sendObject(country.getPopulation());
//            System.out.println("country name: " + country.getName() + " num: " + country.getPopulation());
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
//        System.out.println(this.player.getName());
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
        troopsNum.setText(String.valueOf(this.player.getAllowedTroops()));
        new Thread(() ->{
            while(true){
                boolean isUpdated = this.player.getClientHandler().receiveUpdated();
//                System.out.println(isUpdated);
                if(isUpdated){
                    int num = (int) this.player.getClientHandler().readObject();
                    ArrayList<Territory> obj = new ArrayList<>();
                    for(int i = 0; i <num; i++){
                        Territory territory = (Territory) this.player.getClientHandler().readObject();
                        obj.add(territory);
                    }
                    for(Territory territory: obj){
                        Country country = Country.valueOf(territory.getName());
//                        System.out.println(country.getName());
                        country.setPopulation(territory.getNum());
                        svgUtil.setPathColor(this.player.getPlayersColorMap().get(territory.getOwner()),country);
                        svgUtil.setCountryTroops(country,territory.getNum());
//                        if(territory.getOwner().equals(this.player.getName())){
//                            this.player.getAllPlayers(
//                        }
//                        System.out.println("updated");
                    }
                    if(obj.size() == 2 && this.player.getPhase() == currentProcess.Preparation){
                        this.player.nextPhase();
                        Platform.runLater(()-> phase.setText(this.player.getPhase().toString()));
//                        System.out.println(this.player.getPhase());
                    }
                }
            }

        }).start();

    }

    public boolean isWin(){
        return this.player.getOccupiedCountries().size() == 42;
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
