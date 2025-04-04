package blackjack.FrontEnd;

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
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

public class GameController {
    private BlackJackGame blackJackGame;
    private boolean revealedCards;

    @FXML
    private Button hitButton, stayButton, restartButton, hintButton, splitButton, tipDealerButton;

    @FXML
    private Button previousHandButton, nextHandButton;
    @FXML
    private Label currentHandLabel, unplayedHandLabel;

    @FXML
    private Label resultLabel, hintLabel, playerValueLabel, dealerValueLabel, currencyLabel, betLabel, tipAmountLabel;

    @FXML
    private HBox playerCardImageBox, dealerCardImageBox;
    @FXML
    AnchorPane  mainPane, topPane, bottomPane;

    @FXML
    Polygon increaseTipButton, decreaseTipButton;

    public void initialize() {
        initializeCardsUI();
        currencyLabel.setText(blackJackGame.getCurrency());
        //splitButton.setVisible(blackJackGame.splitabilibity());
        splitButton.setVisible(true);
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

        if (blackJackGame.getPlayerHandValue() > 21) {
            resultLabel.setText("You Lose!");

            if (blackJackGame.roundIsOver()) {
                resultLabel.setText("You Lose!");
                stayButton.setDisable(true);
                hitButton.setDisable(true);
                splitButton.setVisible(false);

                if (blackJackGame.playerHasAnyStays()) {
                    revealDealerCards();
                }
            }
        }

    }

    @FXML
    protected void onStay() {
        if (blackJackGame.playerStays()) {
            blackJackGame.nextSplitHand();
            initializeCardsUI();
            currentHandLabel.setText(Integer.toString(blackJackGame.getCurrentHandIndex() + 1));
        } else {
            // Player pressed stay.
            initializeCardsUI();
            hitButton.setDisable(true);
            stayButton.setDisable(true);
            splitButton.setDisable(true);
            revealDealerCards();
        }
    }

    @FXML
    protected void onSplit() {
        System.out.println("SPLIT");
        double bet = blackJackGame.split();
        currencyLabel.setText(Double.toString(bet));
        splitButton.setVisible(false);
        initializeCardsUI();
        previousHandButton.setVisible(true);
        nextHandButton.setVisible(true);
        currentHandLabel.setVisible(true);
        currentHandLabel.setText("1");
        currentHandLabel.setTextAlignment(TextAlignment.CENTER);
    }

    protected void revealDealerCards() {
        revealedCards = true;
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
                        resultLabel.setText(blackJackGame.getResult());
                        restartButton.setVisible(true);
                        currencyLabel.setText(blackJackGame.getCurrency());
                    })
            );

            timeline.setCycleCount(1);
            timeline.play();
        }
    }

    protected void initializeCardsUI() {
        playerCardImageBox.getChildren().clear();
        restartButton.setVisible(false);
        List<Card> cardList = blackJackGame.getPlayerCards();
        for (Card card : cardList) {
            loadPNG(playerCardImageBox, card);
        }
        if (!revealedCards) {
            dealerCardImageBox.getChildren().clear();
            cardList = blackJackGame.getDealerCards();
            Card card = cardList.get(0);
            loadPNG(dealerCardImageBox, card);
            card = new Card("Blank", "Card");
            loadPNG(dealerCardImageBox, card);
            dealerValueLabel.setText(Integer.toString(blackJackGame.getDealerUpCardValue()));
        }

        playerValueLabel.setText(Integer.toString(blackJackGame.getPlayerHandValue()));
    }


    @FXML
    protected void onRestart() {
        revealedCards = false;
        blackJackGame.initRound();
        initializeCardsUI();
        hitButton.setDisable(false);
        stayButton.setDisable(false);
        hintLabel.setText("");
        resultLabel.setText("");
        playerCardImageBox.setLayoutX(216);
        dealerCardImageBox.setLayoutX(216);
        restartButton.setVisible(false);
        currencyLabel.setText(blackJackGame.getCurrency());
        hitButton.setDisable(false);
        stayButton.setDisable(false);


        splitButton.setVisible(blackJackGame.splitabilibity());

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

    @FXML
    private void moveToPreviousHand() {
        if (blackJackGame.playerHasPreviousHand()) {
            currentHandLabel.setText(Integer.toString(Integer.parseInt(currentHandLabel.getText()) - 1));
            blackJackGame.moveToPreviousHand();
            loadHand();
        }

    }

    @FXML
    private void moveToNextHand() {
        if (blackJackGame.playerHasNextHand()) {
            currentHandLabel.setText(Integer.toString(Integer.parseInt(currentHandLabel.getText()) + 1));
            blackJackGame.moveToForwardHand();
            loadHand();
        }
    }

    private void loadHand() {
        initializeCardsUI();
        resultLabel.setText(blackJackGame.getResult());
        unplayedHandLabel.setVisible(false);

        if (blackJackGame.currentHandIsOver()) {
            stayButton.setDisable(true);
            hitButton.setDisable(true);
        } else {
            stayButton.setDisable(false);
            hitButton.setDisable(false);
        }
    }
}
