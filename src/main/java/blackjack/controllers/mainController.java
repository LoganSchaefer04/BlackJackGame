package blackjack.controllers;

import blackjack.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class mainController {

    private SceneSwitcher sceneSwitcher;
    @FXML
    private Button playBlackJackButton, learnToPlayButton, settingsButton;

    @FXML
    private AnchorPane backgroundAnchorPane;

    @FXML
    private ImageView backgroundImage;

    public mainController(SceneSwitcher s) {
        sceneSwitcher = s;
    }

    public void setSize() {
        backgroundImage.setFitHeight(backgroundAnchorPane.getHeight());
        backgroundImage.setFitWidth(backgroundAnchorPane.getWidth());
    }

    @FXML
    private void playBlackjack() {
        BlackJackGame game = new BlackJackGame();
        sceneSwitcher.switchToGame(game);
    }

}
