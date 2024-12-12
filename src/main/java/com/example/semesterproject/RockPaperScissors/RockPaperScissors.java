package com.example.semesterproject.RockPaperScissors;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Objects;
import java.util.Random;

public class RockPaperScissors extends Application {

    private int playerScore = 0;
    private int computerScore = 0;
    private int roundsToPlay = 0;
    private int roundsPlayed = 0;
    private String username = "";
    private Stage primaryStage;
    private Scene gameScene, finalResultScene;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showUsernameInputScene();
    }

    private void showUsernameInputScene() {
        Label usernameLabel = new Label("Enter your username:");
        usernameLabel.getStyleClass().add("label");
        TextField usernameField = new TextField();
        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("game-button");

        VBox inputLayout = new VBox(10, usernameLabel, usernameField, submitButton);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.setPrefSize(800, 800);
        inputLayout.getStyleClass().add("input-layout");

        submitButton.setOnAction(e -> {
            username = usernameField.getText().trim();
            if (username.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Username Missing");
                alert.setContentText("Please enter your username to proceed.");
                alert.showAndWait();
            } else {
                showMatchInputScene();
            }
        });

        Scene usernameScene = new Scene(inputLayout);
        try {
            usernameScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/semesterproject/RockPaper/Style.css")).toExternalForm());
        } catch (Exception i) {
            System.out.println(i.getMessage());
        }

        primaryStage.setTitle("Rock Paper Scissors - Username");
        primaryStage.setScene(usernameScene);
        primaryStage.show();
    }

    private void showMatchInputScene() {
        Label label = new Label("Enter the number of matches to play:");
        label.getStyleClass().add("label");
        TextField inputField = new TextField("5");
        Button submitButton = new Button("Start");
        submitButton.getStyleClass().add("game-button");

        VBox inputLayout = new VBox(10, label, inputField, submitButton);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.setPrefSize(800, 800);
        inputLayout.getStyleClass().add("input-layout");

        submitButton.setOnAction(e -> {
            try {
                roundsToPlay = Integer.parseInt(inputField.getText());
                if (roundsToPlay <= 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                roundsToPlay = 5; // Default to 5 if invalid input
            }
            showGameScene();
        });

        Scene inputScene = new Scene(inputLayout);
        try {
            inputScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/semesterproject/RockPaper/Style.css")).toExternalForm());
        } catch (Exception i) {
            System.out.println(i.getMessage());
        }

        primaryStage.setTitle("Rock Paper Scissors - Start");
        primaryStage.setScene(inputScene);
        primaryStage.show();
    }

    private void showGameScene() {
        Label usernameLabel = new Label("Player: " + username);
        usernameLabel.getStyleClass().add("label");
        Label computerLabel = new Label("Computer: 0");
        computerLabel.getStyleClass().add("label");
        Label playerLabel = new Label("Player: 0");
        playerLabel.getStyleClass().add("label");
        Label resultLabel = new Label("?");
        resultLabel.getStyleClass().add("result-label");
        Label playerChoiceLabel = new Label("Your Choice: ?");
        playerChoiceLabel.getStyleClass().add("choice-label");
        Label computerChoiceLabel = new Label("Computer's Choice: ?");
        computerChoiceLabel.getStyleClass().add("choice-label");

        Button rockButton = new Button("Rock");
        rockButton.getStyleClass().add("rock-button");
        Button paperButton = new Button("Paper");
        paperButton.getStyleClass().add("paper-button");
        Button scissorsButton = new Button("Scissors");
        scissorsButton.getStyleClass().add("scissors-button");
        Button showFinalResultButton = new Button("Show Final Result");
        showFinalResultButton.getStyleClass().add("show-result-button");

        rockButton.setOnAction(e -> playRound("Rock", computerLabel, playerLabel, resultLabel, playerChoiceLabel, computerChoiceLabel));
        paperButton.setOnAction(e -> playRound("Paper", computerLabel, playerLabel, resultLabel, playerChoiceLabel, computerChoiceLabel));
        scissorsButton.setOnAction(e -> playRound("Scissors", computerLabel, playerLabel, resultLabel, playerChoiceLabel, computerChoiceLabel));
        showFinalResultButton.setOnAction(e -> showFinalResultScene());

        HBox buttons = new HBox(20, rockButton, paperButton, scissorsButton);
        buttons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, usernameLabel, computerLabel, resultLabel, playerLabel, playerChoiceLabel, computerChoiceLabel, buttons, showFinalResultButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(800, 800);
        layout.getStyleClass().add("main-layout");

        gameScene = new Scene(layout);
        try {
            gameScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/semesterproject/RockPaper/Style.css")).toExternalForm());
        } catch (Exception i) {
            System.out.println(i.getMessage());
        }

        primaryStage.setTitle("Rock Paper Scissors - Game");
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    private void playRound(String playerChoice, Label computerLabel, Label playerLabel, Label resultLabel, Label playerChoiceLabel, Label computerChoiceLabel) {
        if (roundsPlayed >= roundsToPlay) {
            showFinalResultScene();
            return;
        }

        String[] options = {"Rock", "Paper", "Scissors"};
        String computerChoice = options[new Random().nextInt(3)];

        playerChoiceLabel.setText("Your Choice: " + playerChoice);
        computerChoiceLabel.setText("Computer's Choice: " + computerChoice);

        if (playerChoice.equals(computerChoice)) {
            resultLabel.setText("It's a Draw!");
        } else if ((playerChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                (playerChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                (playerChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            resultLabel.setText("You Win!");
            playerScore++;
        } else {
            resultLabel.setText("Computer Wins!");
            computerScore++;
        }

        computerLabel.setText("Computer: " + computerScore);
        playerLabel.setText("Player: " + playerScore);
        roundsPlayed++;
    }

    private void showFinalResultScene() {
        Label finalResultLabel = new Label("Final Score");
        finalResultLabel.getStyleClass().add("label");
        Label playerScoreLabel = new Label("Player: " + playerScore);
        playerScoreLabel.getStyleClass().add("label");
        Label computerScoreLabel = new Label("Computer: " + computerScore);
        computerScoreLabel.getStyleClass().add("label");

        Label winnerLabel = new Label();
        winnerLabel.getStyleClass().add("label");

        if (playerScore > computerScore) {
            winnerLabel.setText("Congratulations, " + username + "! You are the Winner!");
            winnerLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #4caf50; -fx-font-weight: bold;");
        } else if (computerScore > playerScore) {
            winnerLabel.setText("Computer is the Winner! Better luck next time, " + username + "!");
            winnerLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #e53935; -fx-font-weight: bold;");
        } else {
            winnerLabel.setText("It's a Draw! Both you and the computer have the same score.");
            winnerLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #ffcc00; -fx-font-weight: bold;");
        }

        Button closeButton = new Button("Close");
        closeButton.getStyleClass().add("close-button");
        closeButton.setOnAction(e -> primaryStage.close());

        VBox layout = new VBox(20, finalResultLabel, playerScoreLabel, computerScoreLabel, winnerLabel, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(800, 800);
        layout.getStyleClass().add("result-layout");

        finalResultScene = new Scene(layout);
        try {
            finalResultScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/semesterproject/RockPaper/Style.css")).toExternalForm());
        } catch (Exception i) {
            System.out.println(i.getMessage());
        }

        primaryStage.setTitle("Rock Paper Scissors - Final Result");
        primaryStage.setScene(finalResultScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
    launch(args);}
}
