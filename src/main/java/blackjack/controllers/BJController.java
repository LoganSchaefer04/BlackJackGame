package blackjack.controllers;

import blackjack.Card;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Objects;

public class BJController {

    @FXML
    private ImageView dealerCard1, dealerCard2, playerCard1, playerCard2;

    @FXML
    private Button hitButton, stayButton, restartButton;

    @FXML
    private Label gameStatus;

    private BlackJackGame blackJackGame;

    public BJController(BlackJackGame blackJackGame) {
        this.blackJackGame = blackJackGame;
    }

    @FXML
    protected void onHit() {
        // Player pressed hit.
        blackJackGame.hitPlayer();
    }


    @FXML
    protected void onStay() {
        // Player pressed stay.
        blackJackGame.playerStays();
    }

    private void updateCardDisplay(ImageView card1, ImageView card2, List<Card> hand) {
        if (!hand.isEmpty()) {
            card1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/CardImages/" + hand.get(0).getRank() + hand.get(0).getSuit() + ".png"))));
        }
        if (hand.size() > 1) {
            card2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/CardImages/" + hand.get(1).getRank() + hand.get(1).getSuit() + ".png"))));
        }
    }

    @FXML
    protected void onRestart() {
        blackJackGame.initRound();
        hitButton.setDisable(false);
        stayButton.setDisable(false);
        playerCard1.setImage(null);
        playerCard2.setImage(null);
        dealerCard1.setImage(null);
        dealerCard2.setImage(null);
    }
}