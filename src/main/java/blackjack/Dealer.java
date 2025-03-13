package blackjack;

import java.util.*;

/*
This is the dealer class for the blackjack game
 */
public class Dealer {
    private Hand hand;
    public Dealer(CardSelector cardSelector) {
        this.hand = new Hand(cardSelector);
    }

    //makes the hand, draws the first two cards
    public void initHand() {
        hand.initialize();
    }

    public void playTurn(int playerHandValue) {
        while(hand.getHandValue() < 17 && hand.getHandValue() < playerHandValue){
            hand.hit();
        }
    }

    //getters
    public int getHandValue(){
        return hand.getHandValue();
    }

    public List<Card> getHand() {
        return hand.getCards();
    }
    public boolean hasBlackJack(){
        return hand.hasBlackJack();
    }
    public boolean hasBust(){
        return hand.hasBust();
    }

}


