package com.example.semesterproject.BattleShip;

import com.example.semesterproject.GameSelectionMenu.GameSelectionMenu;
import com.example.semesterproject.TicTakToe.RowColumnException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BattleShipUI extends Application {
    public void start(Stage primaryStage) {
        BorderPane userNameLayout = new BorderPane();
        userNameLayout.setStyle("-fx-background-color:  black;-fx-padding: 10px,3px,3px,3px");
        primaryStage.setWidth(1100);
        primaryStage.setHeight(1050);
        Image image = new Image("battleship.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(500);
        imageView.setFitWidth(1100);
        userNameLayout.setTop(imageView);
        Label nameLabel = new Label("Username");
        nameLabel.setStyle("-fx-text-fill: white;-fx-font-weight: bold;-fx-font-size: 18px;");
        HBox nameHBox = new HBox();
        nameHBox.setPrefWidth(150);
        nameHBox.setAlignment(Pos.CENTER);

        TextField username = new TextField();
        nameHBox.getChildren().add(username);
        Label userNameresult = new Label();
        userNameresult.setStyle("-fx-text-fill: white;-fx-font-weight: bold;-fx-font-size: 18px;");
        username.setPromptText("Username");
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color:  Green ; -fx-text-fill: white;-fx-font-weight: bold; -fx-font-size: 18px;");
        submitButton.setOnAction(e -> {
            if(username.getText().equals("")) {
               userNameresult.setText("Username cannot be empty");
               return;

            }
            primaryStage.close();
            this.gameScene(primaryStage,username.getText());
        });
        VBox inputBox = new VBox();
        inputBox.setSpacing(15);
        inputBox.setStyle("-fx-padding: 10px");
        inputBox.setAlignment(Pos.TOP_CENTER);
        inputBox.getChildren().addAll(nameLabel, nameHBox, submitButton,userNameresult);
        userNameLayout.setCenter(inputBox);

        primaryStage.setTitle("Battle Ship");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(userNameLayout));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    private void initializeShipGrid(ArrayList<Button> coordinatesOfShips,GridPane gridOfShip,BattleShip backend) {
        coordinatesOfShips.clear();
        gridOfShip.getChildren().clear();
        for(int i = 0; i <backend.getGridfShips().length ; i++){
            for(int j = 0; j < backend.getGridfShips()[0].length; j++){
                Button shipCoordinates = new Button("");
                this.styledShipCoordinatesButton(shipCoordinates);
                gridOfShip.add(shipCoordinates, i,j);
                coordinatesOfShips.add(shipCoordinates);
            }
        }
        backend.initialShipGrid();




    }
   private void styledShipCoordinatesButton(Button button){

        button.setPrefHeight(150);
        button.setPrefWidth(150);
        button.setStyle("-fx-padding: 2px;-fx-font-size: 30px;-fx-font-weight: bold;-fx-text-fill: white;-fx-background-color:skyblue;-fx-border-width: 2px;-fx-border-style: solid;-fx-border-color: white;-fx-border-radius: 2px");

    }
    private Label getStyledlabel (String text){
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold;-fx-font-size: 30px;-fx-text-fill: white");
        return  label;
    }
    private void updatedStats(Label remainingShips,Label hits,Label Misses,BattleShip backend){
        remainingShips.setText("RemainingShips: "+backend.remainingShips);
        hits.setText("Hits: "+backend.hits);
        Misses.setText("Misses: "+backend.misses);

    }
    private void resetGame(BattleShip backend,ArrayList<Button> coordinatesOfShip,GridPane gridOfShip,Label remainingShips,Label hits,Label Misses){
        for(Button curretnButton : coordinatesOfShip){
            curretnButton.setText("");
            this.styledShipCoordinatesButton(curretnButton);

        }
        backend.restartGame();
        this.updatedStats(remainingShips,hits,Misses,backend);
    }
    public void gameScene(Stage primaryStage ,String userName) {

        BattleShip backend = new BattleShip();
        BorderPane uiLayout = new BorderPane();
        uiLayout.setStyle("-fx-background-color:  black;-fx-padding: 10px,3px,3px,3px");
        Media missSound = new Media(this.getClass().getResource("/miss.wav").toExternalForm());

        Media hitSound = new Media(this.getClass().getResource("/Hit.wav").toExternalForm());



        ArrayList<Button> coordinatesOfShips = new ArrayList<>();
        GridPane gridOfShip = new GridPane();
        gridOfShip.setAlignment(Pos.CENTER);
        uiLayout.setCenter(gridOfShip);
        this.initializeShipGrid(coordinatesOfShips, gridOfShip,backend);
        //setting up the remaining ships,hits and misses updates at bottom left
        Label remainingShips = this.getStyledlabel("RemainingShips: "+backend.remainingShips);
        Label hits = this.getStyledlabel("Hits: "+backend.hits);
        Label misses = this.getStyledlabel("Misses: "+backend.misses);
        VBox fieldsBox = new VBox();

        fieldsBox.getChildren().addAll(remainingShips, hits, misses);
        fieldsBox.setAlignment(Pos.BOTTOM_LEFT);
        fieldsBox.setSpacing(10);


        Button resetButton = new Button("Reset");
        resetButton.setAlignment(Pos.BOTTOM_CENTER);
        resetButton.setOnAction(e -> {
            this.resetGame(backend,coordinatesOfShips,gridOfShip,remainingShips,hits,misses);
        });
        //bottom layout
        HBox bottomLayout = new HBox();
        bottomLayout.getChildren().addAll(fieldsBox, resetButton);
        bottomLayout.setPrefWidth(1000);
        bottomLayout.setSpacing(640);

        resetButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 18px;-fx-font-weight: bold");
        resetButton.setPrefWidth(160);
        uiLayout.setBottom(bottomLayout);
        //top layout
        HBox topLayout = new HBox();
        Label username =this.getStyledlabel("Username: "+userName);
        Button hintButton = new Button("Hint");
        hintButton.setStyle("-fx-background-color:  Green ;-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 18px;");
        hintButton.setPrefWidth(160);
        topLayout.setPrefWidth(1100);
        if(userName.length()>=6) {
            topLayout.setSpacing(610);
        }
        else
            topLayout.setSpacing(680);
        hintButton.setOnAction(actionEvent -> {
            int [] shipPosition = backend.hintSystem();
            if(shipPosition!=null) {
                for (Button currentButton : coordinatesOfShips) {
                    if (GridPane.getRowIndex(currentButton) == shipPosition[0] && GridPane.getColumnIndex(currentButton) == shipPosition[1]) {
                        currentButton.setStyle("-fx-padding: 2px;-fx-font-size: 30px;-fx-font-weight: bold;-fx-text-fill: white;-fx-background-color:yellow;-fx-border-width: 2px;-fx-border-style: solid;-fx-border-color: white;-fx-border-radius: 2px");
                        break;
                    }
                }
            }
        });

        hintButton.setAlignment(Pos.TOP_CENTER);
        topLayout.getChildren().addAll(username,hintButton);
        uiLayout.setTop(topLayout);

        for(Button currentShipCoordinate : coordinatesOfShips){
            currentShipCoordinate.setOnAction(event -> {
                int row = GridPane.getRowIndex(currentShipCoordinate);
                int col = GridPane.getColumnIndex(currentShipCoordinate);
                try {
                    if(currentShipCoordinate.getText().equalsIgnoreCase("HIT")||currentShipCoordinate.getText().equalsIgnoreCase("MISS")){
                        return;
                    }
                    else  if(backend.processGuess(row,col)){
                        MediaPlayer hitSoundPlayer = new MediaPlayer(hitSound);
                        hitSoundPlayer.play();
                        currentShipCoordinate.setStyle("-fx-padding: 2px;-fx-font-size: 20px;-fx-font-weight: bold;-fx-text-fill: white;-fx-background-color:green;-fx-border-width: 2px;-fx-border-style: solid;-fx-border-color: white;-fx-border-radius: 2px");
                        currentShipCoordinate.setText("HIT");

                    }
                    else{
                        MediaPlayer missSoundPlayer = new MediaPlayer(missSound);
                        missSoundPlayer.play();
                        currentShipCoordinate.setStyle("-fx-padding: 2px;-fx-font-size: 20px;-fx-font-weight: bold;-fx-text-fill: white;-fx-background-color:red;-fx-border-width: 2px;-fx-border-style: solid;-fx-border-color: white;-fx-border-radius: 2px");
                        currentShipCoordinate.setText("MISS");
                    }

                    this.updatedStats(remainingShips,hits,misses,backend);
                    if(backend.isGameOver()) {
                        primaryStage.close();
                        this.endingScene(primaryStage,userName,backend,remainingShips,hits,misses);

                    }
                } catch (RowColumnException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        Scene battleShipScene = new Scene(uiLayout);
        primaryStage.setScene(battleShipScene);
        primaryStage.show();
    }
    public void endingScene(Stage primaryStage, String userName, BattleShip backend, Label shipRemaining, Label hits, Label misses) {
        Media winningsSound = new Media(this.getClass().getResource("/winning.wav").toExternalForm());
        MediaPlayer winningSoundPlayer = new MediaPlayer(winningsSound);
        winningSoundPlayer.play();
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle(primaryStage.getTitle());
        secondaryStage.setResizable(false);
        secondaryStage.setWidth(600);
        secondaryStage.setHeight(600);

        Label stats = this.getStyledlabel("Congratulations! You won\nYour stats are:\n" + shipRemaining.getText() + "\n" + hits.getText() + "\n" + misses.getText());
        stats.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");


        Button tryAgainButton = new Button("Try Again");
        tryAgainButton.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #2ecc71; -fx-padding: 10px 20px;");
        tryAgainButton.setOnAction(event -> {
            secondaryStage.close();
            this.gameScene(primaryStage,userName);
        });


        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            secondaryStage.close();
            GameSelectionMenu menu = new GameSelectionMenu();
            menu.start(new Stage());
        });
        exitButton.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #e74c3c; -fx-padding: 10px 20px;");

        // Arrange the buttons and label with spacing in a VBox
        VBox buttonLayoutAndLabel = new VBox();
        buttonLayoutAndLabel.setStyle("-fx-background-color: black; -fx-alignment: center; -fx-padding: 20px;");
        buttonLayoutAndLabel.setSpacing(20);
        buttonLayoutAndLabel.getChildren().addAll(stats,tryAgainButton, exitButton);

        buttonLayoutAndLabel.setAlignment(Pos.CENTER);
        secondaryStage.setScene(new Scene(buttonLayoutAndLabel));
        secondaryStage.show();
    }
}

