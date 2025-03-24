package blackjack.controllers;
import blackjack.Card;
import blackjack.Hint;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import java.io.File;
import java.util.List;

public class BJController {

    @FXML
    private ImageView dealerCard1, dealerCard2, playerCard1, playerCard2;

    @FXML
    private Button hitButton, stayButton, restartButton, hintButton, splitButton;


    @FXML
    private Label resultLabel, hintLabel, playerValueLabel, dealerValueLabel;

    @FXML
    private HBox playerCardImageBox;
    @FXML
    private HBox dealerCardImageBox;

    private BlackJackGame blackJackGame;

    public void initialize() {


    }


    public void setGame(BlackJackGame game) {
        this.blackJackGame = game;
        initializeCardsUI();
    }


    @FXML
    protected void onHit() {
        // Player pressed hit.
        Card card = blackJackGame.hitPlayer();
        loadPNG(playerCardImageBox, card);

        // Always update the hand value label
        int currentHandValue = blackJackGame.getPlayerHandValue();
        playerValueLabel.setText(Integer.toString(currentHandValue));

        // If current hand busted, either switch to next or end round
        if (currentHandValue > 21) {
            if (blackJackGame.playerHasNextHand()) {
                blackJackGame.moveToNextHand();
                reloadPlayerCards(); // Show next hand
                playerValueLabel.setText(Integer.toString(blackJackGame.getPlayerHandValue()));
            } else {
                resultLabel.setText("You Lose!");
                hitButton.setDisable(true);
                stayButton.setDisable(true);
                splitButton.setDisable(true);
                blackJackGame.playerStays();
                revealDealerCards();
            }
        }
    }



    @FXML
    protected void onStay() {
        if (blackJackGame.playerHasNextHand()) {
            blackJackGame.moveToNextHand();
            reloadPlayerCards();
            playerValueLabel.setText(Integer.toString(blackJackGame.getPlayerHandValue()));
        } else {
            blackJackGame.playerStays();
            revealDealerCards();
        }
    }


    @FXML
    protected void onSplit() {

        if (blackJackGame.canSplit()) {

            blackJackGame.split();
            reloadPlayerCards();
            playerValueLabel.setText(Integer.toString(blackJackGame.getPlayerHandValue()));
            splitButton.setDisable(true);
        } else {
            System.out.println("Cannot split: either cards don't match or not enough currency.");
        }
    }



    public void revealDealerCards() {
        List<Card> dealerCards = blackJackGame.getDealerCards();
        dealerCardImageBox.getChildren().remove(1);
        int dealerValue = blackJackGame.getDealerUpCardValue();
        for (int i = 1; i < dealerCards.size(); i++) {
            final int index = i;
            dealerValue += dealerCards.get(i).getValue();
            final String stringHandValue = Integer.toString(dealerValue);

            // Create a Timeline to run the image loading with a delay
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(i), event -> {
                        loadPNG(dealerCardImageBox, dealerCards.get(index));
                        dealerValueLabel.setText(stringHandValue);
                    }),
                    new KeyFrame(Duration.seconds(dealerCards.size()), event -> {
                        resultLabel.setText(blackJackGame.determineWinner());
                        restartButton.setVisible(true);
                        restartButton.setDisable(false);
                    })
            );

            timeline.setCycleCount(1); // Run once
            timeline.play();
        }
    }

    private void initializeCardsUI() {
        playerCardImageBox.getChildren().clear();
        dealerCardImageBox.getChildren().clear();
        restartButton.setVisible(false);
        List<Card> cardList = blackJackGame.getPlayerCards();
        for (Card card : cardList) {
            loadPNG(playerCardImageBox, card);
        }

        cardList = blackJackGame.getDealerCards();
        Card card = cardList.get(0);
        loadPNG(dealerCardImageBox, card);
        card = new Card("Blank", "Card");
        loadPNG(dealerCardImageBox, card);

        dealerValueLabel.setText(Integer.toString(blackJackGame.getDealerUpCardValue()));
        playerValueLabel.setText(Integer.toString(blackJackGame.getPlayerHandValue()));
    }

    private void reloadPlayerCards() {
        playerCardImageBox.getChildren().clear();
        List<Card> cards = blackJackGame.getPlayerCards();
        for (Card card : cards) {
            loadPNG(playerCardImageBox, card);
        }
    }



    @FXML
    public void onRestart() {
        System.out.println("↻ Play Again clicked!");

        // Step 1: Clear UI early
        playerCardImageBox.getChildren().clear();
        dealerCardImageBox.getChildren().clear();
        resultLabel.setText("");
        hintLabel.setText("");
        playerValueLabel.setText("");
        dealerValueLabel.setText("");
        playerCard1.setImage(null);
        playerCard2.setImage(null);
        dealerCard1.setImage(null);
        dealerCard2.setImage(null);
        playerCardImageBox.setLayoutX(216);
        dealerCardImageBox.setLayoutX(216);

        // Step 2: Reset game state (this will deal new hands)
        blackJackGame.initRound();

        // Step 3: Rebuild UI after cards are dealt
        initializeCardsUI();

        // Step 4: Re-enable buttons
        hitButton.setDisable(false);
        stayButton.setDisable(false);
        splitButton.setDisable(false);
        restartButton.setVisible(false);
    }


    @FXML
    public void loadPNG(HBox hBox, Card card) {
        String cardName = card.getRank() + card.getSuit();
        String path = "src/main/resources/CardImages/" + cardName + ".png";
        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(144);
        if (hBox.getWidth() > 334) {
            hBox.setLayoutX(hBox.getLayoutX() - 50);
        }
        hBox.getChildren().add(imageView);
    }

    @FXML
    public void displayHint() {
        hintLabel.setText(blackJackGame.getHint());
    }
}