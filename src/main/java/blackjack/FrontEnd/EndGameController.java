package blackjack.FrontEnd;

import blackjack.BlackJackApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class EndGameController {

    private BlackJackApplication application;
    private SceneSwitcher sceneSwitcher;

    @FXML
    private TextField nameInput;

    @FXML
    private AnchorPane backgroundAnchorPane;

    @FXML
    private Label scoreLabel;

    public EndGameController(BlackJackApplication application, SceneSwitcher sceneSwitcher, String score) {
        this.application = application;
        this.sceneSwitcher = sceneSwitcher;
        scoreLabel = new Label();
        scoreLabel.setText(score);
    }

    public void initialize() {
        scoreLabel.setText(application.getRecentScore());
    }

    @FXML
    private void goHome() {
        application.insertScore(nameInput.getText());
        sceneSwitcher.switchToMain(backgroundAnchorPane.getWidth(), backgroundAnchorPane.getHeight());
    }
}
