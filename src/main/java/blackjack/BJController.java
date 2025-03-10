package blackjack;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BJController {

    @FXML
    private ImageView dealerCard1, dealerCard2, playerCard1, playerCard2;

    @FXML
    private Button hitButton, stayButton, restartButton;

    @FXML
    private Label gameStatus;

    private List<Card> playerHand = new ArrayList<>();
    private List<Card> dealerHand = new ArrayList<>();
    private CardSelector cardSelector = new CardSelector("random");

    @FXML
    protected void onHit() {
        if (playerHand.size() < 2) {
            Card newCard = cardSelector.getRandomCard(getHandValue(playerHand));
            playerHand.add(newCard);
            updateCardDisplay(playerCard1, playerCard2, playerHand);
        }

        if (getHandValue(playerHand) > 21) {
            gameStatus.setText("Player Busts! Dealer Wins.");
            endGame();
        }
    }

    @FXML
    protected void onStay() {
        gameStatus.setText("Player stays. Dealer's turn.");
        dealerTurn();
    }

    private void dealerTurn() {
        while (getHandValue(dealerHand) < 17) {
            Card newCard = cardSelector.getRandomCard(getHandValue(dealerHand));
            dealerHand.add(newCard);
        }
        updateCardDisplay(dealerCard1, dealerCard2, dealerHand);

        int dealerValue = getHandValue(dealerHand);
        int playerValue = getHandValue(playerHand);

        if (dealerValue > 21 || playerValue > dealerValue) {
            gameStatus.setText("Player Wins!");
        } else if (dealerValue == playerValue) {
            gameStatus.setText("It's a Tie!");
        } else {
            gameStatus.setText("Dealer Wins!");
        }
        endGame();
    }

    private void updateCardDisplay(ImageView card1, ImageView card2, List<Card> hand) {
        if (!hand.isEmpty()) {
            card1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/CardImages/" + hand.get(0).getRank() + hand.get(0).getSuit() + ".png"))));
        }
        if (hand.size() > 1) {
            card2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/CardImages/" + hand.get(1).getRank() + hand.get(1).getSuit() + ".png"))));
        }
    }

    private int getHandValue(List<Card> hand) {
        int value = 0;
        int aceCount = 0;
        for (Card card : hand) {
            value += card.getValue();
            if (card instanceof Ace) {
                aceCount++;
            }
        }
        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }
        return value;
    }

    private void endGame() {
        hitButton.setDisable(true);
        stayButton.setDisable(true);
    }

    @FXML
    protected void onRestart() {
        playerHand.clear();
        dealerHand.clear();
        gameStatus.setText("New Game Started!");
        hitButton.setDisable(false);
        stayButton.setDisable(false);
        playerCard1.setImage(null);
        playerCard2.setImage(null);
        dealerCard1.setImage(null);
        dealerCard2.setImage(null);
    }
}
