package blackjack.features;

import blackjack.GameComponents.Card;

public class CardCounting {
    private int runningCount;

    // Default constructor (no label)
    public CardCounting() {
        this.runningCount = 0;
    }

    // Method to update the count based on the card's value
    public void updateRunningCount(Card card) {
        int cardValue = card.getValue();
        if (cardValue >= 2 && cardValue <= 6) {
            runningCount++; // 2-6 → +1
        } else if (cardValue == 10 || cardValue == 11 || cardValue == 12 || cardValue == 13) {
            runningCount--; // 10, Jack, Queen, King → -1
        }
    }


    //Trying to update file to commit and push
    // Get the current running count
    public int getRunningCount() {
        return runningCount;
    }
}
