package blackjack.FrontEnd;

import blackjack.GameComponents.BlackJackGame;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    private Stage stage;

    public SceneSwitcher(Stage stage) {
        this.stage = stage;
    }

    public GameController switchToGame(BlackJackGame blackJackGame, double width, double height) {

        String path = "/blackjack/BlackJackTwo.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        GameController gameController = new GameController(blackJackGame);
        loader.setController(gameController);


        try {
            AnchorPane root = loader.load();
            root.setPrefHeight(height);
            root.setPrefWidth(width);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            return gameController;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void switchToTutorial(double width, double height) {
        String path = "/blackjack/GameTutorial.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        loader.setController(new TutorialController(this, new BlackJackGame()));


        try {
            SplitPane root = loader.load();
            root.setPrefHeight(height);
            root.setPrefWidth(width);
            Scene scene = new Scene(root);
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void switchToMain(double width, double height) {
        String path = "/blackjack/mainPage.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        loader.setController(new MainController(this));

        try {
            AnchorPane root = loader.load();
            root.setPrefHeight(height);
            root.setPrefWidth(width);
            Scene scene = new Scene(root);
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
