package blackjack;

public class Ace extends Card {

    private String suit;
    private String rank;

    /**
     * Same constructor for Ace as any other card. Value is not used but needs to remain to keep the compiler happy.
     * @param rank String holding rank of card.
     * @param suit String holding suit of card.
     * @param value - integer holding value of card.
     */
    public Ace(String rank, String suit, int value) {
        super(rank, suit, value);
    }

    /**
     * Returns the value of the ace based on the count of the player's hand.
     *
     * @param count Used to determine if the ace is worth 11 or 1.
     * @return The value of the ace.
     */
    @Override
    public int getValue(int count) {
        // Check if player would bust if ace is counted as 11.
        if (count >= 11) {
            // Card is worth 1.
            return 1;

        // Player would not bust if ace is counted as 11.
        } else {
            // Card is worth 11.
            return 11;
        }
    }

}
