package Model;

import View.CountryPath;

public class Card {
    public CardType cardType;
    private CountryPath territory;

    /**
     * constructor
     * @param cardType the type of card
     */
    public Card(CardType cardType) {
        this.cardType = cardType;
    }

    /**
     * Get card type
     * @return cardType
     */
    public CardType getCardType() {
        return cardType;
    }

    /**
     * Get territory
     * @return Country(territory)
     */
    public CountryPath getTerritory() {
        return territory;
    }


    /**
     * Set Country(territory)
     * @param territory territory(country)
     */
    public void setTerritory(CountryPath territory) {
        this.territory = territory;
    }

    /**
     * Verify if obj is a card
     * @param obj object
     * @return true, if the same card; false, if not a card instance
     */
    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Card)) {
            return false;
        }

        Card card = (Card) obj;
        return card.getCardType().toString().equalsIgnoreCase(cardType.toString())
                && card.getTerritory().equals(territory);
    }
}
