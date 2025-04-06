package blackjack.GameComponents;

import blackjack.GameComponents.Ace;
import blackjack.GameComponents.Card;
import blackjack.GameComponents.CardSelector;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private CardSelector cardSelector;
    private ArrayList<Card> hand;
    private int handValue;
    boolean isSoft;
    boolean canSplit;
    private int bet;
    private String result = "";
    private boolean isOver = false;
    private boolean isStayed = false;


    public Hand(CardSelector cardSelector, int bet, int boost) {
        hand = new ArrayList<>();
        this.cardSelector = cardSelector;
        isSoft = false;
        this.bet = bet;
        initializeNewHand(boost);
    }


    public Hand(CardSelector cardSelector, Card card, int bet, int boost) {
        hand = new ArrayList<>();
        card.raiseValue();
        this.cardSelector = cardSelector;
        this.bet = bet;
        hand.add(card);
        handValue += card.getValue();
        if (card instanceof Ace) {
            isSoft = true;
        }
        hit(boost);
    }

    public Hand(CardSelector cardSelector) {
        isSoft = false;
        hand = new ArrayList<>();
        this.cardSelector = cardSelector;
        hit(0);
    }

    public void initializeNewHand(int boost) {
        handValue = 0;
        isSoft = false;
        hit(boost);
        hit(boost);

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

    public boolean hit(int boost) {
        Card card = cardSelector.getNextCard(handValue, boost); //pulls random card
        hand.add(card); // add card to dealer hand
        handValue += card.getValue(); //updates dealer hand value int
        canSplit = false;

        if (card.getValue() == 11) {
            isSoft = true;
        }
        // Lower value of aces if over 21.
        checkAce();

        if (this.hasBust()) {
            isOver = true;
            result = "You Lose!";
        }

        return this.hasBust();
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


    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
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
    public void stay() {
        this.isStayed = true;
        this.isOver = true;
    }
    public boolean isOver() {
        return isOver;
    }

    public boolean isStayed() {
        return isStayed;
    }

    public String recentCardRank() {
        return hand.get(hand.size() - 1).getRank();
    }

    public String recentCardSuit() {
        return hand.get(hand.size() - 1).getSuit();
    }

    public int getRecentValue() {
        return hand.getLast().getValue();
    }

    public List<String> getCardNames() {
        List<String> cardNames = new ArrayList<>();
        for (Card card : hand) {
            cardNames.add(card.getRank() + card.getSuit());
        }
        return cardNames;
    }

    public int getCardCount() {
        return cardSelector.getCardCount();
    }

    public int getCardValue(int index) {
        return hand.get(index).getValue();
    }


}
