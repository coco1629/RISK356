package View;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DiceController implements Initializable {

    @FXML
    private Label attackCountry;

    @FXML
    private Label attackTroopsNum;

    @FXML
    private Label defendCountry;

    @FXML
    private Label defendTroopsNum;


    @FXML
    private ImageView diceImage;

    @FXML
    private ImageView diceImage2;

    @FXML
    private ImageView diceImage3;

    @FXML
    private ImageView diceImage4;

    @FXML
    private ImageView diceImage5;

    @FXML
    public Button rollButton;

    @FXML
    private RadioButton threeDices;

    @FXML
    private RadioButton twoDices;

    @FXML
    private RadioButton oneDice;

    private String attacker = "";

    private String defender = "";

    private String winner = "";

    private int attackNum = 0;

    private int defendNum = 0;

    public boolean isEnd = false;

    private int[] attackResults = {0,0,0};

    private int[] defendResults = {0,0};

    private int attackTroopsChange = 0;

    private int defendTroopsChange = 0;

    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    private Label success;

    @FXML
    void roll(ActionEvent event) throws InterruptedException {
        Random random = new Random();
        rollButton.setDisable(true);
        Vector<Thread> vectors = new Vector<Thread>();
        attackResults = new int[]{0, 0, 0};
        defendResults = new int[]{0, 0};
        Thread thread = new Thread(() -> {
            try {
                int num = 0;
                for (int i = 0; i < 15; i++) {
                    num = random.nextInt(6)+1;
                    File file = new File("src/main/resources/view/img/dice" + (num)+".png");
                    diceImage.setImage(new Image(file.toURI().toString()));
                    Thread.sleep(50);
                }
//                System.out.println("dice 1 " + num);
                attackResults[0] = num;
//                Arrays.sort(attackResults);
                rollButton.setDisable(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        vectors.add(thread);
        thread.start();

        if(twoDices.isSelected() || threeDices.isSelected()){
            Thread thread2 = new Thread(() -> {
                try {
                    int num = 0;
                    for (int i = 0; i < 15; i++) {
                        num = random.nextInt(6)+1;
                        File file = new File("src/main/resources/view/img/dice" + (num)+".png");
                        diceImage2.setImage(new Image(file.toURI().toString()));
                        Thread.sleep(50);
                    }
    //                System.out.println("dice 2 " + num);
                    attackResults[1] = num;
    //                Arrays.sort(attackResults);
                    rollButton.setDisable(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            vectors.add(thread2);
            thread2.start();
        }

        if(threeDices.isSelected()){
            Thread thread3 = new Thread(){
                public void run(){
                    try {
                        int num = 0;
                        for (int i = 0; i < 15; i++) {
                            num = random.nextInt(6)+1;
                            File file = new File("src/main/resources/view/img/dice" + (num)+".png");
                            diceImage3.setImage(new Image(file.toURI().toString()));
                            Thread.sleep(50);
                        }
//                        System.out.println("dice 3 " + num);
                        attackResults[2] = num;
//                        Arrays.sort(attackResults);
                        rollButton.setDisable(false);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            vectors.add(thread3);
            thread3.start();
        }

        Thread thread4 = new Thread(() -> {
            try {
                int num = 0;
                for (int i = 0; i < 15; i++) {
                    num = random.nextInt(6)+1;
                    File file = new File("src/main/resources/view/img/dice" + (num)+".png");
                    diceImage4.setImage(new Image(file.toURI().toString()));
                    Thread.sleep(50);
                }
//                System.out.println("dice 4 " + num);
                defendResults[0] = num;
//                Arrays.sort(defendResults);
                rollButton.setDisable(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        vectors.add(thread4);
        thread4.start();

        if(defendNum >= 2){
            Thread thread5 = new Thread(() -> {
                try {
                    int num = 0;
                    for (int i = 0; i < 15; i++) {
                        num = random.nextInt(6)+1;
                        File file = new File("src/main/resources/view/img/dice" + (num)+".png");
                        diceImage5.setImage(new Image(file.toURI().toString()));
                        Thread.sleep(50);
                    }
//                    System.out.println("dice 5 " + num);
                    defendResults[1] = num;
//                    Arrays.sort(defendResults);
                    rollButton.setDisable(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            vectors.add(thread5);
            thread5.start();
        }

        Thread thread6 = new Thread(()->{
            for(Thread t : vectors){
                try {
                    t.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Arrays.sort(attackResults);
            Arrays.sort(defendResults);
            int attackDiceNum = 0;
            int defendDiceNum = 0;
            for(int i = 0 ; i < 3; i++){
                if(attackResults[i]!=0)
                    attackDiceNum++;
            }
            for(int i = 0 ; i < 2; i++){
                if(defendResults[i]!=0)
                    defendDiceNum++;
            }
            if(attackDiceNum == 1 || defendDiceNum == 1){
                if(attackResults[2] > defendResults[1]){
//                    winner = attacker;
                    defendTroopsChange -= 1;
                    defendNum -= 1;
                }
                else {
//                    winner = defender;
                    attackTroopsChange -= 1;
                    attackNum -= 1;
                }
            }
            else {
                if(attackResults[2] > defendResults[1]){
                    defendTroopsChange -= 1;
                    defendNum -= 1;
                }
                else {
                    attackTroopsChange -= 1;
                    attackNum -= 1;
                }
                if(attackResults[1] > defendResults[0]){
                    defendTroopsChange -= 1;
                    defendNum -= 1;
                }
                else {
                    attackTroopsChange -= 1;
                    attackNum -= 1;
                }

            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    attackTroopsNum.setText(String.valueOf(attackNum));
                    defendTroopsNum.setText(String.valueOf(defendNum));
                }
            });
//            System.out.println("attack Num" + attackNum);
//            System.out.println("defend Num" + defendNum);
            if(attackNum == 2){
                threeDices.setDisable(true);
                twoDices.setSelected(true);
                diceImage3.setVisible(false);
            }
            if(attackNum <= 1){
//                threeDices.setDisable(true);
//                diceImage3.setVisible(false);
//                twoDices.setDisable(true);
//                diceImage2.setVisible(false);
                winner = defender;
                isEnd = true;
                rollButton.setDisable(true);

            }
            if(defendNum == 1){
                diceImage5.setVisible(false);
            }
            if(defendNum <= 0){
                isEnd = true;
                winner = attacker;
                success.setVisible(true);
                rollButton.setDisable(true);
            }
//            if(attackNum <= 0){
//                winner = defender;
//                rollButton.setDisable(true);
//            }

//            System.out.println(Arrays.toString(attackResults));
//            System.out.println(Arrays.toString(defendResults));
        });
        thread6.start();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        twoDices.setToggleGroup(toggleGroup);
        threeDices.setToggleGroup(toggleGroup);
        oneDice.setToggleGroup(toggleGroup);
//        if(attackNum == 2){
//            threeDices.setVisible(false);
//            diceImage3.setVisible(false);
//        }
//        if(attackNum == 1){
//            threeDices.setVisible(false);
//            diceImage3.setVisible(false);
//            twoDices.setVisible(false);
//            diceImage2.setVisible(false);
//        }
//        if(defendNum == 1){
//            diceImage5.setVisible(false);
//        }
//        attackTroopsNum.setText(String.valueOf(attackNum));
//        defendTroopsNum.setText(String.valueOf(defendNum));
//        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
//            @Override
//            public void changed(final ObservableValue<? extends Toggle> observable, final Toggle oldValue, final Toggle newValue) {
//                RadioButton r = (RadioButton)newValue;
//                if(r.getText().equals("2 dices")){
//                    diceImage3.setVisible(false);
//                    diceImage.setVisible(true);
//                    diceImage2.setVisible(true);
//                }
//                if(r.getText().equals("1 dice")){
//                    diceImage3.setVisible(false);
//                    diceImage2.setVisible(false);
//                    diceImage.setVisible(true);
//                }
//                if(r.getText().equals("3 dices")){
//                    diceImage3.setVisible(true);
//                    diceImage2.setVisible(true);
//                    diceImage.setVisible(true);
//                }
//            }
//        });
    }

    public void initDice(){
        if(attackNum == 2){
            threeDices.setDisable(true);
            diceImage3.setVisible(false);
            twoDices.setSelected(true);
        }
        if(attackNum == 1){
            threeDices.setDisable(true);
            diceImage3.setVisible(false);
            twoDices.setDisable(true);
            diceImage2.setVisible(false);
            oneDice.setSelected(true);
        }
        if(defendNum == 1){
            diceImage5.setVisible(false);
        }
        attackTroopsNum.setText(String.valueOf(attackNum));
        defendTroopsNum.setText(String.valueOf(defendNum));
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(final ObservableValue<? extends Toggle> observable, final Toggle oldValue, final Toggle newValue) {
                RadioButton r = (RadioButton)newValue;
                if(r.getText().equals("2 dices")){
                    diceImage3.setVisible(false);
                    diceImage.setVisible(true);
                    diceImage2.setVisible(true);
                }
                if(r.getText().equals("1 dice")){
                    diceImage3.setVisible(false);
                    diceImage2.setVisible(false);
                    diceImage.setVisible(true);
                }
                if(r.getText().equals("3 dices")){
                    diceImage3.setVisible(true);
                    diceImage2.setVisible(true);
                    diceImage.setVisible(true);
                }
            }
        });
    }

    public String getAttacker() {
        return attacker;
    }

    public void setAttacker(String attacker) {
        this.attacker = attacker;
    }

    public String getDefender() {
        return defender;
    }

    public void setDefender(String defender) {
        this.defender = defender;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getAttackNum() {
        return attackNum;
    }

    public void setAttackNum(int attackNum) {
        this.attackNum = attackNum;
    }

    public int getDefendNum() {
        return defendNum;
    }

    public void setDefendNum(int defendNum) {
        this.defendNum = defendNum;
    }
}