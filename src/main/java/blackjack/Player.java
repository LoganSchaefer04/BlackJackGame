package blackjack;

import blackjack.controllers.CardSelector;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for players and their actions
 */
public class Player {
    private Hand hand;
    private CardSelector dealer;

    private List<Hand> hands = new ArrayList<>();
    private int activeHandIndex = 0;


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
        return getActiveHand().hit();
    }


    /**
     * Clears any previous hand, creates a new initial hand with 2 cards in it
     */
    public void initHand() {
        Hand newHand = new Hand(dealer);
        newHand.initialize();
        hands.clear();
        hands.add(newHand);
        activeHandIndex = 0;
    }



    /**
     * get value of hand
     * @return int, value of hand
     */
    public int getHandValue() {
        return getActiveHand().getHandValue();
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

    public Hand getActiveHand() {
        return hands.get(activeHandIndex);
    }

    public boolean canSplit() {
        if (hands.size() > 1) return false;
        List<Card> cards = hands.get(0).getCards();
        System.out.println("Checking for split: " + cards.get(0).getRank() + " and " + cards.get(1).getRank());
        return cards.size() == 2 && cards.get(0).getRank().equals(cards.get(1).getRank());
    }


    public void split() {
        Hand current = hands.get(0);
        List<Hand> splitHands = Split.performSplit(current, dealer);
        hands = splitHands;
        activeHandIndex = 0;
    }

    public boolean hasNextHand() {
        return activeHandIndex < hands.size() - 1;
    }

    public void moveToNextHand() {
        if (hasNextHand()) {
            activeHandIndex++;
        }
    }

    public void resetHands() {
        hands = new ArrayList<>();
        activeHandIndex = 0;
    }



}
