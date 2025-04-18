package blackjack.FrontEnd;
import blackjack.GameComponents.Dealer;

import blackjack.GameComponents.BlackJackGame;
import blackjack.GameComponents.Card;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import blackjack.features.CardCounting;
import blackjack.features.Bank;
import blackjack.features.Hint;

import java.io.File;
import java.util.List;

public class GameController {
    private BlackJackGame blackJackGame;
    private CardCounting cardCounting;  // Declare cardCounting

    @FXML
    private Button hitButton, stayButton, restartButton, hintButton, splitButton, doubleDownButton, tipDealerButton;

    @FXML
    private Label resultLabel, hintLabel, playerValueLabel, dealerValueLabel, currencyLabel, betLabel, tipAmountLabel;

    @FXML
    private Label cardCountLabel;  // FXML label

    @FXML
    private HBox playerCardImageBox, dealerCardImageBox;
    @FXML
    AnchorPane  mainPane, topPane, bottomPane;

    @FXML
    Polygon increaseTipButton, decreaseTipButton;

    public void initialize() {

        cardCounting = new CardCounting(); // Initialize CardCounting here

        initializeCardsUI();
        currencyLabel.setText(blackJackGame.getCurrency());
        splitButton.setVisible(blackJackGame.splitabilibity());

        // Force update of the running count after the round is initialized
        cardCountLabel.setText("Count: " + blackJackGame.getRunningCount());
    }

    public GameController(BlackJackGame blackJackGame) {
        this.blackJackGame = blackJackGame;
    }

    public GameController() {
        // Empty Constructor for TutorialController
    }


    @FXML
    protected void onHit() {
        // Player pressed hit.
        Card card = blackJackGame.hitPlayer();
        loadPNG(playerCardImageBox, card);
        playerValueLabel.setText(Integer.toString(blackJackGame.getPlayerHandValue()));

        // Update the running count on initialization
        cardCountLabel.setText("Count: " + blackJackGame.getRunningCount());

        if (blackJackGame.getPlayerHandValue() > 21) {
            if (blackJackGame.playerHasNextHand()) {
                blackJackGame.nextSplitHand();
                initializeCardsUI();
            }
            resultLabel.setText("You Lose!");
            restartButton.setVisible(true);
            stayButton.setVisible(false);
            hitButton.setVisible(false);
            splitButton.setVisible(false);
        }

    }

    @FXML
    protected void onStay() {
        if (blackJackGame.playerStays()) {
            blackJackGame.nextSplitHand();
            initializeCardsUI();
            return;
        } else {
            // Player pressed stay.
            initializeCardsUI();
            hitButton.setVisible(false);
            stayButton.setVisible(false);
            splitButton.setVisible(false);
            doubleDownButton.setVisible(false);  // Hide Double Down button after Stay
            revealDealerCards();
        }
    }

    @FXML
    protected void onSplit() {
        double bet = blackJackGame.split();
        currencyLabel.setText(Double.toString(bet));
        splitButton.setVisible(false);
        initializeCardsUI();
    }

    @FXML
    protected void onDoubleDown() {
        if (blackJackGame.getPlayerHandValue() <= 21) {
            // Double the player's bet
            blackJackGame.doubleDown();

            // Show one more card to the player (one card only)
            Card card = blackJackGame.hitPlayer(); // This should only add one card
            loadPNG(playerCardImageBox, card);

            // Update the player's hand value after drawing the card
            playerValueLabel.setText(Integer.toString(blackJackGame.getPlayerHandValue()));

            // Disable the Double Down button after it's used
            doubleDownButton.setDisable(true);

            // Disable the Hit button and Split button as no more moves are allowed
            hitButton.setDisable(true);
            stayButton.setDisable(true);

            // Check if player has busted
            if (blackJackGame.getPlayerHandValue() > 21) {
                resultLabel.setText("You Lose!");
                restartButton.setVisible(true);
                return;  // Player has busted, no need to continue
            }

            // If the player's value is still valid (<= 21), allow the dealer to play
            blackJackGame.playerStays();  // Proceed with the dealer's turn
            revealDealerCards();  // Show dealer's cards
        }
    }







    protected void revealDealerCards() {
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

                        // Update the card count as each card is drawn
                        cardCounting.updateRunningCount(dealerCards.get(index));
                    }),
                    new KeyFrame(Duration.seconds(dealerCards.size()), event -> {
                        resultLabel.setText(blackJackGame.getResult());
                        restartButton.setVisible(true);
                        currencyLabel.setText(blackJackGame.getCurrency());
                    })
            );

            timeline.setCycleCount(1);
            timeline.play();
        }

        // After dealer finishes drawing cards, update the count on the label
        cardCountLabel.setText("Count: " + blackJackGame.getRunningCount());

    }

    protected void initializeCardsUI() {
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


    @FXML
    protected void onRestart() {
        blackJackGame.initRound();
        initializeCardsUI();
        hitButton.setDisable(false);
        stayButton.setDisable(false);
        doubleDownButton.setDisable(false);  // Enable the button again
        hintLabel.setText("");
        resultLabel.setText("");
        playerCardImageBox.setLayoutX(216);
        dealerCardImageBox.setLayoutX(216);
        restartButton.setVisible(false);
        currencyLabel.setText(blackJackGame.getCurrency());
        hitButton.setVisible(true);
        stayButton.setVisible(true);
        doubleDownButton.setVisible(true);



        splitButton.setVisible(blackJackGame.splitabilibity());

        // Reinitialize the cardCounting object for the new round
        cardCounting = new CardCounting();  // This resets the count to 0

        // Update the count based on the new cards dealt
        for (Card card : blackJackGame.getPlayerCards()) {
            cardCounting.updateRunningCount(card);  // Update count for player's cards
        }

        for (Card card : blackJackGame.getDealerCards()) {
            cardCounting.updateRunningCount(card);  // Update count for dealer's cards
        }

        // Update the card count label
        cardCountLabel.setText("Count: " + blackJackGame.getRunningCount());

    }

    @FXML
    protected void loadPNG(HBox container, Card card) {
        String cardName = card.getRank() + card.getSuit();
        String path = "src/main/resources/CardImages/" + cardName + ".png";
        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(180);
        imageView.setFitHeight(260);

        if (container.getWidth() > 334) {
            container.setLayoutX(container.getLayoutX() - 50);
        }
        container.getChildren().add(imageView);

    }

    @FXML
    protected void displayHint() {
        hintLabel.setText(blackJackGame.getHint());
    }

    @FXML
    protected void increaseTip() {
        int tipAmount = Integer.parseInt(tipAmountLabel.getText());
        tipAmount += 5;
        tipAmountLabel.setText(Integer.toString(tipAmount));
    }

    @FXML
    protected void decreaseTip() {
        int tipAmount = Integer.parseInt(tipAmountLabel.getText());
        tipAmount -= 5;
        tipAmountLabel.setText(Integer.toString(tipAmount));
    }

    @FXML
    protected void tipDealer() {
        blackJackGame.tipDealer(Integer.parseInt(tipAmountLabel.getText()));
        currencyLabel.setText(blackJackGame.getCurrency());

    }
}
