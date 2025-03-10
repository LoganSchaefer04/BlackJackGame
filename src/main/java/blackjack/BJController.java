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

    private BlackJackGame game;

    @FXML
    public void initialize() {
        game = new BlackJackGame();
        game.initRound();
        //updateUI();
    }

    @FXML
    protected void onHit() {
        game.playerTurn();
        //updateUI();
    }

    @FXML
    protected void onStay() {
        game.dealerTurn();
        game.determineWinner();
        //updateUI();
    }

    @FXML
    protected void onRestart() {
        game.initRound();
        //updateUI();
    }

    /**private void updateUI() {
        updateCardDisplay(playerCard1, playerCard2, game.getPlayer().getHand());
        updateCardDisplay(dealerCard1, dealerCard2, game.getDealer().getHand());
        gameStatus.setText(game.getGameStatus());
        hitButton.setDisable(game.getPlayer().hasBust());
        stayButton.setDisable(game.getPlayer().hasBust());
    } */    @FXML

    private void updateCardDisplay(ImageView card1, ImageView card2, Card[] hand) {
        if (hand.length > 0) {
            card1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/CardImages/" + hand[0].getRank() + hand[0].getSuit() + ".png"))));
        } else {
            card1.setImage(null);
        }
        if (hand.length > 1) {
            card2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/CardImages/" + hand[1].getRank() + hand[1].getSuit() + ".png"))));
        } else {
            card2.setImage(null);
        }
    }
}
