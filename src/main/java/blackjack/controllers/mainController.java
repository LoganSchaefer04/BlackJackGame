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
        backgroundImage = new ImageView();
        backgroundAnchorPane = new AnchorPane();
        playBlackJackButton = new Button();
        learnToPlayButton = new Button();
        settingsButton = new Button();

        playBlackJackButton.setMinSize(350, 75);
        learnToPlayButton.setMinSize(350, 75);
        settingsButton.setMinSize(350, 75);
    }

    public void setDimensions(double width, double height) {
        backgroundImage.setFitWidth(width-50);
        backgroundImage.setFitHeight(height-50);
        backgroundAnchorPane.setPrefWidth(width);
        backgroundAnchorPane.setPrefHeight(height);

        playBlackJackButton.setPrefWidth(width*.05);
        playBlackJackButton.setPrefHeight(height*.05);

        learnToPlayButton.setPrefWidth(width*.05);
        learnToPlayButton.setPrefHeight(height*.05);

        settingsButton.setPrefWidth(width*.05);
        settingsButton.setPrefHeight(height*.05);
    }


    @FXML
    private void playBlackjack() {
        BlackJackGame game = new BlackJackGame();
        sceneSwitcher.switchToGame(game);
    }

}
