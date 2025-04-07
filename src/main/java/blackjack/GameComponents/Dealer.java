package blackjack.GameComponents;


import java.util.*;

/*
This is the dealer class for the blackjack game
 */
public class Dealer {
    private Hand hand;
    private CardSelector cardSelector;
    private boolean hasPlayed;

    public Dealer(CardSelector cardSelector) {
        this.cardSelector = cardSelector;
    }

    //makes the hand, draws the first two cards
    public void initHand() {
        this.hand = new Hand(cardSelector);
        hasPlayed = false;
    }

    public void playTurn() {
        // Dealer will continue to hit while:
        // 1. Dealer's hand is less than 17 and less than each player hand.
        // 2. Dealer's hand is 17 and carries an Ace of value = 11.
        while((hand.getHandValue() < 17) || (hand.getHandValue() == 17 && hand.isSoft())) {
            hand.hit(0);
        }
        hasPlayed = true;
    }

    //getters
    public int getHandValue(){
        return hand.getHandValue();
    }

    public List<Card> getCards() {
        return hand.getCards();
    }
    public boolean hasBlackJack(){
        return hand.hasBlackJack();
    }
    public boolean hasBust(){
        return hand.hasBust();
    }

    public Hand getHand() {
        return hand;
    }
    public boolean hasPlayed() {
        return hasPlayed;
    }

    public List<String> getDealerCardNames() {
        return hand.getCardNames();
    }

    public int getRecentValue() {
        return hand.getRecentValue();
    }

    public int getCardValue(int index) {
        return hand.getCardValue(index);
    }


}


