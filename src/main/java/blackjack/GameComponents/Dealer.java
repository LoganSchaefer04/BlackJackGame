package blackjack.GameComponents;


import java.util.*;
import blackjack.features.CardCounting;

/*
This is the dealer class for the blackjack game
 */
public class Dealer {
    private Hand hand;
    private CardSelector cardSelector;
    private CardCounting cardCounting;

    public Dealer(CardSelector cardSelector) {
        this.cardSelector = cardSelector;
        this.cardCounting = cardCounting; // Initialize the cardCounting object
        this.hand = new Hand(cardSelector, 0, 0);
    }

    //makes the hand, draws the first two cards
    public void initHand() {
        hand = new Hand(cardSelector, 0, 0);
    }

    public void playTurn(int playerHandValue) {
        // Dealer will continue to hit while:
        // 1. Dealer's hand is less than 17 and less than player's hand.
        // 2. Dealer's hand is 17 and carries an Ace of value = 11.
        while((hand.getHandValue() < 17 && hand.getHandValue() <= playerHandValue)
        || (hand.getHandValue() == 17 && hand.isSoft())) {
            Card drawnCard = hand.hit(0);
            // Update the count when a new card is drawn
            cardCounting.updateRunningCount(drawnCard);
        }
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

}


