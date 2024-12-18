package com.example.semesterproject.LoginMenu;

import com.example.semesterproject.GameSelectionMenu.GameSelectionMenu;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.util.Random;

public class LoginMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Setup the login scene
        VBox loginLayout = new VBox(20);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setPadding(new Insets(20));
        loginLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #1e3c72, #2a5298);");

        // Arcade Title (same font as the game selection menu)
        Label arcadeTitle = new Label("🎮 Arcade Games 🎮");
        arcadeTitle.setFont(Font.font("Arial", FontWeight.BOLD, 48));  // Larger font size
        arcadeTitle.setTextFill(Color.DARKORANGE);

        // Login Title
        Label title = new Label("Login");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setTextFill(Color.DARKORANGE);

        // Username and Password fields (centered)
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);  // Control width to keep them centered
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);  // Control width to keep them centered

        // Hidden label for validation messages
        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);
        messageLabel.setVisible(false);  // Initially hidden

        // Login Button
        Button loginButton = new Button("Login");
        loginButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        loginButton.setTextFill(Color.WHITE);
        loginButton.setStyle("-fx-background-color: #ff6347; -fx-border-color: #ffffff; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        loginButton.setPrefWidth(200);
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (checkLogin(username, password)) {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Login successful!");
                GameSelectionMenu menu = new GameSelectionMenu();
                menu.start(primaryStage);
            } else {
                messageLabel.setText("Invalid username or password.");
            }
            messageLabel.setVisible(true);
        });

        // Sign Up Button
        Button signUpButton = new Button("Sign Up");
        signUpButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        signUpButton.setTextFill(Color.WHITE);
        signUpButton.setStyle("-fx-background-color: #ff6347; -fx-border-color: #ffffff; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        signUpButton.setPrefWidth(200);
        signUpButton.setOnAction(event -> {
            // Switch to the sign-up scene
            signUpScene(primaryStage);
        });

        // Play as Guest Button
        Button guestButton = new Button("Play as Guest");
        guestButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        guestButton.setTextFill(Color.WHITE);
        guestButton.setStyle("-fx-background-color: #ff6347; -fx-border-color: #ffffff; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        guestButton.setPrefWidth(200);
        guestButton.setOnAction(event -> {
            // Play as guest - skip login
            GameSelectionMenu menu = new GameSelectionMenu();
            menu.start(primaryStage);
        });

        // Add components to the layout
        loginLayout.getChildren().addAll(arcadeTitle, title, usernameField, passwordField, loginButton, signUpButton, guestButton, messageLabel);

        // Set the scene and show the stage
        Scene scene = new Scene(loginLayout, 800, 800);
        primaryStage.setTitle("Login Menu");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Check if the username and password match any existing user in the file
    private boolean checkLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(":");
                if (credentials[0].equals(username) && credentials[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Sign-up scene where users can create a new account
    private void signUpScene(Stage primaryStage) {
        VBox signUpLayout = new VBox(20);
        signUpLayout.setAlignment(Pos.CENTER);
        signUpLayout.setPadding(new Insets(20));
        signUpLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #1e3c72, #2a5298);");

        // Title
        Label title = new Label("Sign Up");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setTextFill(Color.DARKORANGE);

        // Username and Password fields (centered)
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);  // Set width like login scene
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);  // Set width like login scene

        // Generate a random code for the user
        String generatedCode = generateRandomCode();
        Label codeLabel = new Label("Your code: " + generatedCode);
        codeLabel.setTextFill(Color.WHITE);
        codeLabel.setFont(Font.font("Arial", 16));

        // Code input field to match the generated code
        TextField codeInputField = new TextField();
        codeInputField.setPromptText("Enter your code");
        codeInputField.setMaxWidth(300);

        // Response message label (to show success/error messages)
        Label responseLabel = new Label();
        responseLabel.setTextFill(Color.RED);
        responseLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Create a VBox for the input fields and the buttons to center them
        VBox inputLayout = new VBox(10);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.getChildren().addAll(usernameField, passwordField, codeLabel, codeInputField, responseLabel);

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        submitButton.setTextFill(Color.WHITE);
        submitButton.setStyle("-fx-background-color: #ff6347; -fx-border-color: #ffffff; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        submitButton.setPrefWidth(200);
        submitButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String enteredCode = codeInputField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                responseLabel.setText("Please enter both username and password.");
                responseLabel.setTextFill(Color.RED);
            } else if (!enteredCode.equals(generatedCode)) {
                responseLabel.setText("Invalid code. Please try again.");
                responseLabel.setTextFill(Color.RED);
            } else if (userExists(username)) {
                responseLabel.setText("Username already exists.");
                responseLabel.setTextFill(Color.RED);
            } else {
                saveUser(username, password);
                responseLabel.setText("User registered successfully!");
                responseLabel.setTextFill(Color.GREEN);
            }
        });

        // Go Back Button
        Button goBackButton = new Button("Go Back");
        goBackButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        goBackButton.setTextFill(Color.WHITE);
        goBackButton.setStyle("-fx-background-color: #ff6347; -fx-border-color: #ffffff; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        goBackButton.setPrefWidth(200);
        goBackButton.setOnAction(event -> {
            start(primaryStage);  // Go back to the login scene
        });

        // Add components to the layout
        signUpLayout.getChildren().addAll(title, inputLayout, submitButton, goBackButton);

        // Set the scene and show the stage
        Scene signUpScene = new Scene(signUpLayout, 800, 800);
        primaryStage.setScene(signUpScene);
    }

    // Check if the username already exists in the users file
    private boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(":");
                if (credentials[0].equals(username)) {
                    return true;  // Username exists
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;  // Username doesn't exist
    }

    // Generate a random 6-character code
    private String generateRandomCode() {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }

    // Save the new user to the file
    private void saveUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + ":" + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
