package com.example.semesterproject.GameSelectionMenu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.semesterproject.TicTakToe.TicTacToeUI;
import com.example.semesterproject.NumberGuessingGame.NumberGuessingGame;
import com.example.semesterproject.RockPaperScissors.RockPaperScissors;
import com.example.semesterproject.BattleShip.BattleShipUI;

import java.util.Random;

public class GameSelectionMenu extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Arcade Title


        Text title = new Text("🎮 Arcade Games 🎮");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setFill(Color.DARKORANGE);

        //craeting objects of differnt games
        TicTacToeUI tiktaktoe = new TicTacToeUI();
        NumberGuessingGame numberGuessingGame = new NumberGuessingGame();
        BattleShipUI battleShipUI = new BattleShipUI();
        RockPaperScissors rockPaperScissors = new RockPaperScissors();

        // Buttons for games
        Button ticTacToeButton = createGameButton("Tic Tac Toe");
        ticTacToeButton.setOnAction(event -> {

                tiktaktoe.start(primaryStage);
        });

        Button battleshipButton = createGameButton("Battleship");
        battleshipButton.setOnAction(event -> {
                battleShipUI.start(primaryStage);
        });
        Button numberGuessingButton = createGameButton("Number Guessing");
        numberGuessingButton.setOnAction(event -> {
            numberGuessingGame.start(primaryStage);
        });
        Button rockPaperScissorsButton = createGameButton("Rock Paper Scissors");
        rockPaperScissorsButton.setOnAction(event -> {
            rockPaperScissors.start(primaryStage);
        });
        Button randomGameButton = createGameButton("Random");
        randomGameButton.setOnAction(event -> {
            Random random = new Random();
            int gameNumber =random.nextInt(4);
            if(gameNumber ==0){
                tiktaktoe.start(primaryStage);
            }
            else if(gameNumber == 1){
                battleShipUI.start(primaryStage);
            }
            else if(gameNumber ==2){
                numberGuessingGame.start(primaryStage);
            }
            else
                rockPaperScissors.start(primaryStage);
        });
        Button exitButton = createGameButton("Exit");
        exitButton.setOnAction(event -> {
            System.exit(0);
        });

        // Layout
        VBox vbox = new VBox(20, title, ticTacToeButton, battleshipButton, numberGuessingButton, rockPaperScissorsButton, randomGameButton,exitButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom, #1e3c72, #2a5298);");

        // Scene setup
        Scene scene = new Scene(vbox, 800, 800);
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.setResizable(false);

        // Stage setup
        primaryStage.setTitle("Arcade Game Selection");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createGameButton(String buttonText) {
        Button button = new Button(buttonText);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: #ff6347; -fx-border-color: #ffffff; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        button.setPrefWidth(200);


        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
