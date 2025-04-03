module org.example.blackjackgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens blackjack to javafx.fxml;
    exports blackjack;
    exports blackjack.FrontEnd;
    opens blackjack.FrontEnd to javafx.fxml;
    exports blackjack.GameComponents;
    opens blackjack.GameComponents to javafx.fxml;
    exports blackjack.features;
    opens blackjack.features to javafx.fxml;
}