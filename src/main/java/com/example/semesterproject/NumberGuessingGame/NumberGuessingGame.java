package com.example.semesterproject.NumberGuessingGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Random;

public class NumberGuessingGame extends Application {
    private int targetNumber;
    private int remainingGuesses;
    private int attempts;
    private String username; // Store username

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        showLoginWindow(primaryStage);
    }

    private void showLoginWindow(Stage stage) {
        VBox loginLayout = new VBox(20);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.getStyleClass().add("login-layout");

        Label welcomeLabel = new Label("Welcome to the Number Guessing Game!");
        welcomeLabel.getStyleClass().add("welcome-label");

        TextField loginField = new TextField();
        loginField.setPromptText("Enter your username");
        loginField.getStyleClass().add("input-field");

        Button loginButton = new Button("Username"); // Changed button text
        loginButton.getStyleClass().add("game-button");
        Label warningLabel = new Label(""); // Warning label for empty username
        warningLabel.getStyleClass().add("warning-label");

        loginButton.setOnAction(e -> {
            String loginInput = loginField.getText().trim();
            if (loginInput.isEmpty()) {
                warningLabel.setText("Username cannot be empty. Please enter your username!");
            } else {
                warningLabel.setText(""); // Clear warning if input is valid
                username = loginInput; // Save username
                targetNumber = new Random().nextInt(100) + 1; // Generate target number
                showMainGameWindow(stage, 10); // Start the game with 10 guesses
            }
        });

        loginLayout.getChildren().addAll(welcomeLabel, loginField, loginButton, warningLabel);

        Scene scene = new Scene(loginLayout, 600, 400);
        applyStylesheet(scene);

        stage.setTitle("Number Guessing Game");
        stage.setScene(scene);
        stage.show();
    }

    private void showMainGameWindow(Stage stage, int guesses) {
        remainingGuesses = guesses;
        attempts = 0;

        VBox gameLayout = new VBox(15);
        gameLayout.setAlignment(Pos.CENTER);
        gameLayout.getStyleClass().add("game-layout");

        Label instructionLabel = new Label("Guess the number between 1 and 100!");
        instructionLabel.getStyleClass().add("instruction-label");

        Label usernameLabel = new Label("Username: " + username); // Display username
        usernameLabel.getStyleClass().add("instruction-label");

        Label guessesLabel = new Label("You have " + remainingGuesses + " guesses!");
        guessesLabel.getStyleClass().add("guesses-label");

        TextField guessField = new TextField();
        guessField.setPromptText("Enter your guess (1-100)");
        guessField.getStyleClass().add("input-field");

        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("game-button");

        Label feedbackLabel = new Label("");
        feedbackLabel.getStyleClass().add("feedback-label");

        submitButton.setOnAction(e -> {
            String input = guessField.getText();
            try {
                int guess = Integer.parseInt(input);
                attempts++;
                remainingGuesses--;

                if (guess == targetNumber) {
                    showCongratulationsWindow(stage);
                } else if (remainingGuesses == 0) {
                    if (guesses == 10) {
                        showBonusOfferWindow(stage);
                    } else {
                        showGameOverWindow(stage);
                    }
                } else {
                    feedbackLabel.setText(guess < targetNumber ? "Too low! Try again." : "Too high! Try again.");
                    guessesLabel.setText("You have " + remainingGuesses + " guesses left!");
                }
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Please enter a valid number!");
            }
            guessField.clear();
        });

        gameLayout.getChildren().addAll(instructionLabel, usernameLabel, guessesLabel, guessField, submitButton, feedbackLabel);

        Scene scene = new Scene(gameLayout, 600, 400);
        applyStylesheet(scene);

        stage.setTitle("Number Guessing Game");
        stage.setScene(scene);
    }

    private void showBonusOfferWindow(Stage stage) {
        VBox bonusLayout = new VBox(20);
        bonusLayout.setAlignment(Pos.CENTER);
        bonusLayout.getStyleClass().add("bonus-layout");

        Label bonusLabel = new Label("You are out of guesses! Would you like 3 bonus guesses?");
        bonusLabel.getStyleClass().add("bonus-label");

        Button yesButton = new Button("Yes");
        yesButton.getStyleClass().add("game-button");
        yesButton.setOnAction(e -> showMainGameWindow(stage, 3));

        Button noButton = new Button("No");
        noButton.getStyleClass().add("game-button");
        noButton.setOnAction(e -> showGameOverWindow(stage));

        bonusLayout.getChildren().addAll(bonusLabel, yesButton, noButton);

        Scene scene = new Scene(bonusLayout, 600, 400);
        applyStylesheet(scene);

        stage.setTitle("Bonus Offer");
        stage.setScene(scene);
    }

    private void showGameOverWindow(Stage stage) {
        VBox gameOverLayout = new VBox(20);
        gameOverLayout.setAlignment(Pos.CENTER);
        gameOverLayout.getStyleClass().add("game-over-layout");

        Label gameOverLabel = new Label("Game Over! The number was " + targetNumber + ".");
        gameOverLabel.getStyleClass().add("game-over-label");

        Button playAgainButton = new Button("Play Again");
        playAgainButton.getStyleClass().add("game-button");
        playAgainButton.setOnAction(e -> showLoginWindow(stage));

        Button exitButton = new Button("Exit");
        exitButton.getStyleClass().add("game-button");
        exitButton.setOnAction(e -> Platform.exit());

        gameOverLayout.getChildren().addAll(gameOverLabel, playAgainButton, exitButton);

        Scene scene = new Scene(gameOverLayout, 600, 400);
        applyStylesheet(scene);

        stage.setTitle("Game Over");
        stage.setScene(scene);
    }

    private void showCongratulationsWindow(Stage stage) {
        VBox congratsLayout = new VBox(20);
        congratsLayout.setAlignment(Pos.CENTER);
        congratsLayout.getStyleClass().add("congrats-layout");

        Label congratsLabel = new Label("Congratulations, " + username + "! You guessed the number in " + attempts + " attempts!");
        congratsLabel.getStyleClass().add("congrats-label");

        Button playAgainButton = new Button("Play Again");
        playAgainButton.getStyleClass().add("game-button");
        playAgainButton.setOnAction(e -> showLoginWindow(stage));

        Button exitButton = new Button("Exit");
        exitButton.getStyleClass().add("game-button");
        exitButton.setOnAction(e -> Platform.exit());

        congratsLayout.getChildren().addAll(congratsLabel, playAgainButton, exitButton);

        Scene scene = new Scene(congratsLayout, 600, 400);
        applyStylesheet(scene);

        stage.setTitle("Congratulations");
        stage.setScene(scene);
    }

    private void applyStylesheet(Scene scene) {
        URL stylesheet = getClass().getResource("/com/example/semesterproject/Style.css");
        if (stylesheet != null) {
            scene.getStylesheets().add(stylesheet.toExternalForm());
        } else {
            System.out.println("Error: Style.css not found!");
        }
    }
}
