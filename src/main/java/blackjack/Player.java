package blackjack;

import blackjack.controllers.CardSelector;
import java.util.List;

/**
 * Class for players and their actions
 */
public class Player {
    private Hand hand;
    private CardSelector dealer;

    /**
     * Constructor
     * @param dealer, used to generate new cards when needed
     */
    public Player(CardSelector dealer) {
        this.dealer = dealer;
        hand = new Hand(dealer);
    }

    /**
     * Calculates the value of hand
     * @return int, value of the hand
     */
    private int calcHand() {
        return hand.getHandValue();
    }

    public boolean hasBust() {
        return hand.hasBust();
    }


    public Card hit() {
        Card card = hand.hit();

        return card;
    }

    /**
     * Clears any previous hand, creates a new initial hand with 2 cards in it
     */
    public void initHand() {
        hand.initialize();
    }

    /**
     * get value of hand
     * @return int, value of hand
     */
    public int getHandValue() {
        return hand.getHandValue();
    }


    public void splitHand() {
        Card splitCard = hand.splitHand();
        hand = new Hand(dealer, splitCard);
    }

    public List<Card> getCards() {
        return hand.getCards();
    }

    public Hand getHand() {
        return hand;
    }
}
