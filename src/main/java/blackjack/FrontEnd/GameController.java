package blackjack.FrontEnd;

import blackjack.GameComponents.BlackJackGame;
import blackjack.GameComponents.Card;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

/*
* Controller for PleaseBe21 UI
* Handles all user input, game interactions, and such
 */
public class GameController {
    // -----main game components-----
    private BlackJackGame blackJackGame;    // game logic
    private boolean revealedCards;          // checks if dealer has shown all cards
    private String currentCardBack = "";    // current back of card
    private boolean autoBetEnabled = false; // default autobet is at false

    // -----UI game components-----
    // game action buttons
    @FXML private Button hitButton, stayButton, restartButton, hintButton, splitButton, tipDealerButton;
    // split integration
    @FXML private Button previousHandButton, nextHandButton;
    @FXML private Label currentHandLabel, unplayedHandLabel;

    @FXML
    private Label countLabel, roundsLeftLabel;
    // game status labels
    @FXML private Label resultLabel, hintLabel, playerValueLabel, dealerValueLabel, currencyLabel, betLabel, tipAmountLabel;

    // card display boxes
    @FXML private HBox playerCardImageBox, dealerCardImageBox;

    // layout containers
    @FXML AnchorPane mainPane, topPane, bottomPane;

    // tip controls
    @FXML Polygon increaseTipButton, decreaseTipButton;

    // Auto-bet toggle
    @FXML private CheckBox autoBetCheckBox;

    /**
     * Constructor including a game reference
     */
    public GameController(BlackJackGame blackJackGame) {
        this.blackJackGame = blackJackGame;
    }

    /**
     * Empty constructor for TutorialController
     */
    /*
    init game UI
     */
    public void initialize() {
        //start initial card display
        initializeCardsUI();
        //show cards, currency, bet amount, and split button if criteria met
        currencyLabel.setText(blackJackGame.getCurrency());
        betLabel.setText(Double.toString(blackJackGame.getBank().getBet()));
        hitButton.setDisable(blackJackGame.getPlayerHandValue() >= 21);
        splitButton.setVisible(blackJackGame.splitabilibity());
        roundsLeftLabel.setText(blackJackGame.getRoundsLeft());
    }

    //GAME FLOW
    @FXML
    protected void onHit() {
        splitButton.setVisible(false);
        if (blackJackGame.hitPlayer()) {
            //  hit button off if player has 21 or more
            if (blackJackGame.getPlayerHandValue() >= 21) {
                hitButton.setDisable(true);
            }

            stayButton.setDisable(blackJackGame.isRoundOver());
        }

        loadPNG(playerCardImageBox, blackJackGame.recentCardRank() + blackJackGame.recentCardSuit());
        playerValueLabel.setText(Integer.toString(blackJackGame.getPlayerHandValue()));

        if (blackJackGame.dealerHasPlayed()) {
            revealDealerCards();
        } else {
            resultLabel.setText(blackJackGame.getResult());

            if (blackJackGame.isRoundOver()) {
                handleRoundEnd();
            }
        }
    }

    @FXML
    protected  void onStay(){
        if(blackJackGame.playerStays()){
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
        dealerCardImageBox.getChildren().remove(dealerCardImageBox.getChildren().size() - 1);
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
                        handleRoundEnd();
                    })
            );

            timeline.setCycleCount(1);
            timeline.play();
        }
    }
    private void handleRoundEnd() {
        resultLabel.setText(blackJackGame.getResult());
        currencyLabel.setText(blackJackGame.getCurrency());

        if (autoBetEnabled && Double.parseDouble(blackJackGame.getCurrency()) >= blackJackGame.getBank().getBet()) {
            Timeline autoPlayTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(2.5), event -> {
                        revealedCards = false;
                        blackJackGame.initRound(true);
                        initializeCardsUI();
                        hitButton.setDisable(false);
                        stayButton.setDisable(false);
                        hintLabel.setText("");
                        resultLabel.setText("");
                        playerCardImageBox.setLayoutX(216);
                        dealerCardImageBox.setLayoutX(216);
                        restartButton.setVisible(false);
                        currencyLabel.setText(blackJackGame.getCurrency());
                        previousHandButton.setVisible(false);
                        nextHandButton.setVisible(false);
                        currentHandLabel.setVisible(false);
                        splitButton.setVisible(blackJackGame.splitabilibity());
                        splitButton.setDisable(!blackJackGame.splitabilibity());
                    })
            );
            autoPlayTimeline.setCycleCount(1);
            autoPlayTimeline.play();
        } else {
            restartButton.setVisible(true);
        }
    }


    public void setCardBack(String cardBackDesign) {
        this.currentCardBack = cardBackDesign;// refresh the UI to show the new card back
        if (!revealedCards && dealerCardImageBox.getChildren().size() > 1) {
            dealerCardImageBox.getChildren().remove(dealerCardImageBox.getChildren().size() - 1);
            loadPNG(dealerCardImageBox, currentCardBack);
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
            loadPNG(dealerCardImageBox, currentCardBack);
            dealerValueLabel.setText(Integer.toString(blackJackGame.getDealerUpCardValue()));
        }

        playerValueLabel.setText(Integer.toString(blackJackGame.getPlayerHandValue()));
    }


    //4.14.2025 new addition checks if autobet should continue after a round is over
    @FXML
    protected void onRestart() {
        revealedCards = false;
        blackJackGame.initRound(false);
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
        splitButton.setDisable(!blackJackGame.splitabilibity());
        if (blackJackGame.getRoundsLeft().equals("-1")) {
            mainPane.setDisable(true);
        } else {
            roundsLeftLabel.setText(blackJackGame.getRoundsLeft());
        }

    }

    @FXML
    protected void loadPNG(HBox container, String cardName) {
        String path;

        if (cardName.equals(currentCardBack) || cardName.endsWith("Card")) {
            path = "src/main/resources/CardImages/backs/" + cardName + ".png";
        } else {
            path = "src/main/resources/CardImages/" + cardName + ".png";
        }
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

    @FXML void onAutoBetToggle(){
        autoBetEnabled = autoBetCheckBox.isSelected();
    }

    @FXML
    protected void increaseBet() {
        double betAmount = Double.parseDouble(betLabel.getText());
        betAmount += 5;
        betLabel.setText(Double.toString(betAmount));
        blackJackGame.getBank().setBet(betAmount);
    }

    @FXML
    protected void decreaseBet() {
        double betAmount = Double.parseDouble(betLabel.getText());
        if (betAmount > 5) { //stops negative or zero bets
            betAmount -= 5;
            betLabel.setText(Double.toString(betAmount));
            blackJackGame.getBank().setBet(betAmount);
        }
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

        restartButton.setVisible(blackJackGame.isRoundOver());
    }
}
