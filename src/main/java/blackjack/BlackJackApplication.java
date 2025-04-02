package blackjack;

import blackjack.controllers.BJController;
import blackjack.controllers.BlackJackGame;
import blackjack.controllers.mainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class BlackJackApplication extends Application {
    private MusicPlayer musicPlayer;

    @Override
    public void start(Stage stage) throws IOException {
        // Initialize the music player with the music file path
        initializeMusic();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
        SceneSwitcher sceneSwitcher = new SceneSwitcher(stage);

        mainController mainControl = new mainController(sceneSwitcher);

        fxmlLoader.setController(mainControl);
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setMaximized(true);

        mainControl.setSize();

        stage.setOnCloseRequest(event -> {
            // Clean up resources when closing the application
            if (musicPlayer != null) {
                musicPlayer.dispose();
            }
        });
        stage.show();

        // Start playing music once the UI is fully loaded
        if (musicPlayer != null) {
            musicPlayer.play();
        }
    }

    /**
     * Initialize the background music for the game
     */
    private void initializeMusic() {
        try {
            // ONLY USE .WAV FILES
            String musicPath = "src/main/resources/music/background_music.wav";
            musicPlayer = new MusicPlayer(musicPath);
            musicPlayer.setVolume(0.5f); // Set to 50% volume by default
        } catch (Exception e) {
            System.err.println("Failed to initialize music: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        if (musicPlayer != null) {
            musicPlayer.dispose();
        }
        Platform.exit();
    }
}