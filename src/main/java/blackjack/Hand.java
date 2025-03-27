package blackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private CardSelector cardSelector;
    private ArrayList<Card> hand;
    private int handValue;
    boolean isSoft;
    boolean canSplit;
    private int bet;


    public Hand(CardSelector cardSelector, int bet) {
        hand = new ArrayList<>();
        this.cardSelector = cardSelector;
        isSoft = false;
        this.bet = bet;
        initializeNewHand();
    }


    public Hand(CardSelector cardSelector, Card card, int bet) {
        hand = new ArrayList<>();
        card.raiseValue();
        this.cardSelector = cardSelector;
        this.bet = bet;
        hand.add(card);
        handValue += card.getValue();
        if (card instanceof Ace) {
            isSoft = true;
        }
        hit();
    }

    public void initializeNewHand() {
        handValue = 0;
        isSoft = false;
        hit();
        hit();

        if (hand.get(0).getValue() == hand.get(1).getValue()
            || hand.get(0).getRank().equals("Ace") && hand.get(1).getRank().equals("Ace")) {
            canSplit = true;
        }
    }

    public Card splitHand() {
        handValue -= hand.get(1).getValue();
        return hand.remove(1);
    }

    public int getHandValue() {
        return handValue;
    }

    public Card hit() {
        Card card = cardSelector.getNextCard(handValue); //pulls random card
        hand.add(card); //adds card to dealer hand
        handValue += card.getValue(); //updates dealer hand value int
        canSplit = false;

        if (card.getValue() == 11) {
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
    public boolean canSplit() {
        return canSplit;
    }
    public int getBet() {
        return bet;
    }


}
