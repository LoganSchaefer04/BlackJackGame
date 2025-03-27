package blackjack;


import java.util.ArrayList;
import java.util.List;

/**
 * Class for players and their actions
 */
public class Player {
    private Hand currentHand;
    private CardSelector dealer;

    private List<Hand> handsList = new ArrayList<>();
    private int currentHandIndex = 0;


    /**
     * Constructor
     * @param dealer, used to generate new cards when needed
     */
    public Player(CardSelector dealer) {
        this.dealer = dealer;
        currentHand = new Hand(dealer, 5);
    }

    public boolean hasBust() {
        return currentHand.hasBust();
    }


    public Card hit() {
        return currentHand.hit();
    }


    /**
     * Clears any previous hand, creates a new initial hand with 2 cards in it
     */
    public void initHand() {
        handsList.clear();
        currentHand = new Hand(dealer, 5);
        handsList.add(currentHand);
    }

    /**
     * get value of hand
     * @return int, value of hand
     */
    public int getHandValue() {
        return currentHand.getHandValue();
    }


    public List<Card> getCards() {
        return currentHand.getCards();
    }

    public Hand getHand() {
        return currentHand;
    }
    public int getHandBet() {
        return currentHand.getBet();
    }

    public void splitCurrentHand() {
        handsList.add(new Hand(dealer, currentHand.splitHand(), currentHand.getBet()));
        System.out.println(handsList.size());
        currentHand.hit();
    }

    public boolean canSplit() {
        return currentHand.canSplit();
    }

    public boolean hasNextHand() {
        return currentHandIndex < handsList.size() - 1;
    }

    public boolean moveToNextHand() {
        if (hasNextHand()) {
            currentHand = handsList.get(++currentHandIndex);
            return true;
        } else {
            currentHandIndex = 0;
            System.out.println("setting hand index to 0");
            return false;
        }
    }

    public void resetHands() {
        handsList = new ArrayList<>();
        currentHandIndex = 0;
    }



}
