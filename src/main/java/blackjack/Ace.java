package blackjack;

public class Ace extends Card {

    private int value;

    /**
     * Constructor for ace uses different super constructor method because the value is variable.
     *
     * @param rank String holding rank of card.
     * @param suit String holding suit of card.
     */
    public Ace(String rank, String suit) {
        super(rank, suit);
    }

    /**
     * Returns the value of the ace based on the count of the player's hand.
     * The value of the ace is not set until it is needed, because CardSelector cannot determine the value of the hand.
     *
     * @param count Used to determine if the ace is worth 11 or 1.
     * @return The value of the ace.
     */
    @Override
    public int getValue(int count) {
        // Check if player would bust if ace is counted as 11.
        if (count >= 11) {
            // Card is worth 1.
            value = 1;
            return value;

        // Player would not bust if ace is counted as 11.
        } else {
            // Card is worth 11.
            value = 11;
            return value;
        }
    }

    /**
     * Changes the value of the ace to 1.
     */
    public void lowerValue() {
        value = 1;
    }

}
