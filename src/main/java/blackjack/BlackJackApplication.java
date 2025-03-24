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
        Parent root = fxmlLoader.load();

        // ✅ Get the controller created by FXML and inject the game
        BJController controller = fxmlLoader.getController();
        controller.setGame(new BlackJackGame());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}