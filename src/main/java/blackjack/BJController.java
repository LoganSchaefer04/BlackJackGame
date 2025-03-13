package blackjack;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.File;
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

    @FXML
    private HBox playerCardImageBox;
    @FXML
    private HBox dealerCardImageBox;

    private BlackJackGame blackJackGame;

    public void initialize() {
        initializeCardsUI();
    }

    public BJController(BlackJackGame blackJackGame) {
        this.blackJackGame = blackJackGame;
        playerCardImageBox = new HBox();
        dealerCardImageBox = new HBox();
        playerCardImageBox.setSpacing(200);

    }

    @FXML
    protected void onHit() {
        // Player pressed hit.
        Card card = blackJackGame.hitPlayer();
        loadPNG(playerCardImageBox, card);

    }

    @FXML
    protected void onStay() {
        // Player pressed stay.
        blackJackGame.playerStays();
        List<Card> dealerCards = blackJackGame.getDealerHand();
        dealerCardImageBox.getChildren().remove(1);
        for (int i = 1; i < dealerCards.size(); i++) {
            final int index = i;

            // Create a Timeline to run the image loading with a delay
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1 * i), event -> {
                        loadPNG(dealerCardImageBox, dealerCards.get(index));
                    })
            );
            timeline.setCycleCount(1); // Run once
            timeline.play();
        }
    }

    private void initializeCardsUI() {
        playerCardImageBox.getChildren().clear();
        dealerCardImageBox.getChildren().clear();
        List<Card> cardList = blackJackGame.getPlayerHand();
        for (Card card : cardList) {
            loadPNG(playerCardImageBox, card);
        }

        cardList = blackJackGame.getDealerHand();
        Card card = cardList.get(0);
        loadPNG(dealerCardImageBox, card);
        card = new Card("Blank", "Card");
        loadPNG(dealerCardImageBox, card);

    }


    @FXML
    protected void onRestart() {
        blackJackGame.initRound();
        initializeCardsUI();
        hitButton.setDisable(false);
        stayButton.setDisable(false);
        playerCard1.setImage(null);
        playerCard2.setImage(null);
        dealerCard1.setImage(null);
        dealerCard2.setImage(null);
        playerCardImageBox.setLayoutX(216);

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
}