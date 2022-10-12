package View;

import Connection.Operation;
import Model.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainView implements Initializable {
    @FXML
    private Button AttackPhase;

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
    private ListView<String> log;

    private int leftTroops;

    @FXML
    private Label instructions;

    private ArrayList<String> logs = new ArrayList<>();

    private ArrayList<Territory> territories = new ArrayList<>();

    private boolean waitForNext = false;

//    private int playerNum;
//
//    private int currentNum = 0;

    private int troops = 10;

    public MainView(){
        svgUtil = new SvgUtil();

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
        ObservableList<String> logItems = FXCollections.observableArrayList(logs);
        log.setItems(logItems);
    }

    @FXML
    void occupy(ActionEvent event) {

        if(svgUtil.getSelectedPath() != null){
            if(numBox.getValue() <= this.player.getAllowedTroops()){
                if(this.player.getPhase() == currentProcess.Preparation){
                    svgUtil.getSelectedCountry().setPopulation(numBox.getValue());
                    svgUtil.getSelectedPath().getText().setText(String.valueOf(numBox.getValue()));
                    svgUtil.getSelectedPath().setOccupied(true);
                    Country country = svgUtil.getSelectedCountry();
                    country.setPopulation(numBox.getValue());
                    this.player.setAllowedTroops(this.player.getAllowedTroops() - numBox.getValue());
                    troopsNum.setText(String.valueOf(this.player.getAllowedTroops()));
                    this.player.addToOccupiedCountries(country);
                }

            }

        }
        if(this.player.getPhase() == currentProcess.Preparation){
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
        if(this.player.getPhase() == currentProcess.Reinforcement){
            svgUtil.getSelectedCountry().setPopulation(numBox.getValue() + svgUtil.getSelectedCountry().getPopulation());
            this.player.setAllowedTroops(this.player.getAllowedTroops() - numBox.getValue());
            troopsNum.setText(String.valueOf(this.player.getAllowedTroops()));
            Country country = svgUtil.getSelectedCountry();
            for(Territory territory: territories){
                if(territory.getName().equals(country.getName())){
                    territory.setNum(territory.getNum() + numBox.getValue());
                    svgUtil.getSelectedPath().getText().setText(String.valueOf(territory.getNum()));
                }
            }
            this.player.getClientHandler().sendObject(Operation.REINFORCE);
            this.player.getClientHandler().sendObject(territories);
        }

    }

    @FXML
    void nextPhase(ActionEvent event) {

        if(this.player.getPhase() == currentProcess.Attack || this.player.getPhase() == currentProcess.Fortify || this.player.getPhase() == currentProcess.Reinforcement){
            this.player.getClientHandler().sendObject(Operation.NEXT_PHASE);
            waitForNext = true;
            if(this.player.getPhase() == currentProcess.Fortify){
                this.player.getClientHandler().sendObject(SvgUtil.getCountryContinentHashMap());
            }
        }

    }


    @FXML
    void attack(ActionEvent event) {
        try {

            if(svgUtil.getTwoSelectedPaths().size() == 2){
//                CountryPath attackCountry =
                CountryPath attackCountryPath = svgUtil.getTwoSelectedPaths().get(0);
                String attackCountryName = attackCountryPath.getName();
                CountryPath defendCountryPath = svgUtil.getTwoSelectedPaths().get(1);
                String defendCountryName = defendCountryPath.getName();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Dice.fxml"));
                Parent main = loader.load();
                Scene scene = new Scene(main);
                DiceController controller = loader.getController();
                controller.setAttacker(this.player.getName());
                String defender = "";
                for(int i = 0; i < territories.size();i++){
                    if(territories.get(i).getName().equals(defendCountryPath.getName())){
                        defender = territories.get(i).getOwner();
                        controller.setDefender(defender);
//                        System.out.println("defender:" + territories.get(i).getOwner());
                        break;
                    }
                }
                controller.setAttackNum(Integer.parseInt(attackCountryPath.getText().getText()));
//                System.out.println("attack num" + attackCountryPath.getText().getText());
                controller.setDefendNum(Integer.parseInt(defendCountryPath.getText().getText()));
//                System.out.println("defend num" + defendCountryPath.getText().getText());
                controller.initDice();
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
                final boolean[] isWin = {false};
                final boolean[] isDraw = {true};
                final int[] attackerNum = {0};
                final int[] defenderNum = {0};
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        isDraw[0] = Objects.equals(controller.getWinner(), "");
                        isWin[0] = Objects.equals(controller.getWinner(), player.getName());
                        attackerNum[0] = controller.getAttackNum();
                        defenderNum[0] = controller.getDefendNum();
                        if(isWin[0]){
                            for(int i = 0; i < territories.size();i++){
                                if(territories.get(i).getName().equals(defendCountryPath.getName())){
                                    territories.get(i).setOwner(player.getName());
                                    territories.get(i).setNum(1);
                                }
                                if(territories.get(i).getName().equals(attackCountryPath.getName())){
//                            territories.get(i).setOwner(this.player.getName());
                                    territories.get(i).setNum(attackerNum[0]-1);
                                }
                            }
                        }
                        if(isDraw[0]){
                            for(int i = 0; i < territories.size();i++){
                                if(territories.get(i).getName().equals(defendCountryPath.getName())){
//                            territories.get(i).setOwner(this.player.getName());
                                    territories.get(i).setNum(defenderNum[0]);
                                }
                                if(territories.get(i).getName().equals(attackCountryPath.getName())){
//                            territories.get(i).setOwner(this.player.getName());
                                    territories.get(i).setNum(attackerNum[0]);
                                }
                            }
                        }
                        if (!isWin[0] && !isDraw[0]){
                            for(int i = 0; i < territories.size();i++){
                                if(territories.get(i).getName().equals(defendCountryPath.getName())){
                                    territories.get(i).setNum(defenderNum[0]);
                                }
                                if(territories.get(i).getName().equals(attackCountryPath.getName())){
//                                    territories.get(i).setOwner(controller.getDefender());
//                                    System.out.println(controller.getDefender());
                                    territories.get(i).setNum(1);
                                }
                            }
                        }
                        for( CountryPath p : svgUtil.getTwoSelectedPaths()){
                            p.setStrokeWidth(1);
                        }
                        svgUtil.setTwoSelectedPaths(new ArrayList<CountryPath>());
                        svgUtil.deleteArrow();
                        player.getClientHandler().sendObject(Operation.ATTACK);
                        player.getClientHandler().sendObject(territories);

                    }
                });

//                this.player.getClientHandler().sendObject(defender);
//                this.player.getClientHandler().sendObject(this.player.getName() + " " + attackCountryName + " attacked your " + defendCountryName);


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void transfer(ActionEvent event) {
        if(this.player.getPhase() == currentProcess.Fortify){
            if(svgUtil.getTwoSelectedPaths().size() == 2){
                CountryPath fromPath = svgUtil.getTwoSelectedPaths().get(0);
                CountryPath toPath = svgUtil.getTwoSelectedPaths().get(1);
                boolean success = true;
                for(Territory territory: territories){
                    if(territory.getName().equals(fromPath.getName())){
                        if(territory.getNum() > numBox.getValue())
                            territory.setNum(territory.getNum() - numBox.getValue());
                        else{
                            success = false;
//                            System.out.println("fail to transfer");
                        }

                    }
                }

                if(success){
//                    System.out.println("transfer success");
                    for(Territory territory: territories){
                        if(territory.getName().equals(toPath.getName())){
                            territory.setNum(territory.getNum() + numBox.getValue());
                        }
                    }
                }

                this.player.getClientHandler().sendObject(Operation.FORTIFY);
                this.player.getClientHandler().sendObject(territories);
                for( CountryPath p : svgUtil.getTwoSelectedPaths()){
                    p.setStrokeWidth(1);
                }
                svgUtil.setTwoSelectedPaths(new ArrayList<CountryPath>());
                svgUtil.deleteArrow();

            }
        }
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
        svgUtil.setCurrentPlayer(this.player);
        new Thread(() ->{
            while(true){
                boolean isUpdated = this.player.getClientHandler().receiveUpdated();
                if(this.player.getPhase() == currentProcess.Fortify && waitForNext && !isUpdated){
                    int newNum = (int) this.player.getClientHandler().readObject();
                    this.player.setAllowedTroops(this.player.getAllowedTroops() + newNum);
                    Platform.runLater(()->troopsNum.setText(String.valueOf(this.player.getAllowedTroops())));
                }
//                System.out.println(isUpdated);
                if(isUpdated){
                    int num = (int) this.player.getClientHandler().readObject();
                    ArrayList<Territory> obj = new ArrayList<>();
                    for(int i = 0; i <num; i++){
                        Territory territory = (Territory) this.player.getClientHandler().readObject();
                        obj.add(territory);
                    }
                    this.territories = obj;
                    this.player.setTerritories(obj);
                    svgUtil.setTerritories(territories);

                    for(Territory territory: obj){
                        Country country = Country.valueOf(territory.getName());
                        if(this.player.getPhase() == currentProcess.Preparation){
//                            updateLeftTroops();
                            leftTroops = Integer.parseInt(troopsNum.getText());
                            for(Territory t : this.territories){
                                if(this.player.getOccupiedCountries().contains(Country.valueOf(t.getName())) && !Objects.equals(t.getOwner(), this.player.getName())){
                                    leftTroops = leftTroops + Country.valueOf(t.getName()).getPopulation();
//                                    System.out.println(leftTroops);
                                    ArrayList<Country> updated = this.player.getOccupiedCountries();
                                    updated.remove(Country.valueOf(t.getName()));
                                    this.player.setOccupiedCountries(updated);
                                    this.player.setAllowedTroops(leftTroops);
//                System.out.println(Country.valueOf(t.getName()).getPopulation());
                                }
                            }
//                            troopsNum.setText(String.valueOf(this.player.getAllowedTroops()));
//                            System.out.println(this.player.getAllowedTroops());
                            Platform.runLater(()-> troopsNum.setText(String.valueOf(this.player.getAllowedTroops())));
                        }
//                        System.out.println(country.getName());
                        country.setPopulation(territory.getNum());
                        svgUtil.setPathColor(this.player.getPlayersColorMap().get(territory.getOwner()),country);
                        svgUtil.setCountryTroops(country,territory.getNum());
//                        if(territory.getOwner().equals(this.player.getName())){
//                            this.player.getAllPlayers(
//                        }
//                        System.out.println("updated");
                    }
                    if(obj.size() >= 42 && this.player.getPhase() == currentProcess.Preparation){
                        this.player.nextPhase();
                        svgUtil.unselectAllPaths();
                        Platform.runLater(()-> phase.setText(this.player.getPhase().toString()));
                        svgUtil.setPhase(this.player.getPhase());
//                        System.out.println(this.player.getPhase());
                    }
                    if(waitForNext){
                        boolean isNext = this.player.getClientHandler().receiveUpdated();
//                        System.out.println("isNext" + isNext);
                        if(isNext){
                            this.player.nextPhase();
//                            System.out.println("fortify");
                            svgUtil.unselectAllPaths();
                            Platform.runLater(()-> phase.setText(this.player.getPhase().toString()));
                            svgUtil.setPhase(this.player.getPhase());
                            svgUtil.setTwoSelectedPaths(new ArrayList<>());
                            waitForNext = false;
                        }
                    }

                }
            }

        }).start();

    }

    public void updateLeftTroops(){
        leftTroops = Integer.parseInt(troopsNum.getText());
        for(Territory t : this.territories){
            if(this.player.getOccupiedCountries().contains(Country.valueOf(t.getName())) && !Objects.equals(t.getOwner(), this.player.getName())){
                leftTroops = leftTroops + Country.valueOf(t.getName()).getPopulation();
                System.out.println(leftTroops);
                ArrayList<Country> updated = this.player.getOccupiedCountries();
                updated.remove(Country.valueOf(t.getName()));
                this.player.setOccupiedCountries(updated);
                this.player.setAllowedTroops(leftTroops);
//                System.out.println(Country.valueOf(t.getName()).getPopulation());
            }
        }
        troopsNum.setText(String.valueOf(this.player.getAllowedTroops()));


    }

    public boolean isWin(){
        return this.player.getOccupiedCountries().size() == 42;
    }


    public SvgUtil getSvgUtil() {
        return svgUtil;
    }


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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }




}
