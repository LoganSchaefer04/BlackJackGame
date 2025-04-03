package blackjack.controllers;

import blackjack.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class mainController {

    private SceneSwitcher sceneSwitcher;
    @FXML
    private Button playBlackJackButton, learnToPlayButton, settingsButton, exitButtonSettings, exitHighScores;

    @FXML
    private Label settingsLabel, highScorePopupLabel;

    @FXML
    private CheckBox cardCountingSettingSelector;

    @FXML
    private AnchorPane backgroundAnchorPane, settingsPopup, highScorePopup;

    @FXML
    private ImageView backgroundImage;

    private boolean settingsVisible;
    private boolean highScoreVisible;

    public mainController(SceneSwitcher s) {
        settingsVisible = false;
        highScoreVisible = false;
        sceneSwitcher = s;

        backgroundImage = new ImageView();
        backgroundAnchorPane = new AnchorPane();

        //main screen buttons
        playBlackJackButton = new Button();
        learnToPlayButton = new Button();
        settingsButton = new Button();

        //settings popup buttons and labels
        exitButtonSettings = new Button();
        settingsLabel = new Label();
        settingsPopup = new AnchorPane();
        cardCountingSettingSelector = new CheckBox();

        //highScore popup buttons and labels
        highScorePopup = new AnchorPane();
        exitHighScores = new Button();
        highScorePopupLabel = new Label();

        //set the initial settings visibility
        exitButtonSettings.setVisible(settingsVisible);
        settingsLabel.setVisible(settingsVisible);
        settingsPopup.setVisible(settingsVisible);
        cardCountingSettingSelector.setVisible(settingsVisible);

        //set the initial high score popup visibility
        highScorePopup.setVisible(highScoreVisible);
        exitHighScores.setVisible(highScoreVisible);
        highScorePopupLabel.setVisible(highScoreVisible);

        //set minimum sizes of main screen buttons
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

        settingsPopup.setPrefWidth(width * .4);
        settingsPopup.setPrefHeight(height * 0.4);
    }


    @FXML
    private void playBlackjack() {
        BlackJackGame game = new BlackJackGame();
        sceneSwitcher.switchToGame(game);
    }

    @FXML
    private void toggleSettingPopup() {
        settingsVisible = !settingsVisible;

        //change settings pane to visible or not
        exitButtonSettings.setVisible(settingsVisible);
        settingsLabel.setVisible(settingsVisible);
        settingsPopup.setVisible(settingsVisible);
        cardCountingSettingSelector.setVisible(settingsVisible);
    }

    @FXML
    private void toggleHighScorePopup() {
        highScoreVisible = !highScoreVisible;
        highScorePopup.setVisible(highScoreVisible);
        exitHighScores.setVisible(highScoreVisible);
        highScorePopupLabel.setVisible(highScoreVisible);
    }

}
