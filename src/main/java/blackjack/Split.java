// Split.java
package blackjack;

import blackjack.controllers.CardSelector;
import java.util.ArrayList;
import java.util.List;

public class Split {
    /**
     * Splits a hand into two separate hands using the existing two cards.
     * Each new hand will be initialized with one of the original cards and a new card.
     */
    public static List<Hand> performSplit(Hand originalHand, CardSelector selector) {
        List<Card> cards = originalHand.getCards();
        if (cards.size() != 2 || !cards.get(0).getRank().equals(cards.get(1).getRank())) {
            throw new IllegalArgumentException("Cannot split: cards are not a matching pair.");
        }

        List<Hand> splitHands = new ArrayList<>();

        // First hand: first card + new card
        Hand hand1 = new Hand(selector, cards.get(0));
        hand1.hit();
        splitHands.add(hand1);

        // Second hand: second card + new card
        Hand hand2 = new Hand(selector, cards.get(1));
        hand2.hit();
        splitHands.add(hand2);

        return splitHands;
    }
}
