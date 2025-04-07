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
    private Label currentHandLabel, unplayedHandLabel, countLabel;

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
         if (blackJackGame.hitPlayer()) {
             stayButton.setDisable(true);
             hitButton.setDisable(true);
             splitButton.setVisible(false);
         }

        loadPNG(playerCardImageBox, blackJackGame.recentCardRank() + blackJackGame.recentCardSuit());
        playerValueLabel.setText(Integer.toString(blackJackGame.getPlayerHandValue()));
        resultLabel.setText(blackJackGame.getResult());

        if (blackJackGame.dealerHasPlayed()) {
            revealDealerCards();
        }

        if (blackJackGame.isRoundOver()) {
            restartButton.setVisible(true);
        }

    }

    @FXML
    protected void onStay() {
        if (blackJackGame.playerStays()) {
            blackJackGame.nextSplitHand();
            loadHand();
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
        double bet = blackJackGame.split();
        currencyLabel.setText(Double.toString(bet));
        splitButton.setVisible(false);
        previousHandButton.setVisible(true);
        nextHandButton.setVisible(true);
        currentHandLabel.setVisible(true);
        currentHandLabel.setText("1");
        currentHandLabel.setTextAlignment(TextAlignment.CENTER);
        initializeCardsUI();
    }

    protected void revealDealerCards() {
        revealedCards = true;
        List<String> dealerCardNames = blackJackGame.getDealerCardNames();
        dealerCardImageBox.getChildren().removeLast();
        int dealerValue = blackJackGame.getDealerUpCardValue();

        for (int i = 1; i < dealerCardNames.size(); i++) {
            final int index = i;
            dealerValue += blackJackGame.getDealerCardValue(i);
            final String stringHandValue = Integer.toString(dealerValue);
            int j = i;
            // Create a Timeline to run the image loading with a delay
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(i), event -> {
                        loadPNG(dealerCardImageBox, dealerCardNames.get(j));
                        dealerValueLabel.setText(stringHandValue);
                    }),
                    new KeyFrame(Duration.seconds(dealerCardNames.size()), event -> {
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
            loadPNG(playerCardImageBox, card.getRank() + card.getSuit());
        }
        if (!revealedCards) {
            dealerCardImageBox.getChildren().clear();
            cardList = blackJackGame.getDealerCards();
            Card card = cardList.get(0);
            loadPNG(dealerCardImageBox, card.getRank() + card.getSuit());
            loadPNG(dealerCardImageBox, "BlankCard");
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
        previousHandButton.setVisible(false);
        nextHandButton.setVisible(false);
        currentHandLabel.setVisible(false);


        splitButton.setVisible(blackJackGame.splitabilibity());

    }

    @FXML
    protected void loadPNG(HBox container, String cardName) {
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
        countLabel.setText(blackJackGame.getCardCount());

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
    protected void increaseBet(){
        double betAmount = Double.parseDouble(betLabel.getText());
        betAmount+= 5;
        betLabel.setText(Double.toString(betAmount));
    }
    @FXML
    protected void decreaseBet(){
        double betAmount = Double.parseDouble(betLabel.getText());
        betAmount-= 5;
        betLabel.setText(Double.toString(betAmount));
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
