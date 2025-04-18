package blackjack.FrontEnd;

import blackjack.BlackJackApplication;
import blackjack.GameComponents.BlackJackGame;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    private Stage stage;
    private BlackJackApplication application;

    public SceneSwitcher(Stage stage, BlackJackApplication application) {
        this.stage = stage;
        this.application = application;
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
        loader.setController(new TutorialController(this));


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
        loader.setController(new MainController(this, application));

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

    public void switchToMain() {
        String path = "/blackjack/mainPage.fxml";
        MainController mainControl = new MainController( this, application);

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = stage.getWidth();
            double height = stage.getHeight();
            mainControl.setDimensions(width, height);
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            double width = stage.getWidth();
            double height = stage.getHeight();
            mainControl.setDimensions(width, height);
        });

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));

        fxmlLoader.setController(mainControl);
        try {
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToEndGame(String score) {
        String path = "/blackjack/EndGame.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        loader.setController(new EndGameController(application, this, score));

        try {
            AnchorPane root = loader.load();
            root.setPrefHeight(stage.getHeight());
            root.setPrefWidth(stage.getWidth());
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
