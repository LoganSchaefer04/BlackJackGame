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
        //this.cardSelectorType = cardSelectorType;
    }

    /** Checks card generation settings and calls necessary method for generation.
     * @return Next card instance.
     */
    public Card getNextCard() {
        // This method will call another method only right now.
        // This will be updated as we continue in the project with different card generation styles.
        return getRandomCard();
    }

    /**
     * Generates a random card based on random number generator.
     * @return a random card object.
     */
    private Card getRandomCard() {
        Random randomNumberGenerator = new Random();

        int rankNumber = randomNumberGenerator.nextInt(12);
        int suitNumber = randomNumberGenerator.nextInt(3);

        return new Card(ranks[rankNumber], suits[suitNumber], rankNumber);
    }

}
