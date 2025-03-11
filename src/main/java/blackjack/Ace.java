package blackjack;

public class Ace extends Card {

    private int value;

    /**
     * Constructor for ace uses different super constructor method because the value is variable.
     *
     * @param rank String holding rank of card.
     * @param suit String holding suit of card.
     * @param value The value of the hand at current time.
     */
    public Ace(String rank, String suit, int value) {
        super(rank, suit);
        // Check if player would bust if ace is counted as 11.
        if (value >= 11) {
            // Card is worth 1.
            this.value = 1;
            // Player would not bust if ace is counted as 11.
        } else {
            // Card is worth 11.
            this.value = 11;
        }
    }

    @Override
    public int getValue() {
        return value;
    }

    /**
     * Changes the value of the ace to 1.
     */
    public void lowerValue() {
        value = 1;
    }

}