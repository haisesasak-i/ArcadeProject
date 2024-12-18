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

        Text title = new Text("🎮 Arcade Games 🎮");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setFill(Color.DARKORANGE);


        TicTacToeUI tiktaktoe = new TicTacToeUI();
        NumberGuessingGame numberGuessingGame = new NumberGuessingGame();
        BattleShipUI battleShipUI = new BattleShipUI();
        RockPaperScissors rockPaperScissors = new RockPaperScissors();


        Button ticTacToeButton = createGameButton("Tic Tac Toe");
        ticTacToeButton.setOnAction(event -> tiktaktoe.start(primaryStage));

        Button battleshipButton = createGameButton("Battleship");
        battleshipButton.setOnAction(event -> battleShipUI.start(primaryStage));

        Button numberGuessingButton = createGameButton("Number Guessing");
        numberGuessingButton.setOnAction(event -> numberGuessingGame.start(primaryStage));

        Button rockPaperScissorsButton = createGameButton("Rock Paper Scissors");
        rockPaperScissorsButton.setOnAction(event -> rockPaperScissors.start(primaryStage));

        Button randomGameButton = createGameButton("Random");
        randomGameButton.setOnAction(event -> {
            Random random = new Random();
            int gameNumber = random.nextInt(4);
            if (gameNumber == 0) {
                tiktaktoe.start(primaryStage);
            } else if (gameNumber == 1) {
                battleShipUI.start(primaryStage);
            } else if (gameNumber == 2) {
                numberGuessingGame.start(primaryStage);
            } else {
                rockPaperScissors.start(primaryStage);
            }
        });

        Button exitButton = createGameButton("Exit");
        exitButton.setOnAction(event -> System.exit(0));


        Button instructionsButton = createGameButton("Instructions");
        instructionsButton.setOnAction(event -> showInstructions(primaryStage));


        VBox vbox = new VBox(20, title, ticTacToeButton, battleshipButton, numberGuessingButton, rockPaperScissorsButton, randomGameButton, instructionsButton, exitButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom, #1e3c72, #2a5298);");


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


    private void showInstructions(Stage primaryStage) {
        Text instructionTitle = new Text("Game Instructions");
        instructionTitle.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        instructionTitle.setFill(Color.DARKORANGE);


        Text instructionsText = new Text("Welcome to the Arcade!\n\n"
                + "Choose a game and enjoy!\n\n"
                + "1. Tic Tac Toe: A classic 3x3 grid game.\n"
                + "2. Battleship: Sink your opponent's ships.\n"
                + "3. Number Guessing: Guess the number between 1 and 100.\n"
                + "4. Rock Paper Scissors: A simple hand game.\n\n"
                + "Click 'Continue' to go back to the game selection menu.");

        instructionsText.setFont(Font.font("Arial", 18));
        instructionsText.setFill(Color.WHITE);

        Button continueButton = createGameButton("Continue");
        continueButton.setOnAction(event -> {
            start(primaryStage);
        });


        VBox instructionLayout = new VBox(20, instructionTitle, instructionsText, continueButton);
        instructionLayout.setAlignment(Pos.CENTER);
        instructionLayout.setPadding(new Insets(20));
        instructionLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #1e3c72, #2a5298);");


        Scene instructionsScene = new Scene(instructionLayout, 800, 800);
        primaryStage.setScene(instructionsScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
