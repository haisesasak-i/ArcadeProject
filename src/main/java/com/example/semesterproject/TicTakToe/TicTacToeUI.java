package com.example.semesterproject.TicTakToe;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.example.semesterproject.GameSelectionMenu.GameSelectionMenu;


import java.util.ArrayList;
import java.util.Random;

public class TicTacToeUI extends Application {

    public void start(Stage window) {
        Image image = new Image("b.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(200);
        // Window setup
        window.setHeight(800);
        window.setWidth(800);
        window.setResizable(false);

        TicTakToe game = new TicTakToe();

        // UI Components
        Button submissionButton = new Button("Submit");
        submissionButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 18px;");
        submissionButton.setPrefWidth(150);

        Label username = new Label("Username: ");
        username.setPrefWidth(100);
        username.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

        TextField nameField = new TextField();
        nameField.setPromptText("USERNAME");

        Label symbol = new Label("Symbol: ");
        symbol.setPrefWidth(100);
        symbol.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
        ToggleGroup group = new ToggleGroup();
        RadioButton xButton =this.styledRadioButtons("X");

        RadioButton oButton = this.styledRadioButtons("O");

        xButton.setToggleGroup(group);
        oButton.setToggleGroup(group);


        // Layout setup
        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: black");
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(imageView);
        borderPane.setStyle("-fx-background-color: black");
        HBox nameBox = new HBox();

        nameBox.setAlignment(Pos.CENTER);
        nameBox.setSpacing(20);
        nameBox.getChildren().addAll(username, nameField);

        HBox symbolBox = new HBox();
        symbolBox.setAlignment(Pos.CENTER);
        symbolBox.setSpacing(20);

        symbolBox.getChildren().addAll(symbol, xButton,oButton);

        vbox.getChildren().addAll(nameBox, symbolBox, submissionButton);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);

        borderPane.setCenter(vbox);
        borderPane.setStyle("-fx-background-color: #f0f0f0;");

        Scene scene = new Scene(borderPane);
        window.setScene(scene);
        window.setTitle("Tic Tac Toe");
        window.show();

        // Submission button action
        submissionButton.setOnAction(e -> {
            String userName = nameField.getText();
            RadioButton button = (RadioButton) group.getSelectedToggle();
            if(button==null){
                System.out.println("Game symbol cannot be empty.");
                return;
            }
            String gameSymbol = button.getText();
            String enemySymbol ="";

            if (userName.isEmpty()) {
                System.out.println("Username cannot be empty.");
                return;
            }

            if(gameSymbol.equalsIgnoreCase("X")){
                enemySymbol ="O";

            }
            else
                enemySymbol ="X";
            this.setupBoard(window, game, userName, gameSymbol,enemySymbol);
        });
    }

    private void setupBoard(Stage primaryStage, TicTakToe game, String userName, String symbol ,String enemySymbol) {
        String tapSoundPath ="";
        try{tapSoundPath = getClass().getResource("/tapSound.wav").toExternalForm();}catch (Exception e){e.printStackTrace();}

        Media tapSound = new Media(tapSoundPath);



        Label usernameLabel = new Label("Username: " + userName);
        usernameLabel.setAlignment(Pos.TOP_LEFT);
        usernameLabel.setPrefWidth(200);
        usernameLabel.setStyle("-fx-font-size: 20px; -fx-text-fill:white;");

        BorderPane root = new BorderPane();
        root.setPrefHeight(800);
        root.setPrefWidth(800);
        root.setStyle("-fx-background-color: black;");

        GridPane grid = new GridPane();
        ArrayList<Button> buttons = new ArrayList<>();
        this.initializeBoard(buttons, grid, game, symbol);

        root.setTop(usernameLabel);
        Label currentPlayerTurn = new Label("Turn: " + game.getCurrentPlayer());
        currentPlayerTurn.setPrefWidth(80);
        currentPlayerTurn.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        for (Button button : buttons) {
            button.setOnAction(event -> {

                int row = GridPane.getRowIndex(button);
                int col = GridPane.getColumnIndex(button);

                try {
                    if (game.makeMove(row, col)) {
                        MediaPlayer userSound = new MediaPlayer(tapSound);
                        button.setText(game.getCurrentPlayer());
                        userSound.play();


                        button.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 40px;-fx-border-width: 2px;-fx-border-color: white;-fx-border-radius: 4px;");

                        if (game.checkTie() || game.checkWinner()) {
                            if (game.checkTie()) {
                                this.endingOnTieOrWinner(primaryStage, new Stage(),"tie");
                            } else {
                                this.endingOnTieOrWinner(primaryStage,new Stage(), "winner is " + game.getCurrentPlayer());
                            }
                        } else {
                            game.switchPlayer();
                            currentPlayerTurn.setText("Turn: " + game.getCurrentPlayer());}

                        if(game.getCurrentPlayer().equalsIgnoreCase(enemySymbol)){
                            PauseTransition pause = new PauseTransition(Duration.seconds(1));

                            pause.play();
                            pause.setOnFinished(e->{
                                MediaPlayer media = new MediaPlayer(tapSound);
                                Random random = new Random();
                                int enemyRow =0;
                                int enemyColumn =0;
                                do {
                                    enemyRow= random.nextInt(3);
                                    enemyColumn = random.nextInt(3);

                                }while (!game.checkForEmptyRowAndColumn(enemyRow,enemyColumn));
                                try {
                                    game.makeMove(enemyRow,enemyColumn);
                                } catch (RowColumnException ex) {
                                    throw new RuntimeException(ex);
                                }
                                for(Button currentButton : buttons){
                                    if(GridPane.getRowIndex(currentButton).equals(enemyRow)&&GridPane.getColumnIndex(currentButton).equals(enemyColumn)){
                                        currentButton.setText(game.getCurrentPlayer());
                                        media.play();
                                        currentButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 40px;-fx-border-width: 2px;-fx-border-color: white;-fx-border-radius: 4px;");
                                        game.switchPlayer();
                                        currentPlayerTurn.setText("Turn: " + game.getCurrentPlayer());
                                        break;
                                    }
                                    if (game.checkTie() || game.checkWinner()) {
                                        if (game.checkTie()) {
                                            this.endingOnTieOrWinner(primaryStage,new Stage(), "tie");
                                        } else {
                                            this.endingOnTieOrWinner(primaryStage,new Stage(), "winner is " + game.getCurrentPlayer());
                                        }
                                    }
                                }


                            });

                        }




                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });

        }

        grid.setAlignment(Pos.CENTER);

        Button resetButton = new Button("Reset");
        resetButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 18px;");
        resetButton.setPrefWidth(150);
        resetButton.setOnAction(event -> this.resetBoard(buttons, game, symbol));

        root.setCenter(grid);
        HBox box = new HBox();
        box.setSpacing(240);
        box.getChildren().addAll(currentPlayerTurn, resetButton);
        root.setBottom(box);

        Scene newScene = new Scene(root);
        primaryStage.setScene(newScene);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private Button styledButtons(String text) {
        Button button = new Button(text);
        button.setPrefHeight(200);
        button.setPrefWidth(200);
        button.setStyle("-fx-font-size: 80px; -fx-text-fill: grey; -fx-background-color: #eeeeee; -fx-border-color: #bbbbbb; -fx-padding: 5px;");
        return button;
    }

    private void initializeBoard(ArrayList<Button> buttons, GridPane grid, TicTakToe game, String symbol) {
        buttons.clear();
        grid.getChildren().clear();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = this.styledButtons("");
                buttons.add(button);
                grid.add(button, j, i);
            }
        }
        game.initializeBoard(symbol);
    }

    private void resetBoard(ArrayList<Button> buttons, TicTakToe game, String symbol) {
        for (Button button : buttons) {
            button.setText("");
            button.setStyle("-fx-font-size: 80px; -fx-text-fill: grey; -fx-background-color: #eeeeee; -fx-border-color: #bbbbbb; -fx-padding: 5px;");
        }
        game.resetGame(symbol);
    }

    public void endingOnTieOrWinner(Stage oldStage,Stage stage, String text) {
        stage.setHeight(600);
        stage.setWidth(600);
        stage.setResizable(false);
        String result;
        if (text.equals("tie")) {
            result = "The match ended in a tie.";
        } else {
            result = "The match ended with a winner. \n\t\tThe " + text;
        }
        Label resultLabel = new Label(result);
        resultLabel.setAlignment(Pos.CENTER);
        resultLabel.setStyle("-fx-text-fill: white;-fx-font-size: 20px;");
        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: black");
        Button tryAgain = new Button("Try Again");
        tryAgain.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 18px;");
        tryAgain.setPrefWidth(150);

        Button exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 18px;");
        exitButton.setPrefWidth(150);

        vbox.getChildren().addAll(resultLabel, tryAgain, exitButton);
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox);

        tryAgain.setOnAction(event -> {
            stage.close();
            this.start(oldStage);


        });
        exitButton.setOnAction(event ->
        {oldStage.close();
                stage.close();
            GameSelectionMenu menu = new GameSelectionMenu();
            menu.start(new Stage());
        });

        stage.setScene(scene);
        stage.show();
    }
    private  RadioButton styledRadioButtons(String text) {
        RadioButton button = new RadioButton(text);
        button.setStyle("-fx-text-fill: white;-fx-font-size: 18px");
        button.setPrefWidth(50);
        return button;
    }
}