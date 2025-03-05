package blackjack;
import java.util.Random;

public class CardSelector {
    private String cardSelectorType;
    private static final String[] ranks = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
            "Jack", "Queen", "King"};
    private static final String[] suits = {"Heart", "Diamond", "Club", "Spade"};


    /** CardSelector Constructor
     * Saves user settings for card generation settings
     * @param cardSelectorType - the way in which cards will be generated.
     */
    public CardSelector(String cardSelectorType) {
        // This constructor will remain empty for now.
        // For now, all cards will be generated randomly.
        // Use any string for now when creating instance of CardSelector
        // this.cardSelectorType = cardSelectorType;
    }

    /** Checks card generation settings and calls necessary method for generation.
     * @return Next card instance.
     * @param value The value of the hand at current time.
     */
    public Card getNextCard(int value) {
        // This method only calls random card for now.
        // This will be updated as we continue in the project with different card generation styles.
        return getRandomCard(value);
    }

    /**
     * Generates a random card based on random number generator.
     * @return a random card object.
     * @param value The value of the hand.
     */
    private Card getRandomCard(int value) {
        Random randomNumberGenerator = new Random();

        int rankNumber = randomNumberGenerator.nextInt(12);
        int suitNumber = randomNumberGenerator.nextInt(3);

        if (rankNumber == 0) {
            return new Ace("Ace", suits[suitNumber], value);
        }

        return new Card(ranks[rankNumber], suits[suitNumber], rankNumber);
    }

}
