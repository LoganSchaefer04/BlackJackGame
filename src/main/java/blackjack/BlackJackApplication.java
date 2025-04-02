package blackjack;

import blackjack.FrontEnd.GameController;
import blackjack.FrontEnd.TutorialController;
import blackjack.GameComponents.BlackJackGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BlackJackApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameTutorial.fxml"));
        BlackJackGame blackJackGame = new BlackJackGame();
        fxmlLoader.setController(new TutorialController(blackJackGame));
        SplitPane root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}