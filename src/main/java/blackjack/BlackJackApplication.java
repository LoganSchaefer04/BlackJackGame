package blackjack;

import blackjack.controllers.BJController;
import blackjack.controllers.BlackJackGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class BlackJackApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Blackjack Game.fxml"));
        BlackJackGame blackJackGame = new BlackJackGame();
        fxmlLoader.setController(new BJController(blackJackGame));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}