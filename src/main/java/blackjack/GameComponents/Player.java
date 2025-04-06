package blackjack.GameComponents;


import blackjack.GameComponents.Card;
import blackjack.GameComponents.CardSelector;
import blackjack.GameComponents.Hand;

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
    private int oddsBoost; // A value to determine how much a player's odds are boosted.
    private int boostsLeft; // How many more hands the player's odds will be boosted.


    /**
     * Constructor
     * @param dealer, used to generate new cards when needed
     */
    public Player(CardSelector dealer) {
        this.dealer = dealer;
    }

    /**
     * Add a new card to the player's current hand.
     * @return If the player's hand value exceeded 21.
     */
    public boolean hit() {
        return currentHand.hit(oddsBoost);
    }
    public void stay() {
        currentHand.stay();
    }

    /**
     * Clears any previous hand, creates a new initial hand with 2 cards in it
     */
    public void initHand() {
        handsList.clear();
        if (boostsLeft > 0) {
            currentHand = new Hand(dealer, 5, oddsBoost);
        } else {
            currentHand = new Hand(dealer, 5, 0);
        }
        handsList.add(currentHand);
        boostsLeft--;
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
    public List<Hand> getHandsList() {
        return handsList;
    }

    public Hand getCurrentHand() {
        return currentHand;
    }
    public int getHandBet() {
        return currentHand.getBet();
    }

    public void splitCurrentHand() {
        handsList.add(new Hand(dealer, currentHand.splitHand(), currentHand.getBet(), oddsBoost));
        System.out.println(handsList.size());
        currentHand.hit(oddsBoost);
    }

    public boolean canSplit() {
        return currentHand.canSplit();
    }

    public boolean hasNextHand() {
        return currentHandIndex < handsList.size() - 1;
    }
    public boolean hasPreviousHand() {
        return currentHandIndex >= 1;
    }
    public int getCurrentHandIndex() {
        return currentHandIndex;
    }


    public boolean moveToNextHand() {
        if (hasNextHand()) {
            currentHand = handsList.get(++currentHandIndex);
            return true;
        } else {
            currentHandIndex = 0;
            return false;
        }
    }

    public void moveToPreviousHand() {
        if (hasPreviousHand()) {
            currentHand = handsList.get(--currentHandIndex);
        }
    }
    public void moveToForwardHand() {
        if (hasNextHand()) {
            currentHand = handsList.get(++currentHandIndex);
        }
    }

    public void resetHands() {
        handsList.clear();
        currentHandIndex = 0;
    }

    /**
     * Boosts the odds of the player if they tip the dealer.
     *
     * @param tipAmount The amount of money the player tipped
     * @param bankAmount The amount of money left in the player's bank.
     */
    public void tipDealer(double bankAmount, int tipAmount) {
        if (tipAmount > bankAmount / 10) {
            oddsBoost = 40;
            boostsLeft = 10;
        } else {
            oddsBoost  = 20;
            boostsLeft = 5;
        }
    }

    public boolean hasAllHandsOver() {
        for (Hand hand : handsList) {
            if (!hand.isOver()) {
                return false;
            }
        }
        return true;
    }

    public void moveToOpenHand() {
        for (Hand hand : handsList) {
            if (!hand.isOver()) {
                System.out.println(handsList.indexOf(hand));
                currentHandIndex = handsList.indexOf(hand);
                currentHand = hand;
            }
        }
    }

    public boolean hasAnyStays() {
        for (Hand hand : handsList) {
            if (hand.isStayed()) {
                return true;
            }
        }
        return false;
    }

    public String recentCardRank() {
        return currentHand.recentCardRank();
    }

    public String recentCardSuit() {
        return currentHand.recentCardSuit();
    }

    public int getCardCount() {
        return currentHand.getCardCount();
        }
    }


