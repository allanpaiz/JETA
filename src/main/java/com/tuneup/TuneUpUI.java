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
    private ProfileManager profileManager;
    private User userProfile;
    // private Stage primaryStage;
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
        // this.primaryStage = stage;
        FileUtils.ensureDataFolderExists();
        profileManager = new ProfileManager();              // TODO: Fix ProfileManager

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

        /**
         * These are the fillable fields for the login screen
         * Everything needs to be a variable then added to the layout below:
         *   -   layout.getChildren().addAll(variables here) will add them to the layout
         */
        Label welcomeLabel = new Label("Welcome to TuneUp!");                   // Title
        TextField usernameField = new TextField();                              // Username field
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();                      // Password field
        passwordField.setPromptText("Password");
        TextField emailField = new TextField();                                 // Email field
        emailField.setPromptText("Email (for new users)");

        ComboBox<String> roleCombo = new ComboBox<>();                          // Role combo box from javafx.scene.control
        roleCombo.getItems().addAll("Teacher", "Student");
        roleCombo.setPromptText("Select Role");
        ComboBox<String> expCombo = new ComboBox<>();                           // Experience combo box
        expCombo.getItems().addAll("Beginner", "Intermediate", "Advanced");
        expCombo.setPromptText("Experience Level");
        expCombo.setVisible(false);                                             // Hide experience combo box by default
        
        Button loginButton = new Button("Login / Register");                    // Login button
        Label statusLabel = new Label();                                        // Status label (for login errors)

        /**
         * This handles the dropdown for the roles
         * If the role is student, show the experience combo box
         */
        roleCombo.setOnAction(e -> {
            expCombo.setVisible(roleCombo.getValue() != null && roleCombo.getValue().equals("Student"));
        });

        /**
         * This handles the login button
         * It stores the info into variables
         */
        loginButton.setOnAction(e -> {
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

            // Creates a user through the profile manager
            // TODO: This needs to work with DataLoader/Writer through ProfileManager
            User user = profileManager.getProfile(username);

            /** This section handles login.
             *      - if the above user is not null, it's an existing user
             *        veryfies the password and logs in
             *        currently not actually checking if the user exists
             *     - if the user is null, it's a new user
             *       checks if the email and role are filled
             *       creates a new user profile and "adds" it to the profile manager
             *       shows the main menu
             * 
             * TODO: This needs to work with DataWriter through ProfileManager, needs a rework
             */
            if (user != null) {
                // Existing user
                if (user.verifyPassword(password)) {
                    // Successful login - set the user profile and show the main menu
                    this.userProfile = user;
                    mainMenuScene = createMainMenuScene(primaryStage);
                    primaryStage.setScene(mainMenuScene);
                } else {
                    statusLabel.setText("Incorrect password");
                }
            } else {    
                // New User
                if (email.isEmpty() || role == null) {
                    // ensure email and a role is selected
                    statusLabel.setText("Please fill all fields for registration");
                    return;
                }
                ExperienceLevel expierienceLevel;   // enum for experience level
                if (role.equals("Teacher")) {
                    // defaulting Teachers to advanced, probably need to change this
                    expierienceLevel = ExperienceLevel.ADVANCED;
                } else {
                    // Students
                    String exp = expCombo.getValue();
                    if (exp == null) {
                        statusLabel.setText("Please select experience level");
                        return;
                    }
                    expierienceLevel = ExperienceLevel.valueOf(exp.toUpperCase());
                }

                // Create the user profile and add it to the profile manager
                userProfile = new User(username, password, email, role, expierienceLevel);
                profileManager.addProfile(userProfile);

                // Show the main menu
                mainMenuScene = createMainMenuScene(primaryStage);
                primaryStage.setScene(mainMenuScene);
            }
        });

        // Add all the elements to the layout
        layout.getChildren().addAll(welcomeLabel, usernameField, passwordField, emailField, 
            roleCombo, expCombo, loginButton, statusLabel);
        
        // Return the scene
        return new Scene(layout, 300, 400);
    }

    /**
     * Creates the main menu scene
     * 
     * @param stage
     * @return
     */
    public Scene createMainMenuScene(Stage stage) {
        // Basic layout
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(20));

        // Main menu elements
        Label title = new Label("TuneUp Main Menu");
        Button songLibraryBtn = new Button("Song Library");

        // TODO: Implement these modes
        Button lessonsBtn = new Button("Lessons");
        Button createBtn = new Button("Create");
        Button playBtn = new Button("Play");
        Button templateBtn = new Button("Template");
        Button exitBtn = new Button("Exit");

        // This handles the song library button, goes to the SongLibraryMode
        songLibraryBtn.setOnAction(e -> {
            Mode mode = new SongLibraryMode(userProfile, profileManager, stage, this);
            mode.handle();
        });

        // Placeholder Example to make sure I can switch modes
        templateBtn.setOnAction(e -> {
            Mode mode = new TemplateMode(userProfile, profileManager, stage, this);
            mode.handle();
        });


        // TODO: Implement these modes
        // lessonsBtn.setOnAction(e -> {
        //     Mode mode = new LessonMode(userProfile, profileManager, stage, this);
        //     mode.handle();
        // });

        // createBtn.setOnAction(e -> {
        //     Mode mode = new CreateMode(userProfile,userProfile, profileManager, stage, this, InstrumentFactory.createInstrument(InstrumentType.PIANO));
        //     mode.handle();
        // });

        // playBtn.setOnAction(e -> {
        //     Mode mode = new PlayMode(userProfile,userProfile, profileManager, stage, this, InstrumentFactory.createInstrument(InstrumentType.PIANO));
        //     mode.handle();
        // });

        // Exit button
        exitBtn.setOnAction(e -> stage.close());

        // Add all the elements to the layout
        layout.getChildren().addAll(title, songLibraryBtn, lessonsBtn, createBtn, playBtn, templateBtn, exitBtn);
        
        // Return the scene
        return new Scene(layout, 300, 250);
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