package blackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private CardSelector cardSelector;
    private ArrayList<Card> hand;
    public int handValue;
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

        if (card instanceof Ace) {
            isSoft = true;
        }
    }

    public void initialize() {
        hand.clear();
        handValue = 0;
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
        Card card = cardSelector.getNextCard(handValue); //pulls random card
        hand.add(card); //adds card to dealer hand
        handValue += card.getValue(); //updates dealer hand value int

        if (card.getValue() == 1) {
            isSoft = true;
        }

        // Lower value of aces if over 21.
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
