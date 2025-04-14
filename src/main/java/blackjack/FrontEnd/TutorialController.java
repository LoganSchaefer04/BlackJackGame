package blackjack.FrontEnd;

import blackjack.GameComponents.Ace;
import blackjack.GameComponents.BlackJackGame;
import blackjack.GameComponents.Card;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class TutorialController extends GameController {
    private SceneSwitcher sceneSwitcher;
    private Queue<Runnable> actionQueue = new LinkedList<>();
    private String currentCardBack = "RedCard";

    private BlackJackGame blackJackGame;

    @FXML
    private Button hitButton, stayButton, restartButton, hintButton, splitButton, tipDealerButton, returnHomeButton;

    @FXML
    private Label resultLabel, hintLabel, playerValueLabel, dealerValueLabel, currencyLabel, betLabel, countLabel,
            tutorialText;

    @FXML
    private Label stepOne, stepTwo, stepThree, stepFour, stepFive, stepSix, stepSeven, stepEight, stepNine, finalStep;

    @FXML
    private HBox playerCardImageBox, dealerCardImageBox;
    @FXML
    private AnchorPane mainPane, topPane, bottomPane;

    @FXML
    private SplitPane mainSplitPane;

    public void initialize() {
        loadPNG(playerCardImageBox, "Five" + "Heart");
        loadPNG(playerCardImageBox, "Six" + "Club");
        loadPNG(dealerCardImageBox, "Ten" + "Diamond");
        loadPNG(dealerCardImageBox, currentCardBack);
        dealerValueLabel.setText("10");
        playerValueLabel.setText("11");
        hitButton.setDisable(true);
        stayButton.setDisable(true);
        hintButton.setDisable(true);
        tipDealerButton.setDisable(true);
    }

    public TutorialController(SceneSwitcher sceneSwitcher, BlackJackGame blackJackGame) {
        super(blackJackGame);
        this.sceneSwitcher = sceneSwitcher;
        queueSteps();
    }

    public void setCardBack(String cardBackDesign) {
        this.currentCardBack = cardBackDesign;
    }

    private void queueSteps() {
        actionQueue.add(() -> stepTwo());
        actionQueue.add(() -> stepThree());
        actionQueue.add(() -> stepFour());
        actionQueue.add(() -> stepFive());
        actionQueue.add(() -> stepNine());
    }

    @FXML
    private void nextStep() {
        if (!actionQueue.isEmpty()) {
            actionQueue.poll().run();
        }
    }

    private void stepTwo() {
        stepOne.setVisible(false);
        stepTwo.setVisible(true);
    }

    private void stepThree() {
        stepTwo.setVisible(false);
        stepThree.setVisible(true);
    }

    private void stepFour() {
        stepThree.setVisible(false);
        stepFour.setVisible(true);
    }

    private void stepFive() {
        stepFour.setVisible(false);
        stepFive.setVisible(true);
        hitButton.setDisable(false);
        hitButton.setOnAction(event -> stepSix());

    }

    private void stepSix() {
        loadPNG(playerCardImageBox, "Ten" + "Heart");
        playerValueLabel.setText("21");

        stepFive.setVisible(false);
        stepSix.setVisible(true);
        hitButton.setDisable(true);
        stayButton.setDisable(false);

        stayButton.setOnAction(event -> stepSeven());

    }

    private void stepSeven() {
        stayButton.setDisable(true);
        stepSix.setVisible(false);
        dealerCardImageBox.getChildren().remove(1);

        Timeline timeline = new Timeline (
                new KeyFrame(Duration.seconds(1), event -> {
                    loadPNG(dealerCardImageBox, "Seven" + "Spade");
                    dealerValueLabel.setText("17");
                }),
                new KeyFrame(Duration.seconds(2), event -> {
                    resultLabel.setText("You Win!");
                    restartButton.setVisible(true);
                    stepSeven.setVisible(true);
                    stepEight.setVisible(true);
                })
        );
        timeline.setCycleCount(1);
        timeline.play();
        restartButton.setOnAction(event -> stepEight());
    }

    private void stepEight() {
        resultLabel.setText("");
        restartButton.setVisible(false);
        stepSeven.setVisible(false);
        stepEight.setVisible(false);
        dealerCardImageBox.getChildren().clear();
        playerCardImageBox.getChildren().clear();
        loadPNG(dealerCardImageBox, "Ace" + "Heart");
        loadPNG(dealerCardImageBox, currentCardBack);
        loadPNG(playerCardImageBox, "Ace" + "Heart");
        loadPNG(playerCardImageBox, "Ace" + "Club");
        dealerValueLabel.setText("11");
        playerValueLabel.setText("12");
        stepNine.setVisible(true);
        stepNine.setOnMouseClicked(event -> {
            stepNine.setText("When you have two cards of the same value, you can split the cards and play both " +
                    "hands. This requires your original bet to be matched for the second hand.");
            splitButton.setVisible(true);
            stepNine.setOnMouseClicked(null);
        });
        splitButton.setOnAction(event -> stepNine());

    }

    private void stepNine() {
        splitButton.setVisible(false);
        playerCardImageBox.getChildren().remove(1);
        loadPNG(playerCardImageBox, "Ten" + "Spade");
        playerValueLabel.setText("21");
        stepNine.setText("Wow! You got 21 again! Let's see what happens when we bust. Press HIT!");
        hitButton.setDisable(false);
        hitButton.setOnAction(event -> {
            loadPNG(playerCardImageBox,"Ten" + "Spade");
            playerValueLabel.setText("21");
            stepNine.setText("The value of the ace lowered to 1. Press HIT again!");
            stepTen();
        });

    }

    private void stepTen() {
        hitButton.setOnAction(event -> {
            loadPNG(playerCardImageBox, "Two" + "Club");
            playerValueLabel.setText("23");
            resultLabel.setText("You Lose!");
            hitButton.setVisible(false);
            stayButton.setDisable(false);
            stepNine.setText("Press STAY to see your second hand!");
            stayButton.setOnAction(stepEleven -> stepEleven());
        });
    }

    private void stepEleven() {
        resultLabel.setText("");
        stayButton.setDisable(true);
        hitButton.setVisible(true);
        hitButton.setDisable(true);
        playerCardImageBox.getChildren().clear();
        loadPNG(playerCardImageBox,"Ace" + "Club");
        loadPNG(playerCardImageBox,"Ten" + "Spade");
        playerValueLabel.setText("21");
        stepNine.setText("You may have noticed you are automatically dealt a second card when you split");
        stepNine.setOnMouseClicked(event -> {
            stepNine.setText("You got another Blackjack! Press STAY one more time to let the dealer play");
            stayButton.setDisable(false);
            stayButton.setOnAction(stepTwelve -> stepTwelve());
        });

    }

    private void stepTwelve() {
        stayButton.setVisible(false);
        stepNine.setVisible(false);
        dealerCardImageBox.getChildren().remove(1);
        Timeline timeline = new Timeline (
                new KeyFrame(Duration.seconds(1), event -> {
                    loadPNG(dealerCardImageBox, "Ten" + "Diamond");
                    dealerValueLabel.setText("21");
                }),
                new KeyFrame(Duration.seconds(2), event -> {
                    resultLabel.setText("Push!");
                    stepThirteen();
                })
        );
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void stepThirteen() {
        finalStep.setVisible(true);
        finalStep.setOnMouseClicked(event -> {
            finalStep.setText("You've got the hang of it! Return to main menu and try playing on your own!\n" +
                    "Press RETURN HOME");
            returnHomeButton.setVisible(true);
        });
    }

    @FXML
    private void returnHome() {
        sceneSwitcher.switchToMain();
    }
}