package blackjack;

import blackjack.controllers.BJController;
import blackjack.controllers.BlackJackGame;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    private Stage stage;

    public SceneSwitcher(Stage stage) {
        this.stage = stage;
    }

    public void switchToGame(BlackJackGame blackJackGame) {

        String path = "blackjack/Blackjack Game.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Blackjack Game.fxml"));
        loader.setController(new BJController(blackJackGame));


        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
