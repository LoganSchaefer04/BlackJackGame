// Card.java file

package blackjack;
import javafx.scene.image.Image;

public class Card {
    private String suit;
    private String rank;
    private int value;
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
        image = null;

        if (value >= 9) {
            this.value = 10;
        } else {
            this.value = value + 2;
        }

        // These lines need to stay commented out until we have all the card images ready in resources.
        // String imageName = rank + suit;
        // image = new Image("src/main/resources/CardImages/" + imageName);
    }

    /**
     * Constructor is used to create ace, where value is not set until retrieved.
     * @param rank String holding rank of card.
     * @param suit String holding suit of card.
     */
    public Card(String rank, String suit) {
        this.suit = suit;
        this.rank = rank;
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

    public void printCard() {
        System.out.print(getRank() + " of " + getSuit());
    }

    public void lowerValue() {

    }

    public void raiseValue() {

    }

}
