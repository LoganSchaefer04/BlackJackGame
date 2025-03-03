module org.example.blackjackgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.blackjackgame to javafx.fxml;
    exports org.example.blackjackgame;
}