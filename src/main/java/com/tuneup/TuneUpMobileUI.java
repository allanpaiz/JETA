package com.tuneup;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.animation.PauseTransition; // Added import
import javafx.util.Duration; // Added import

import java.io.IOException;

/**
 * Mobile-optimized login screen for TuneUp application
 */
public class TuneUpMobileUI extends Application {
    private TuneUp facade;
    
    // Color constants for styling
    private static final String PRIMARY_COLOR = "#6200EE";
    private static final String SECONDARY_COLOR = "#03DAC5";
    
    @Override
    public void start(Stage stage) throws IOException {
        FileUtils.ensureDataFolderExists();
        facade = new TuneUp();
        
        // Create the login scene and show it
        Scene loginScene = createMobileLoginScene(stage);
        stage.setTitle("TuneUp");
        stage.setScene(loginScene);
        stage.setWidth(360);  // Standard mobile width
        stage.setHeight(640); // Standard mobile height
        stage.show();
    }
    
    /**
     * Creates a simple mobile-optimized login screen
     */
    private Scene createMobileLoginScene(Stage primaryStage) {
        // Main container with padding appropriate for touch
        VBox root = new VBox(25);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(40, 20, 40, 20));
        root.setStyle("-fx-background-color: #F5F5F5;");
        
        // App logo/title
        Label titleLabel = new Label("🎵 TUNE UP");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.web(PRIMARY_COLOR));
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        titleLabel.setPadding(new Insets(20, 0, 10, 0));
        
        // Username field - larger for touch input
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefHeight(50);
        usernameField.setStyle("-fx-font-size: 16px; -fx-background-radius: 8px;");
        usernameField.setMaxWidth(300);
        
        // Password field - larger for touch input
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefHeight(50);
        passwordField.setStyle("-fx-font-size: 16px; -fx-background-radius: 8px;");
        passwordField.setMaxWidth(300);
        
        // Status message for feedback
        Label statusLabel = new Label();
        statusLabel.setTextFill(Color.RED);
        statusLabel.setFont(Font.font(14));
        statusLabel.setTextAlignment(TextAlignment.CENTER);
        statusLabel.setMaxWidth(300);
        
        // Login button - large for easy tapping
        Button loginButton = new Button("LOGIN");
        loginButton.setPrefWidth(300);
        loginButton.setPrefHeight(50);
        loginButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        loginButton.setStyle("-fx-background-color: " + PRIMARY_COLOR + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 25;");
        
        // Register link
        Hyperlink registerLink = new Hyperlink("New user? Create an account");
        registerLink.setFont(Font.font(14));
        registerLink.setTextFill(Color.web(PRIMARY_COLOR));
        
        // Login button action
        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            
            if (username.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Username and password are required");
                return;
            }
            
            // Attempt login
            User user = facade.login(username, password);
            
            if (user != null) {
                statusLabel.setText("Login successful!");
                statusLabel.setTextFill(Color.web(SECONDARY_COLOR));
                
                // Show success message with delay effect
                PauseTransition delay = new PauseTransition(Duration.seconds(1));
                delay.setOnFinished(event -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Welcome");
                    alert.setHeaderText(null);
                    alert.setContentText("Welcome to TuneUp, " + user.getUsername() + "!");
                    alert.showAndWait();
                });
                delay.play();
            } else {
                statusLabel.setText("Invalid username or password");
            }
        });
        
        // Register link action
        registerLink.setOnAction(e -> {
            statusLabel.setText("Registration screen would appear here");
            statusLabel.setTextFill(Color.web(PRIMARY_COLOR));
        });
        
        // Add spacing between elements
        Region spacer1 = new Region();
        spacer1.setPrefHeight(20);
        Region spacer2 = new Region();
        spacer2.setPrefHeight(10);
        
        // Add all elements to root container with proper spacing
        root.getChildren().addAll(
            titleLabel,
            spacer1,
            usernameField,
            passwordField,
            statusLabel,
            spacer2,
            loginButton,
            registerLink
        );
        
        // Push everything to the top with a VBox.vgrow spacer at bottom
        Region bottomSpacer = new Region();
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);
        root.getChildren().add(bottomSpacer);
        
        // Add version text at bottom
        Label versionLabel = new Label("TuneUp v1.0");
        versionLabel.setTextFill(Color.GRAY);
        versionLabel.setFont(Font.font(12));
        root.getChildren().add(versionLabel);
        
        return new Scene(root);
    }
    
    /**
     * Main method, launches the application
     */
    public static void main(String[] args) {
        launch(args); // Fixed: passing args to launch
    }
}