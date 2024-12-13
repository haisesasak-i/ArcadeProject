module com.example.semesterproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires  javafx.media;

    opens com.example.semesterproject to javafx.fxml;
    exports com.example.semesterproject;
    exports com.example.semesterproject.TicTakToe;
    exports com.example.semesterproject.BattleShip to javafx.graphics;
    exports com.example.semesterproject.NumberGuessingGame;
    opens com.example.semesterproject.NumberGuessingGame to javafx.fxml;
    exports com.example.semesterproject.RockPaperScissors;
    opens com.example.semesterproject.RockPaperScissors to javafx.fxml;
    exports  com.example.semesterproject.GameSelectionMenu;


}