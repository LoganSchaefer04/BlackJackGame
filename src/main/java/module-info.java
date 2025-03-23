module org.example.blackjackgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens blackjack to javafx.fxml;
    exports blackjack;
    exports blackjack.controllers;
    opens blackjack.controllers to javafx.fxml;
}