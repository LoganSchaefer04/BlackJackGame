module org.example.blackjackgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens blackjack to javafx.fxml;
    exports blackjack;
}