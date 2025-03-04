package blackjack;
import javafx.scene.image.Image;

public class Card {
    String suit;
    String rank;
    int value;
    private Image image;

    /**
     * Constructor for each card
     * @param rank String holding rank of card.
     * @param suit String holding suit of card.
     * @param value - integer holding value of card.
     */
    public Card(String rank, String suit, int value) {
        this.suit = suit;
        this.rank = rank;

        if (value >= 9) {
            this.value = 10;
        } else if (value == 0) {
            this.value = 0;
        } else {
            this.value = value + 1;
        }

        // These lines need to stay commented out until we have all the card images ready in resources.
        // String imageName = rank + suit;
        // Image image = new Image("src/main/resources/CardImages/" + imageName);
    }

    /** Return the value of card.
     *
     * @return Value of each card. Returns 0 if card is an ace.
     */
    public int getValue() {
        return value;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public Image getImage() {
        // Continues to return null until images are in resources.
        // return image;
        return null;
    }
}
