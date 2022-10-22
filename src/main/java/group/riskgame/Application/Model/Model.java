package group.riskgame.Application.Model;

import java.util.ArrayList;

public class Model {
    public static int cardsValue = 5;
    private Player currentPlayer;
    public static boolean disable = false;

    public final static String[] cards = {"infantry","cavalry","artillery"};
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * set current player
     * @param  p player
     */
    public void setCurrentPlayer(Player p) {
        currentPlayer = p;
    }

    public void trade(ArrayList<Card> cards){

        String card1 = cards.get(0).cardType.toString().toLowerCase();
        String card2 = cards.get(1).cardType.toString().toLowerCase();
        String card3 = cards.get(2).cardType.toString().toLowerCase();

        if(!validCardExchange(card1,card2,card3)){
            CardModel.getInstance().setInvalidInfo(1);
            CardModel.getInstance().update();
            return;
        }
        CardModel.getInstance().setInvalidInfo(0);
        CardModel.getInstance().update();
        currentPlayer.handleCards(card1, card2, card3);
        currentPlayer.exchangeForArmy();
        CardModel.getInstance().update();
    }

    /**
     * indicate whether the exchange operation is valid
     * @param card1 name of the first card
     * @param card2 name of the second card
     * @param card3 name of the third card
     * @return true if the operation is valid; otherwise return false
     */
    public boolean validCardExchange(String card1, String card2, String card3){
        if(card1 == null || card2 == null || card3 == null){
            return false;
        }
        if(card1.equals(card2) && card2.equals(card3)){
            if(currentPlayer.getCards().get(card1) >= 1 && currentPlayer.getCards().get(card2) >= 1 &&
                    currentPlayer.getCards().get(card3) >= 1){
                return true;
            } else {
                return false;
            }
        }
        if(!card1.equals(card2) && !card2.equals(card3) && !card1.equals(card3)){
            if(currentPlayer.getCards().get(card1) >= 1 && currentPlayer.getCards().get(card2) >= 1 &&
                    currentPlayer.getCards().get(card3) >= 1){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * button event for cardView quit button
     */
    public void quitCards(){

        if(currentPlayer.getTotalCards() >= 5){
            CardModel.getInstance().setInvalidInfo(3);
            CardModel.getInstance().update();
            return;
        }

        CardModel.getInstance().hide();
        disable = false;
    }
}
