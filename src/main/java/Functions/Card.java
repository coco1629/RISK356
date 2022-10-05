package Functions;

import Model.Country;
import Model.Territory;

public class Card {
    public CardType cardType;
    private Territory territory;
    public Card(CardType cardType){
        this.cardType = cardType;

    }
    public CardType getCardType(){
        return cardType;

    }
    public Territory getTerritory(){
        return territory;

    }
    public void setTerritory(Territory territory){
        this.territory = territory;

    }
    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if (!(obj instanceof Card)){
            return false;
        }
        Card card = (Card) obj;
        return card.getCardType().toString().equalsIgnoreCase(cardType.toString()) && card.getTerritory().equals(territory);

    }
}
