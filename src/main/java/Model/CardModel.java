package Model;

import Functions.Card;
import javafx.scene.control.CheckBox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

public class CardModel extends Observable implements Serializable {
    private static CardModel cardModel;
    private Player currentPlayer;
    private String invalidInformation;
    private int invalidInformationNum = -1;

    private final String validType = "Finished to exchange";
    private final String invalidInformation1 = "Invalid Card Combination";
    private final String invalidInformation2 = "Only select 3 cards";
    private final String invalidInformation3 = "Current player owning more than 5 cards";
    private final List<String> invalidTypes = Arrays.asList(validType, invalidInformation1, invalidInformation2, invalidInformation3);
    public static CardModel getInstance(){
        if(null == cardModel) cardModel = new CardModel();
        return cardModel;
    }
    public void update(){
        setChanged();
        notifyObservers();
    }
    public void show(){


    }
    public void hide(){


    }
    public Player getCurrentPlayer(){
        return currentPlayer;

    }
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    void setInvalidInfo(int invalidNum){
        invalidInformationNum = invalidNum;
        this.invalidInformation = invalidTypes.get(invalidNum);

    }
    public List<Card> retrieveCards(List<Card> cards, CheckBox[] checkBoxes){
        int num = 0;
        List<Card> selected = new ArrayList<>();
        for (CheckBox checkBox: checkBoxes){
            if (checkBox.isSelected()){
                selected.add(cards.get(num));
            }
            num ++;
        }
        if(selected.size() != 3){
            setInvalidInfo(2);
            update();
        }
        return selected;
    }
    public String getInvalidInformation(){
        return invalidInformation;

    }
    public boolean quit(){
        invalidInformation = null;
        return invalidInformationNum != 3;

    }
    public boolean finish(){
        return invalidInformationNum == 0;

    }
}
