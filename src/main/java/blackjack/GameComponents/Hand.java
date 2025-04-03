package blackjack;

import java.util.ArrayList;
import java.util.List;
import blackjack.controllers.CardSelector;

public class Hand {

    private CardSelector cardSelector;
    private ArrayList<Card> hand;
    private int handValue;
    boolean isSoft;


    public Hand(CardSelector cardSelector) {
        hand = new ArrayList<>();
        this.cardSelector = cardSelector;
        isSoft = false;

    }

    public Hand(CardSelector cardSelector, Card card) {
        hand = new ArrayList<>();
        this.cardSelector = cardSelector;
        hand.add(card);

        handValue = card.getValue();

        if (card instanceof Ace && card.getValue() == 11) {
            isSoft = true;
        }
    }


    public void initialize() {
        hand.clear();
        handValue = 0;
        isSoft = false;
        hit();
        hit();
    }


    public Card splitHand() {
        return hand.remove(1);
    }

    public int getHandValue() {
        return handValue;
    }

    public Card hit() {
        Card card = cardSelector.getNextCard(handValue);
        hand.add(card);
        handValue += card.getValue();

        if (card.getValue() == 11) {
            isSoft = true;
        }

        checkAce();

        return card;
    }


    private void checkAce() {
        if (handValue > 21) {
            for (Card card : hand) {
                if (card instanceof Ace &&  card.getValue() == 11) {
                    card.lowerValue();
                    handValue -= 10;
                    isSoft = false;
                    return;
                }
            }
        }
    }

    public boolean hasBust() {
        return handValue > 21;
    }

    public List<Card> getCards() {
        return hand;
    }

    public boolean hasBlackJack() {
        return handValue == 21;
    }

    public boolean isSoft() {
        return isSoft;
    }


}
