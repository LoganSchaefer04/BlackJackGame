package blackjack;

import java.util.*;

/*
This is the dealer class for the blackjack game
 */
public class Dealer {
    private ArrayList<Card> hand;
    private CardSelector cardSelector;
    private int handVal;
    //getters
    public int getHandVal(){
        return handVal;
    }
    private void recalculateHandValue(){
        handVal = 0;
        for(Card card : hand){
            handVal+= card.getValue();
        }
    }
    public ArrayList<Card> getHand() {
        return hand;
    }
    //Only has a blackjack if and only if 2 cards have been dealt and the value is 21
    public boolean hasBlackJack(){
        return hand.size() == 2 && handVal == 21;
    }
    public boolean hasBust(){
        return handVal > 21;
    }

    public Dealer(CardSelector cardSelector) {
        this.cardSelector = cardSelector;
        this.hand = new ArrayList<>();
        this.handVal = 0;
    }
    // Allows dealer to draw card
    public Card drawCard() {
        Card card = cardSelector.getNextCard(handVal); //pulls random card
        hand.add(card); //adds card to dealer hand
        //System.out.printf("Card drawn with val of %d\n", card.getValue()); //debug print for card drawn
        handVal += card.getValue(); //updates dealer hand value int

        // The case where dealer busts but an ace could be counted as 1
        checkAce();

        return card; //sends card to dealer hand
    }

    //makes the hand, draws the first two cards
    public void initHand() {
        hand.clear();
        handVal = 0;

        drawCard();
        //System.out.println("New hand val" + handVal);
        drawCard();
        //System.out.println("New hand val" + handVal);
    }


    private void checkAce() {
        if (handVal > 21) {
            for (Card card : hand) {
                if (card instanceof Ace && ((Ace) card).getValue() == 11) {
                    ((Ace) card).lowerValue();
                    handVal -= 10;
                    if (handVal <= 21) {
                        break;
                    }
                }
            }
        }
    }

    public void playTurn(){
        recalculateHandValue();
        while(handVal < 17){
            drawCard();
        }
    }

}


