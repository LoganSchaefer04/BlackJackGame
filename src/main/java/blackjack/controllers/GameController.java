package blackjack.controllers;

import blackjack.Card;
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
import javafx.util.Duration;

import java.io.File;
import java.util.List;

public class GameController {
    private BlackJackGame blackJackGame;
    @FXML
    private ImageView dealerCard1, dealerCard2, playerCard1, playerCard2;

    @FXML
    private Button hitButton, stayButton, restartButton, hintButton;

    @FXML
    private Label resultLabel, hintLabel, playerValueLabel, dealerValueLabel, currencyLabel;

    @FXML
    private HBox playerCardImageBox, dealerCardImageBox;
    @FXML
    AnchorPane  mainPane, topPane, bottomPane;

    public void initialize() {
        initializeCardsUI();
        HBox.setHgrow(playerCardImageBox, Priority.ALWAYS);
        HBox.setHgrow(dealerCardImageBox, Priority.ALWAYS);
        currencyLabel.setText(blackJackGame.getCurrency());
    }

    public GameController(BlackJackGame blackJackGame) {
        this.blackJackGame = blackJackGame;
    }


    @FXML
    protected void onHit() {
        // Player pressed hit.
        Card card = blackJackGame.hitPlayer();
        loadPNG(playerCardImageBox, card);
        playerValueLabel.setText(Integer.toString(blackJackGame.getPlayerHandValue()));

        if (blackJackGame.getPlayerHandValue() > 21) {
            resultLabel.setText("You Lose!");
            restartButton.setVisible(true);
        }

    }

    @FXML
    protected void onStay() {
        // Player pressed stay.
        blackJackGame.playerStays();
        revealDealerCards();

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
                        currencyLabel.setText(blackJackGame.getCurrency());
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


    @FXML
    protected void onRestart() {
        blackJackGame.initRound();
        initializeCardsUI();
        hitButton.setDisable(false);
        stayButton.setDisable(false);
        hintLabel.setText("");
        resultLabel.setText("");
        playerCardImageBox.setLayoutX(216);
        dealerCardImageBox.setLayoutX(216);
        restartButton.setVisible(false);

    }

    @FXML
    public void loadPNG(HBox container, Card card) {
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
    public void displayHint() {
        hintLabel.setText(blackJackGame.getHint());
    }
}
