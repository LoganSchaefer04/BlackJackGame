package blackjack;

import blackjack.controllers.CardSelector;

import java.util.ArrayList;

/**
 * Class for players and their actions
 */
public class Player {
    private int handValue;
    private ArrayList<Card> hand = null;
    private final CardSelector dealer;


    /**
     * Constructor
     * @param dealer, used to generate new cards when needed
     */
    public Player(CardSelector dealer) {
        this.dealer = dealer;
        handValue = 0;
        hand = new ArrayList<Card>();
    }

    /**
     * Calculates the value of hand
     * @return int, value of the hand
     */
    private int calcHand() {
        int value = 0;
        for(Card c : hand) {
            value += c.getValue();
        }
        return value;
    }

    /**
     * Checks if hand is bust or not
     * @return true if hand is over 21, else returns false
     */
    private boolean checkBust() {
        return calcHand() > 21;
    }

    /**
     *
     * @return true if bust after hit, else return false
     */
    public boolean hit() {
        hand.add(dealer.getNextCard(handValue));
        printHand();
        handValue = calcHand();
        System.out.println(getHandValue());

        return checkBust();
    }

    /**
     * Clears any previous hand, creates a new initial hand with 2 cards in it
     */
    public void initHand() {
        hand.clear();
        handValue = 0;
        hand.add(dealer.getNextCard(handValue));
        handValue = calcHand();
        hand.add(dealer.getNextCard(handValue));
        handValue = calcHand();
    }

    /**
     * get value of hand
     * @return int, value of hand
     */
    public int getHandValue() {
        return handValue;
    }

    /**
     * checks if player bust
     * @return boolean, true if bust, false otherwise
     */
    public boolean hasBust() {
        return checkBust();
    }

    /**
     * print hand, prints out the hand of the user with rank and suit
     */
    public void printHand() {
        System.out.println("Players hand of cards: ");
        for(Card c : hand) {
            System.out.print(c.getRank() + " of " + c.getSuit() + ", ");
        }
        System.out.print("\n");
    }
}
