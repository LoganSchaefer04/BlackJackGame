package blackjack;

import blackjack.FrontEnd.MainController;
import blackjack.FrontEnd.SceneSwitcher;
import blackjack.features.HighScore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BlackJackApplication extends Application {
    HighScore highScore;
    @Override
    public void start(Stage stage) throws IOException {
        highScore = new HighScore();

        SceneSwitcher sceneSwitcher = new SceneSwitcher(stage, this);
        MainController mainControl = new MainController(sceneSwitcher, this);

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

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainPage.fxml"));

        fxmlLoader.setController(mainControl);
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();

    }

    public String getHighScore(int index) {
        return highScore.getScore(index);
    }

    public void insertScore(String name) {
        highScore.insertScore(name);
    }

    public String getRecentScore() {
        return highScore.getRecentScore();
    }

    public void setRecentScore(double score) {
        highScore.setRecentScore(score);
    }

    public static void main(String[] args) {
        launch();
    }
}