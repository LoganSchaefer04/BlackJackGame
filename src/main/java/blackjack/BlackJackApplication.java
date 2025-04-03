package blackjack;

import blackjack.FrontEnd.MainController;
import blackjack.FrontEnd.SceneSwitcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BlackJackApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneSwitcher sceneSwitcher = new SceneSwitcher(stage);
        MainController mainControl = new MainController(sceneSwitcher);

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

    public static void main(String[] args) {
        launch();
    }
}