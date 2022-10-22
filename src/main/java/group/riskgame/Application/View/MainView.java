package group.riskgame.Application.View;

import group.riskgame.Application.Connection.Operation;
import group.riskgame.Application.Model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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

    private int leftTroops;

    @FXML
    private Button cardtest;

    private boolean isAuto = false;

    private Stage DiceStage = new Stage();

    private DiceController diceController;

    private EventHandler<WindowEvent> eventHandler;

    @FXML
    private Button autoButton;

    @FXML
    private Label instructions;

    @FXML
    private Label timerLabel;

    @FXML
    private Label targetText;

    @FXML
    private Label leftCountries;

    private static final int STARTTIME = 100;
    private Timeline timeline;
    private final IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
    private String style;
    private ArrayList<Territory> territories = new ArrayList<>();

    private boolean waitForNext = false;

    private int gainedCard = 0;

    private Model model = new Model();
    private CardView card;
    private CheckBox[] cbs;
    @FXML private Button trade;
    @FXML private Button cancelCardView;
    @FXML private Button closeButton;
    @FXML private Label currentPlayerName;
    @FXML private Label textToShow;
    @FXML private VBox cardVbox;


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
        timerLabel.textProperty().bind(timeSeconds.asString());
        new Thread(()->{
            String url1=getClass().getResource("/group.riskgame.Application/music/Journey.mp3").toExternalForm();
            Media media =new Media(url1);
            MediaPlayer mediaPlayer=new MediaPlayer(media);
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
        }).start();

        handleTimer();
    }

    private void updateTime(){
        // increment seconds
        int seconds = timeSeconds.get();
        timeSeconds.set(seconds - 1);
    }

    private void handleTimer(){
        try{
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> updateTime()));
            timeline.setCycleCount(STARTTIME);
            timeSeconds.set(STARTTIME);
            timeline.setOnFinished(e -> autoButton.fire());
            timeline.play();
        }
        catch (Exception e){

        }

    }


    @FXML
    void occupy(ActionEvent event) {

        if(svgUtil.getSelectedPath() != null & !svgUtil.getSelectedPath().isOccupied()){
            if(numBox.getValue() <= this.player.getAllowedTroops()){
                if(this.player.getPhase() == currentProcess.Preparation){
                    svgUtil.getSelectedCountry().setPopulation(numBox.getValue());
                    svgUtil.getSelectedPath().getText().setText(String.valueOf(numBox.getValue()));
                    leftCountries.setText(String.valueOf(Integer.parseInt(leftCountries.getText()) - 1));
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
                this.player.getClientHandler().sendObject(country);
                this.player.getClientHandler().sendObject(country.getPopulation());
            }
            if(gainedCard == 0){
                gainedCard += 1;
                player.addRandomCard();
            }
            timeline.stop();
            handleTimer();
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
            timeline.stop();
            handleTimer();
        }

    }

    @FXML
    void nextPhase(ActionEvent event) {

        if(this.player.getPhase() == currentProcess.Attack || this.player.getPhase() == currentProcess.Fortify || this.player.getPhase() == currentProcess.Reinforcement){
            svgUtil.deleteArrow();
            svgUtil.unselectAllPaths();
            svgUtil.setTwoSelectedPaths(new ArrayList<>());
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
                CountryPath attackCountryPath = svgUtil.getTwoSelectedPaths().get(0);
                String attackCountryName = attackCountryPath.getName();
                CountryPath defendCountryPath = svgUtil.getTwoSelectedPaths().get(1);
                String defendCountryName = defendCountryPath.getName();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/group.riskgame.Application/Dice.fxml"));
                Parent main = loader.load();
                Scene scene = new Scene(main);
                if(style.equals("light")){
                    scene.getStylesheets().add(getClass().getResource("/group.riskgame.Application/css/DiceLightCss.css").toExternalForm());
                }
                if(style.equals("dark")){
                    scene.getStylesheets().add(getClass().getResource("/group.riskgame.Application/css/DiceDarkCss.css").toExternalForm());
                }
                DiceController controller = loader.getController();
                controller.setStyle(style);

                if(isAuto){
                    this.diceController = controller;
                }
                controller.setAttacker(this.player.getName());
                String defender = "";
                for(int i = 0; i < territories.size();i++){
                    if(territories.get(i).getName().equals(defendCountryPath.getName())){
                        defender = territories.get(i).getOwner();
                        controller.setDefender(defender);
                        break;
                    }
                }
                controller.setAttackNum(Integer.parseInt(attackCountryPath.getText().getText()));
                controller.setDefendNum(Integer.parseInt(defendCountryPath.getText().getText()));
                controller.initDice();
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
                if(isAuto){
                    this.DiceStage = stage;
                }
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
                                    territories.get(i).setNum(attackerNum[0]-1);
                                    if(gainedCard == 0){
                                        gainedCard += 1;
                                        player.addRandomCard();
                                    }

                                }
                            }
                        }
                        if(isDraw[0]){
                            for(int i = 0; i < territories.size();i++){
                                if(territories.get(i).getName().equals(defendCountryPath.getName())){
                                    territories.get(i).setNum(defenderNum[0]);
                                }
                                if(territories.get(i).getName().equals(attackCountryPath.getName())){
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
                timeline.stop();
                handleTimer();
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
                        }

                    }
                }

                if(success){
                    for(Territory territory: territories){
                        if(territory.getName().equals(toPath.getName())){
                            territory.setNum(territory.getNum() + numBox.getValue());
                        }
                    }
                }

                this.player.getClientHandler().sendObject(Operation.FORTIFY);
                this.player.getClientHandler().sendObject(territories);
                // clear the TwoSelectedPaths
                for( CountryPath p : svgUtil.getTwoSelectedPaths()){
                    p.setStrokeWidth(1);
                }
                svgUtil.setTwoSelectedPaths(new ArrayList<CountryPath>());
                svgUtil.deleteArrow();
                timeline.stop();
                handleTimer();
            }
        }
    }

    @FXML
    void auto(ActionEvent event) throws InterruptedException {
        HashMap<String,CountryPath> restMap = svgUtil.getCountryPathHashMap();
        List<CountryPath> restCountryPath = new ArrayList<>();
        for (CountryPath countryPath : restMap.values()){
            if (!countryPath.isOccupied()){
                restCountryPath.add(countryPath);
            }
        }
        int randomtroopNum = 0;
        switch (this.player.getPhase()){
            case Preparation -> {
                // country should not be occupied.
                Random random = new Random();
                CountryPath randomCountry = restCountryPath.get(random.nextInt(restCountryPath.size()));
                Country temp = Country.valueOf(randomCountry.getName());
                if (!randomCountry.isOccupied()){
                    if (this.player.getAllowedTroops() >= 1){
                        Random randomNum = new Random();
                        randomtroopNum = randomNum.nextInt(2,3);
                        if (randomtroopNum <= this.player.getAllowedTroops()){
                            temp.setPopulation(randomtroopNum);
                            this.player.setAllowedTroops(this.player.getAllowedTroops() - randomtroopNum);
                        }else {
                            temp.setPopulation(this.player.getAllowedTroops());
                            this.player.setAllowedTroops(0);
                        }
                    }
                }
                svgUtil.setPathColor(this.color, temp);
                this.player.addToOccupiedCountries(temp);
                troopsNum.setText(String.valueOf(this.player.getAllowedTroops()));
                this.player.getClientHandler().sendObject(Operation.OCCUPY);
                this.player.getClientHandler().sendObject(this.player.getName());
                this.player.getClientHandler().sendObject(this.player.getOccupiedCountries().size());
                for (int i = 0; i < this.player.getOccupiedCountries().size(); i++) {
                    Country country = this.player.getOccupiedCountries().get(i);
                    this.player.getClientHandler().sendObject(country);
                    this.player.getClientHandler().sendObject(country.getPopulation());
                }
                if (gainedCard == 0) {
                    gainedCard += 1;
                    player.addRandomCard();
                }
                timeline.stop();
                handleTimer();
            }
            case Attack -> {
                String attackCountry = "";
                String defendCountry = "";
                ArrayList<CountryPath> autoSelectedTwo = new ArrayList<CountryPath>();
                ArrayList<String> attackList = new ArrayList<>();
                ArrayList<String> defendList = new ArrayList<>();
                for (Territory i:this.territories){
                    if (i.getOwner().equals(this.player.getName())){
                        if (i.getNum() > 1){
                            attackList.add(i.getName());
                        }
                    }
                }
                Random randomAttacker = new Random();
                attackCountry = attackList.get(randomAttacker.nextInt(attackList.size()));
                for (String i:svgUtil.getNeighbourList().get(attackCountry)){
                    for (Territory j:this.territories){
                        if (j.getName().equals(i)){
                            if (!j.getOwner().equals(this.player.getName())){
                                defendList.add(i);
                            }
                        }
                    }
                }
                System.out.println(defendList);
                Random randomDefender = new Random();
                defendCountry = defendList.get(randomDefender.nextInt(defendList.size()));
                CountryPath attacker = svgUtil.getCountryPathHashMap().get(attackCountry);
                CountryPath defender = svgUtil.getCountryPathHashMap().get(defendCountry);
                autoSelectedTwo.add(attacker);
                autoSelectedTwo.add(defender);
                svgUtil.setTwoSelectedPaths(autoSelectedTwo);
                svgUtil.setStartX(attacker.getText().getX() + 17);
                svgUtil.setEndX(defender.getText().getX() + 17);
                svgUtil.setStartY(attacker.getText().getY() - 5);
                svgUtil.setEndY(defender.getText().getY() - 5);
                svgUtil.showArrow();
                isAuto = true;
                Thread.sleep(1000);
                AttackPhase.fire();
                DiceStage.setOnCloseRequest(eventHandler);
                Thread thread = new Thread(()->{
                    while (!diceController.isEnd){
                        if(!diceController.rollButton.isDisable()){
                            diceController.rollButton.fire();
                            try {
                                //停留时间后点击，可以改
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });
                thread.start();
                Thread endThread= new Thread(()->{
                    try {
                        thread.join();
                        //停留时间后关闭，可以改
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Platform.runLater(()-> {

                        if(diceController.getWinner().equals(this.player.getName())){
                            int attackerNum = diceController.getAttackNum();
                            int defenderNum = diceController.getDefendNum();
                            for(int i = 0; i < territories.size();i++){
                                if(territories.get(i).getName().equals(defender.getName())){
                                    territories.get(i).setOwner(player.getName());
                                    territories.get(i).setNum(1);
                                }
                                if(territories.get(i).getName().equals(attacker.getName())){
                                    territories.get(i).setNum(attackerNum-1);
                                    if(gainedCard == 0){
                                        gainedCard += 1;
                                        player.addRandomCard();
                                    }

                                }
                            }
                        }
                        else {
                            int attackerNum = diceController.getAttackNum();
                            int defenderNum = diceController.getDefendNum();
                            for(int i = 0; i < territories.size();i++){
                                if(territories.get(i).getName().equals(defender.getName())){
                                    territories.get(i).setNum(defenderNum);
                                }
                                if(territories.get(i).getName().equals(attacker.getName())){
                                    territories.get(i).setNum(1);
                                }
                            }
                        }
                        DiceStage.close();
                        timeline.stop();
                        handleTimer();
                        isAuto = false;
                        svgUtil.setTwoSelectedPaths(new ArrayList<CountryPath>());
                        svgUtil.deleteArrow();
                        player.getClientHandler().sendObject(Operation.ATTACK);
                        player.getClientHandler().sendObject(territories);
                    });
                });
                endThread.start();
            }
            case Reinforcement -> {
                // 这个数字根据战略需要改，这里固定是为了方便测试
                // 国家要是自己占有的国家，territory.getOwner等于玩家
                int addNum = 0;
                String reinforceCountry = "";
                int reinfoceNum = 100;
                for (Country i:this.player.getOccupiedCountries()){
                    if (i.getPopulation() < reinfoceNum){
                        reinfoceNum = i.getPopulation();
                        reinforceCountry = i.getName();
                    }
                }
                if (this.player.getAllowedTroops() >=1 ){
                    Random randomAdd = new Random();
                    addNum = randomAdd.nextInt(4 ,10);
                    if (addNum > this.player.getAllowedTroops()){
                        addNum = this.player.getAllowedTroops();
                    }
                }
                Country country = Country.valueOf(reinforceCountry);
                CountryPath countryPath = svgUtil.getCountryPathHashMap().get(reinforceCountry);
                country.setPopulation(country.getPopulation() + addNum);
                this.player.setAllowedTroops(this.player.getAllowedTroops() - addNum);
                troopsNum.setText(String.valueOf(this.player.getAllowedTroops()));
                for(Territory territory: territories){
                    if(territory.getName().equals(country.getName())){
                        territory.setNum(territory.getNum() + addNum);
                        countryPath.getText().setText(String.valueOf(territory.getNum()));
                    }
                }
                this.player.getClientHandler().sendObject(Operation.REINFORCE);
                this.player.getClientHandler().sendObject(territories);
                timeline.stop();
                handleTimer();
            }
            case Fortify -> {
                // 两个国家都需要是自己占领的
                System.out.println("auto-fortify");
                ArrayList<CountryPath> autoSelectedTwo = new ArrayList<CountryPath>();
                int country2Num = 100;
                HashMap<String,Integer> ownCountry = new HashMap<String, Integer>();
                Map<String,Integer> ownCountry2 = new LinkedHashMap<String, Integer>();
                ArrayList<String> stringArrayList = new ArrayList<>();
                for (Territory i:this.territories){
                    if (i.getOwner().equals(this.player.getName())){
                        ownCountry.put(i.getName(),i.getNum());
                    }
                }
                ownCountry.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .forEachOrdered(e -> ownCountry2.put(e.getKey(),e.getValue()));
                for (Map.Entry<String,Integer> j:ownCountry2.entrySet()){
                    stringArrayList.add(j.getKey());
                }
                CountryPath from = svgUtil.getCountryPathHashMap().get(stringArrayList.get(0));
                CountryPath to = svgUtil.getCountryPathHashMap().get(stringArrayList.get(stringArrayList.size()-1));
                autoSelectedTwo.add(from);
                autoSelectedTwo.add(to);
                // 这个数字根据战略需要改，这里固定是为了方便测试(
                int num = Country.valueOf(from.getName()).getPopulation() / 2;
                svgUtil.setTwoSelectedPaths(autoSelectedTwo);
                svgUtil.setStartX(from.getText().getX() + 17);
                svgUtil.setEndX(to.getText().getX() + 17);
                svgUtil.setStartY(from.getText().getY() - 5);
                svgUtil.setEndY(to.getText().getY() - 5);
                svgUtil.showArrow();
                numBox.getValueFactory().setValue(num);
                new Thread(()->{
                    try {
                        Thread.sleep(500);
                        TransferPhase.fire();
                        svgUtil.deleteArrow();
                    } catch (Exception e) {
                        Platform.runLater(()->{
                            svgUtil.setTwoSelectedPaths(new ArrayList<CountryPath>());
                            svgUtil.deleteArrow();
                        });

                    }
                    timeline.stop();
                    handleTimer();
                }).start();


            }
        }

    }

    private boolean isOccupied(String countryName){
        // this.territories only store occupied countries.
        for(Territory territory: territories){
            if(countryName.equals(territory.getName())){
                return true;
            }
        }
        return false;
    }


    @FXML
    private void cancelCardView(ActionEvent event) {
        model.quitCards();
        if(CardModel.getInstance().readyToQuit()) {
            Stage stage = (Stage) cancelCardView.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void cardview(ActionEvent event) throws IOException {

        Stage previous = new Stage();
        model.setCurrentPlayer(this.player);
        CardModel.getInstance().setCurrentPlayer(this.player);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/group.riskgame.Application/Card.fxml"));
        Parent main = loader.load();
        Scene scene = new Scene(main);
        // decide style
        if(style.equals("light")){
            scene.getStylesheets().add(getClass().getResource("/group.riskgame.Application/css/CardLightCss.css").toExternalForm());
        }
        if(style.equals("dark")){
            scene.getStylesheets().add(getClass().getResource("/group.riskgame.Application/css/CardDarkCss.css").toExternalForm());
        }
        CardController cardController = loader.getController();
        cardController.setStyle(style);
        cardController.setModel(model);
        cardController.autoInitializeController();
        previous.setResizable(false);
        previous.setScene(scene);
        previous.show();
        previous.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                troopsNum.setText(String.valueOf(player.getAllowedTroops()));
            }
        });
        cardController.getCancelCardView().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                cardController.cancelCardView(event);
                troopsNum.setText(String.valueOf(player.getAllowedTroops()));
            }
        });

    }

    public void setPlayersNumOnPane(){
        model.setCurrentPlayer(this.player);
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
        int recommendNum = (int) Math.ceil(42.0 / (double)this.player.getAllPlayers().size());
        leftCountries.setText(String.valueOf(recommendNum));
        svgUtil.setCurrentPlayer(this.player);
        new Thread(() ->{
            while(true){
                boolean isUpdated = this.player.getClientHandler().receiveUpdated();
                if(this.player.getPhase() == currentProcess.Fortify && waitForNext && !isUpdated){
                    int newNum = (int) this.player.getClientHandler().readObject();
                    this.player.setAllowedTroops(this.player.getAllowedTroops() + newNum);
                    Platform.runLater(()->troopsNum.setText(String.valueOf(this.player.getAllowedTroops())));
                }
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
                            leftTroops = Integer.parseInt(troopsNum.getText());
                            for(Territory t : this.territories){
                                if(this.player.getOccupiedCountries().contains(Country.valueOf(t.getName())) && !Objects.equals(t.getOwner(), this.player.getName())){
                                    leftTroops = leftTroops + Country.valueOf(t.getName()).getPopulation();
                                    ArrayList<Country> updated = this.player.getOccupiedCountries();
                                    updated.remove(Country.valueOf(t.getName()));
                                    this.player.setOccupiedCountries(updated);
                                    this.player.setAllowedTroops(leftTroops);
                                }
                            }
                            Platform.runLater(()-> troopsNum.setText(String.valueOf(this.player.getAllowedTroops())));
                        }
                        country.setPopulation(territory.getNum());
                        svgUtil.setPathColor(this.player.getPlayersColorMap().get(territory.getOwner()),country);
                        svgUtil.setCountryTroops(country,territory.getNum());
                    }
                    int owned = 0;
                    if(territories.size() > 0){
                        String name = territories.get(0).getOwner();

                        for(Territory territory: territories){
                            if(territory.getOwner().equals(name)){
                                owned++;
                            }
                        }
                        // the game end
                        if(owned >= 6 ){
                            this.player.getClientHandler().sendObject(Operation.END);
                            this.player.setPhase(currentProcess.END);
                            svgUtil.unselectAllPaths();
                            svgUtil.setPhase(this.player.getPhase());
                            if(territories.get(0).getOwner().equals(this.player.getName())){
                                Platform.runLater(()-> phase.setText("Winner"));
                            }
                            else
                                Platform.runLater(()-> phase.setText(this.player.getPhase().toString()));
                            break;
                        }
                    }

                    if(obj.size() >= 6 && this.player.getPhase() == currentProcess.Preparation){
                        // go to attack phase
                        this.player.nextPhase();
                        svgUtil.unselectAllPaths();
                        Platform.runLater(()-> {
                            phase.setText(this.player.getPhase().toString());
                            leftCountries.setVisible(false);
                            targetText.setVisible(false);
                            timeline.stop();
                            handleTimer();
                        });
                        svgUtil.setPhase(this.player.getPhase());
                    }
                    if(waitForNext){
                        boolean isNext = this.player.getClientHandler().receiveUpdated();
                        if(isNext){
                            this.player.nextPhase();
                            this.player.getClientHandler().sendObject(Operation.RESET_WAIT_NEXT);
                            svgUtil.unselectAllPaths();
                            Platform.runLater(()-> {
                                phase.setText(this.player.getPhase().toString());
                                timeline.stop();
                                handleTimer();
                            });
                            svgUtil.setPhase(this.player.getPhase());
                            svgUtil.setTwoSelectedPaths(new ArrayList<>());
                            waitForNext = false;
                            if(this.player.getPhase() == currentProcess.Reinforcement){
                                gainedCard = 0;
                            }
                        }

                    }

                }
            }

        }).start();

    }




    public SvgUtil getSvgUtil() {
        return svgUtil;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
