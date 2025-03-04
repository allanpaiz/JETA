package com.tuneup;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;

/**
 * Main class
 */
public class TuneUpUI extends Application {
    private TuneUp facade;
    private User userProfile;
    private Scene mainMenuScene;

    /**
     * Start method
     * This is the entry point of the application
     * 
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FileUtils.ensureDataFolderExists();
        facade = new TuneUp();

        // Create the login scene and show it
        Scene loginScene = createLoginScene(stage);
        stage.setTitle("TuneUp");
        stage.setScene(loginScene);
        stage.show();
    }

    /**
     * Creates the login scene
     * 
     * @param primaryStage
     * @return
     */
    private Scene createLoginScene(Stage primaryStage) {
        // This works the layout of the login screen
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(20));

        // These are the fillable fields for the login screen
        Label welcomeLabel = new Label("Welcome to TuneUp!");                   // Title
        TextField usernameField = new TextField();                              // Username field
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();                      // Password field
        passwordField.setPromptText("Password");
        TextField emailField = new TextField();                                 // Email field
        emailField.setPromptText("Email (for new users)");
        emailField.setVisible(false);                                           // Hide email field by default

        ComboBox<String> roleCombo = new ComboBox<>();                          // Role combo box from javafx.scene.control
        roleCombo.getItems().addAll("Teacher", "Student");
        roleCombo.setPromptText("Select Role");
        roleCombo.setVisible(false);                                            // Hide role combo box by default

        ComboBox<String> expCombo = new ComboBox<>();                           // Experience combo box
        expCombo.getItems().addAll("Beginner", "Intermediate", "Advanced");
        expCombo.setPromptText("Experience Level");
        expCombo.setVisible(false);                                             // Hide experience combo box by default

        Button loginButton = new Button("Login");                               // Login button
        Button signupButton = new Button("Signup");                             // Signup button
        Label statusLabel = new Label();                                        // Status label (for login errors)

        // This handles the dropdown for the roles
        roleCombo.setOnAction(e -> {
            expCombo.setVisible(roleCombo.getValue() != null && roleCombo.getValue().equals("Student"));
        });

        // This handles the login button
        loginButton.setOnAction(e -> {
            // Get the values from the fields
            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            // Check if the username and password are empty
            if (username.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Username and password are required");
                return;
            }

            // Check if the user exists
            User user = facade.login(username, password);

            if (user != null) {
                // Existing user
                this.userProfile = user;
                statusLabel.setText("Login successful!");
                System.out.println("User exists: " + username);
                showMainMenu(primaryStage);
            } else {
                // User does not exist
                statusLabel.setText("Invalid username or password");
                System.out.println("User does not exist: " + username);
            }
        });

        // This handles the signup button
        signupButton.setOnAction(e -> {
            // Get the values from the fields and combo boxes
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            String email = emailField.getText().trim();
            String role = roleCombo.getValue();

            // Check if the username and password are empty
            if (username.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Username and password are required");
                return;
            }

            // Check if the user already exists
            User existingUser = facade.login(username, password);
            if (existingUser != null) {
                statusLabel.setText("Username already exists");
                System.out.println("Username already exists: " + username);
                return;
            }

            // Ensure email and role are filled for new user registration
            if (email.isEmpty() || role == null) {
                statusLabel.setText("Please fill all fields for registration");
                emailField.setVisible(true);
                roleCombo.setVisible(true);
                return;
            }

            ExperienceLevel experienceLevel;   // enum for experience level
            if (role.equals("Teacher")) {
                // Defaulting Teachers to advanced
                experienceLevel = ExperienceLevel.ADVANCED;
            } else {
                // Students
                String exp = expCombo.getValue();
                if (exp == null) {
                    statusLabel.setText("Please select experience level");
                    return;
                }
                experienceLevel = ExperienceLevel.valueOf(exp.toUpperCase());
            }

            // Register the user
            userProfile = facade.register(username, password, email, role, experienceLevel);
            statusLabel.setText("Registration successful! Please log in.");
            System.out.println("User registered: " + username);
        });

        // Add all the elements to the layout
        layout.getChildren().addAll(welcomeLabel, usernameField, passwordField, emailField, 
            roleCombo, expCombo, loginButton, signupButton, statusLabel);
        
        // Return the scene
        return new Scene(layout, 300, 400);
    }

    /**
     * Shows the main menu with the "Create" button
     * 
     * @param stage
     */
    public void showMainMenu(Stage stage) {
        if (mainMenuScene == null) {
            VBox layout = new VBox(10);
            layout.setPadding(new javafx.geometry.Insets(20));
    
            Label titleLabel = new Label("Main Menu");
            Button createButton = new Button("Create");
            Button songLibraryButton = new Button("Song Library");
            Button exitButton = new Button("Exit");
    
            // Handle the "Create" button click
            createButton.setOnAction(e -> {
                facade.activateCreateMode(userProfile, stage, this);
            });
    
            songLibraryButton.setOnAction(e -> {
                facade.activateSongLibrary(userProfile, stage, this);
            });
    
            exitButton.setOnAction(e -> {
                stage.close();
            });

            layout.getChildren().addAll(titleLabel, createButton, songLibraryButton, exitButton);
    
            mainMenuScene = new Scene(layout, 300, 200);
        }

        stage.setScene(mainMenuScene);
    }


    /**
     * Main method, launches the application
     * Don't change for now
     * Somehow this goes to start() method
     */
    public static void main(String[] args) {
        launch();
    }
}