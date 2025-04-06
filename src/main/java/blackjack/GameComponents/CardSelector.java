package blackjack.GameComponents;

import blackjack.GameComponents.Ace;
import blackjack.GameComponents.Card;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CardSelector {
    private final String generationStyle;
    private List<Card> deckOfCards;
    private static final String[] ranks = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
            "Jack", "Queen", "King"};
    private static final String[] suits = {"Heart", "Diamond", "Club", "Spade"};

    private int cardCount = 0;


    /** CardSelector Constructor
     * Saves user settings for card generation settings
     * @param generationStyle - the way in which cards will be generated.
     */
    public CardSelector(String generationStyle) {
        // Assign the specified generation style.
        if (generationStyle.equals("Shuffle")) {
            this.generationStyle = generationStyle;
            deckOfCards = new LinkedList<>();
            initializeShuffledDeck();
        } else {
            // Random card generation
            this.generationStyle = generationStyle;
        }
    }

    /** Checks card generation settings and calls necessary method for generation.
     * @return Next card instance.
     * @param value The value of the hand at current time.
     */
    public Card getNextCard(int value, int boost) {
        System.out.println("HERE");
        Random boostChecker = new Random();
        Card card;
        if (boostChecker.nextInt(100) < boost) {
            card = getBestCard(value);
        }
        if (generationStyle.equals("Shuffle")) {
            card = dequeueCard(value);
        } else {
            card = getRandomCard(value);
        }
        if (card.getValue() >= 2 && card.getValue() <= 6) {
            cardCount++;
        } else if (card.getValue() >= 10 || card.getValue() == 1) {
            cardCount--;
        }
        return card;
    }

    private Card getBestCard(int value) {
        Random rng = new Random();
        if (value < 10) {
            return new Card(ranks[11 - (value + 2)], suits[rng.nextInt(3)], 11 -(value + 2));
        } else if (value == 10 || value == 20) {
            return new Ace("Ace", suits[rng.nextInt(3)], value);
        } else  if (value < 21) {
            return new Card(ranks[21 - (value + 2)], suits[rng.nextInt(3)], 21 - (value + 2));
        } else {
            return getNextCard(value, 0);
        }
    }

    /**
     * Generates a random card based on random number generator.
     * @return a random card object.
     * @param value The value of the hand.
     */
    private Card getRandomCard(int value) {
        Random randomNumberGenerator = new Random();

        // Generate a random rank and random suit.
        int rankNumber = randomNumberGenerator.nextInt(12);
        int suitNumber = randomNumberGenerator.nextInt(3);

        // Create new instance of ace if card is an ace.
        if (rankNumber == 0) {
            return new Ace("Ace", suits[suitNumber], value);

        // Return a regular card.
        } else {
            return new Card(ranks[rankNumber], suits[suitNumber], rankNumber);
        }
    }

    /**
     * Returns the next card in a shuffled deck of cards.
     *
     * @param value The value of hand at the current time.
     * @return The next card in the shuffled deck with the correct value
     */
    private Card dequeueCard(int value) {
        // Get next card in the list.
        if (!deckOfCards.isEmpty()) {
            Card card = deckOfCards.get(0);
            deckOfCards.remove(0);


            // Check if card is an ace, and lower the value if value of 11 would bust player.
            if (card instanceof Ace && value >= 11) {
                ((Ace) card).lowerValue();
            }
            return card;
        }
        return null;
    }

    /**
     * Generates four decks of cards and shuffles them.
     */
    private void initializeShuffledDeck() {
        for (int x = 0; x < 4; x++) {
            for (int i = 0; i < 4; i++) {
                deckOfCards.add(new Ace("Ace", suits[i], 0));
                for (int j = 0; j < 12; j++) {
                    deckOfCards.add(new Card(ranks[j], suits[i], j));
                }
            }
        }
        Collections.shuffle(deckOfCards);
    }

    public int getCardCount() {
        return cardCount;
    }


}