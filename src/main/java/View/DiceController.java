package View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DiceController implements Initializable {

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
    private Button rollButton;

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

    private int[] attackResults = {0,0,0};

    private int[] defendResults = {0,0};



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
                System.out.println("dice 1 " + num);
                attackResults[0] = num;
//                Arrays.sort(attackResults);
                rollButton.setDisable(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        vectors.add(thread);
        thread.start();

        Thread thread2 = new Thread(() -> {
            try {
                int num = 0;
                for (int i = 0; i < 15; i++) {
                    num = random.nextInt(6)+1;
                    File file = new File("src/main/resources/view/img/dice" + (num)+".png");
                    diceImage2.setImage(new Image(file.toURI().toString()));
                    Thread.sleep(50);
                }
                System.out.println("dice 2 " + num);
                attackResults[1] = num;
//                Arrays.sort(attackResults);
                rollButton.setDisable(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        vectors.add(thread2);
        thread2.start();

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
                        System.out.println("dice 3 " + num);
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
                System.out.println("dice 4 " + num);
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
                    System.out.println("dice 5 " + num);
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

//        for(Thread t : vectors){
//            t.join(); //使用join来保证childrenThread的5个线程都执行完后，才执行主线程
//        }

//        Arrays.sort(attackResults);
//        Arrays.sort(defendResults);
        Thread thread6 = new Thread(()->{
            for(Thread t : vectors){
                try {
                    t.join(); //使用join来保证childrenThread的5个线程都执行完后，才执行主线程
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

//            System.out.println(Arrays.toString(attackResults));
//            System.out.println(Arrays.toString(defendResults));
        });
        thread6.start();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        twoDices.setToggleGroup(toggleGroup);
        threeDices.setToggleGroup(toggleGroup);
        oneDice.setToggleGroup(toggleGroup);
        if(attackNum == 2){
            threeDices.setVisible(false);
            diceImage3.setVisible(false);
        }
        if(attackNum == 1){
            threeDices.setVisible(false);
            diceImage3.setVisible(false);
            twoDices.setVisible(false);
            diceImage2.setVisible(false);
        }
        if(defendNum == 1){
            diceImage5.setVisible(false);
        }
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