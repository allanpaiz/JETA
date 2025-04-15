package tuneup;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import controllers.HomeController;
import controllers.LoginController;
import controllers.SignUpController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Mobile-optimized UI for TuneUp application
 * Provides login, registration, and home screens with navigation to various modes
 */
public class TuneUpMobileUI extends Application {
    private static final Logger logger = Logger.getLogger(TuneUpMobileUI.class.getName());
    
    private TuneUp facade;
    private Stage primaryStage;
    private LoginController loginController;
    private SignUpController signUpController;
    private HomeController homeController;
    
    // Color constants for styling
    private static final String PRIMARY_COLOR = "#6200EE";
    private static final String SECONDARY_COLOR = "#03DAC5";
    private static final String APP_BACKGROUND_COLOR = "#B4F0F9";
    
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FileUtils.ensureDataFolderExists();
            
            // Initialize facade and controllers
            facade = new TuneUp();
            loginController = new LoginController(facade);
            signUpController = new SignUpController(facade);
            homeController = new HomeController(facade);
            
            this.primaryStage = stage;
            
            // Create the login scene and show it
            Scene loginScene = createMobileLoginScene();
            stage.setTitle("TuneUp");
            stage.setScene(loginScene);
            stage.setWidth(390);  // Match image width
            stage.setHeight(844); // Match image height
            stage.show();
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error starting application", e);
            throw e;
        }
    }
    
    /**
     * Creates a simple mobile-optimized login screen
     */
    private Scene createMobileLoginScene() {
        // Main container
        StackPane mainContainer = new StackPane();
        
        try {
            // Load background image from resources folder
            String imagePath = "/images/TuneUpLoginBackgroundImage.png";
            Image backgroundImage = new Image(getClass().getResourceAsStream(imagePath));
            ImageView backgroundView = new ImageView(backgroundImage);
            
            // Make the background fit the scene
            backgroundView.setFitWidth(390);
            backgroundView.setFitHeight(844);
            backgroundView.setPreserveRatio(false);
            
            // Add light overlay for better text readability
            Rectangle overlay = new Rectangle(390, 844);
            overlay.setFill(Color.rgb(0, 0, 0, 0.2));
            
            // Add background and overlay
            mainContainer.getChildren().addAll(backgroundView, overlay);
        } catch (Exception e) {
            // Fallback to plain color background if image can't be loaded
            mainContainer.setStyle("-fx-background-color: " + APP_BACKGROUND_COLOR + ";");
            logger.log(Level.WARNING, "Background image could not be loaded", e);
        }
        
        // Main content with padding appropriate for touch
        VBox content = new VBox(25);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(40, 20, 40, 20));
        content.setBackground(Background.EMPTY); // Transparent background to show the image
        
        // Login panel with semi-transparent background
        VBox loginPanel = new VBox(15);
        loginPanel.setAlignment(Pos.CENTER);
        loginPanel.setPadding(new Insets(25));
        loginPanel.setMaxWidth(300);
        loginPanel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.85);" +
                          "-fx-background-radius: 15;" +
                          "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");
        
        // Username field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefHeight(50);
        usernameField.setStyle("-fx-font-size: 16px; -fx-background-radius: 8px;");
        usernameField.setMaxWidth(250);
        usernameField.setFocusTraversable(false);

        // Password field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefHeight(50);
        passwordField.setStyle("-fx-font-size: 16px; -fx-background-radius: 8px;");
        passwordField.setMaxWidth(250);
        passwordField.setFocusTraversable(false);
        
        // Status message
        Label statusLabel = new Label();
        statusLabel.setTextFill(Color.RED);
        statusLabel.setFont(Font.font(14));
        statusLabel.setTextAlignment(TextAlignment.CENTER);
        statusLabel.setMaxWidth(250);
        
        // Login button
        Button loginButton = new Button("LOGIN");
        loginButton.setPrefWidth(250);
        loginButton.setPrefHeight(50);
        loginButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        loginButton.setStyle("-fx-background-color: " + PRIMARY_COLOR + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 25;");
        
        // Register link
        Hyperlink registerLink = new Hyperlink("New user? Create an account");
        registerLink.setFont(Font.font(14));
        registerLink.setTextFill(Color.web(PRIMARY_COLOR));
        
        // Add elements to login panel
        loginPanel.getChildren().addAll(
            usernameField,
            passwordField,
            statusLabel,
            loginButton,
            registerLink
        );
        
        // Login button action - now uses LoginController
        loginButton.setOnAction(e -> {
            try {
                String username = usernameField.getText().trim();
                String password = passwordField.getText();
                
                // Use controller to validate input
                String validationError = loginController.validateCredentials(username, password);
                if (!validationError.isEmpty()) {
                    statusLabel.setText(validationError);
                    return;
                }
                
                // Use controller to authenticate
                User user = loginController.authenticateUser(username, password);
                
                if (user != null) {
                    statusLabel.setText("Login successful!");
                    statusLabel.setTextFill(Color.web(SECONDARY_COLOR));
                    
                    // Switch to home screen after successful login
                    primaryStage.setScene(createHomeScene(user));
                } else {
                    statusLabel.setText("Invalid username or password");
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error during login", ex);
                statusLabel.setText("An error occurred. Please try again.");
            }
        });
        
        // Register link action - switch to registration screen
        registerLink.setOnAction(e -> {
            primaryStage.setScene(createMobileRegistrationScene());
        });
        
        // Add version text at bottom
        Label versionLabel = new Label("TuneUp v1.0");
        versionLabel.setTextFill(Color.WHITE);
        versionLabel.setFont(Font.font(12));
        
        // Create a spacer to position the login panel appropriately
        Region topSpacer = new Region();
        topSpacer.setPrefHeight(280); // Adjust this value to position the login panel correctly
        
        // Add all elements to content with proper spacing
        content.getChildren().addAll(
            topSpacer,
            loginPanel,
            new Region() // Spacer
        );
        
        // Push everything to the top with a VBox.vgrow spacer
        Region bottomSpacer = new Region();
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);
        content.getChildren().add(bottomSpacer);
        content.getChildren().add(versionLabel);
        
        // Add content pane on top of background
        mainContainer.getChildren().add(content);
        
        return new Scene(mainContainer);
    }
    
    /**
     * Creates a registration screen with form fields for creating a new account
     */
    private Scene createMobileRegistrationScene() {
        // Main container with the light blue background
        StackPane mainContainer = new StackPane();
        mainContainer.setStyle("-fx-background-color: " + APP_BACKGROUND_COLOR + ";");
        
        // Main content with padding appropriate for touch
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setPannable(true); // Allow touch scrolling
        
        VBox content = new VBox(20);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(40, 20, 40, 20));
        content.setBackground(Background.EMPTY);
        
        // App title
        Label titleLabel = new Label("Create Account");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#333333"));
        
        // Registration panel with white background
        VBox registrationPanel = new VBox(15);
        registrationPanel.setAlignment(Pos.CENTER);
        registrationPanel.setPadding(new Insets(25));
        registrationPanel.setMaxWidth(350);
        registrationPanel.setStyle("-fx-background-color: white;" +
                                "-fx-background-radius: 15;" +
                                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");
        
        // Registration form fields - using appropriate field types
        TextField usernameField = createFormField("Username");
        PasswordField passwordField = createPasswordField("Password");
        PasswordField confirmPasswordField = createPasswordField("Confirm Password");
        TextField emailField = createFormField("Email");
        
        // Role selection
        Label roleLabel = new Label("I am a:");
        roleLabel.setFont(Font.font(14));
        
        ToggleGroup roleGroup = new ToggleGroup();
        RadioButton teacherRadio = new RadioButton("Teacher");
        teacherRadio.setToggleGroup(roleGroup);
        teacherRadio.setFont(Font.font(14));
        
        RadioButton studentRadio = new RadioButton("Student");
        studentRadio.setToggleGroup(roleGroup);
        studentRadio.setFont(Font.font(14));
        
        HBox roleBox = new HBox(20);
        roleBox.setAlignment(Pos.CENTER_LEFT);
        roleBox.getChildren().addAll(roleLabel, teacherRadio, studentRadio);
        
        // Experience level (initially hidden - only for students)
        VBox experienceBox = new VBox(10);
        experienceBox.setAlignment(Pos.CENTER_LEFT);
        
        Label expLabel = new Label("Experience Level:");
        expLabel.setFont(Font.font(14));
        
        ComboBox<String> expCombo = new ComboBox<>();
        expCombo.getItems().addAll("Beginner", "Intermediate", "Advanced");
        expCombo.setPromptText("Select Experience Level");
        expCombo.setPrefWidth(250);
        expCombo.setStyle("-fx-font-size: 14px;");
        
        experienceBox.getChildren().addAll(expLabel, expCombo);
        experienceBox.setVisible(false); // Initially hidden until role is selected
        
        // Enhanced role selection listeners with proper experience level handling
        studentRadio.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) { // Student selected
                experienceBox.setVisible(true);
                expCombo.setDisable(false);
                expCombo.setValue(null); // Clear any previous selection
                expCombo.setPromptText("Select Experience Level");
            }
        });
        
        teacherRadio.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) { // Teacher selected
                experienceBox.setVisible(true); // Show it
                expCombo.setValue("Advanced"); // Set to Advanced
                expCombo.setDisable(true); // Disable changes
            }
        });
        
        // Status message
        Label statusLabel = new Label();
        statusLabel.setTextFill(Color.RED);
        statusLabel.setFont(Font.font(14));
        statusLabel.setTextAlignment(TextAlignment.CENTER);
        statusLabel.setMaxWidth(250);
        
        // Register button
        Button registerButton = new Button("CREATE ACCOUNT");
        registerButton.setPrefWidth(250);
        registerButton.setPrefHeight(50);
        registerButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        registerButton.setStyle("-fx-background-color: " + PRIMARY_COLOR + ";" +
                              "-fx-text-fill: white;" +
                              "-fx-background-radius: 25;");
        
        // Login link
        Hyperlink loginLink = new Hyperlink("Already have an account? Sign in");
        loginLink.setFont(Font.font(14));
        loginLink.setTextFill(Color.web(PRIMARY_COLOR));
        
        // Add elements to registration panel
        registrationPanel.getChildren().addAll(
            usernameField,
            passwordField,
            confirmPasswordField,
            emailField,
            roleBox,
            experienceBox,
            statusLabel,
            registerButton,
            loginLink
        );
        
        // Register button action - now uses SignUpController
        registerButton.setOnAction(e -> {
            try {
                String username = usernameField.getText().trim();
                String password = passwordField.getText();
                String confirmPassword = confirmPasswordField.getText();
                String email = emailField.getText().trim();
                
                // Get selected role
                Toggle selectedRole = roleGroup.getSelectedToggle();
                String role = null;
                if (selectedRole == teacherRadio) {
                    role = "Teacher";
                } else if (selectedRole == studentRadio) {
                    role = "Student";
                }
                
                // Get experience level for students
                String experienceLevel = null;
                if (role != null && role.equals("Student")) {
                    experienceLevel = expCombo.getValue();
                } else if (role != null && role.equals("Teacher")) {
                    experienceLevel = "Advanced"; // Default for teachers
                }
                
                logger.info("Attempting to register user: " + username + 
                          ", role: " + role + 
                          ", exp: " + experienceLevel);
                
                // Use controller to validate registration data
                String validationError = signUpController.validateRegistrationData(
                    username, password, confirmPassword, email, role, experienceLevel);
                    
                if (!validationError.isEmpty()) {
                    statusLabel.setText(validationError);
                    return;
                }
                
                // Use controller to register user
                User user = signUpController.registerUser(
                    username, password, confirmPassword, email, role, experienceLevel);
                
                if (user != null) {
                    statusLabel.setText("Registration successful!");
                    statusLabel.setTextFill(Color.web(SECONDARY_COLOR));
                    
                    // Show success message and redirect to login
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Account Created");
                    alert.setHeaderText(null);
                    alert.setContentText("Your account has been created successfully!\nPlease sign in with your credentials.");
                    alert.showAndWait();
                    
                    // Switch back to login screen
                    primaryStage.setScene(createMobileLoginScene());
                } else {
                    statusLabel.setText("Registration failed. Username may already exist.");
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error during registration", ex);
                statusLabel.setText("An error occurred during registration.");
            }
        });
        
        // Login link action - switch back to login screen
        loginLink.setOnAction(e -> {
            primaryStage.setScene(createMobileLoginScene());
        });
        
        // Add title and panel to content
        content.getChildren().addAll(titleLabel, registrationPanel);
        
        // Add version text at bottom
        Label versionLabel = new Label("TuneUp v1.0");
        versionLabel.setTextFill(Color.web("#333333"));
        versionLabel.setFont(Font.font(12));
        
        // Add spacing at the bottom
        Region bottomSpacer = new Region();
        bottomSpacer.setPrefHeight(20);
        content.getChildren().addAll(bottomSpacer, versionLabel);
        
        // Set content to scroll pane
        scrollPane.setContent(content);
        
        // Add scroll pane to main container
        mainContainer.getChildren().add(scrollPane);
        
        return new Scene(mainContainer, 390, 844);
    }
    
    /**
     * Creates the home screen with mode selection buttons
     */
    private Scene createHomeScene(User user) {
        // Main container
        StackPane mainContainer = new StackPane();
        mainContainer.setStyle("-fx-background-color: " + APP_BACKGROUND_COLOR + ";");
        
        // Main content
        VBox content = new VBox(25);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(40, 20, 40, 20));
        
        // Welcome header
        Label welcomeLabel = new Label("Welcome, " + user.getUsername() + "!");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        welcomeLabel.setTextFill(Color.web("#333333"));
        
        Label subtitleLabel = new Label("What would you like to do today?");
        subtitleLabel.setFont(Font.font("Arial", 16));
        subtitleLabel.setTextFill(Color.web("#666666"));
        
        // User info panel
        HBox userInfoBox = new HBox(15);
        userInfoBox.setAlignment(Pos.CENTER);
        userInfoBox.setPadding(new Insets(10));
        userInfoBox.setMaxWidth(350);
        userInfoBox.setStyle("-fx-background-color: white;" +
                       "-fx-background-radius: 15;" +
                       "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 3);");
        
        // User icon (placeholder)
        Rectangle userIcon = new Rectangle(40, 40);
        userIcon.setArcWidth(20);
        userIcon.setArcHeight(20);
        userIcon.setFill(Color.web(PRIMARY_COLOR));
        
        // User details
        VBox userDetails = new VBox(5);
        Label usernameLabel = new Label(user.getUsername());
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        String roleText = (user.getRole() == UserType.TEACHER) ? "Teacher" : "Student";
        String expText = "";
        if (user.getRole() == UserType.STUDENT) {
            switch (user.getExperienceLevel()) {
                case BEGINNER:
                    expText = " (Beginner)";
                    break;
                case INTERMEDIATE:
                    expText = " (Intermediate)";
                    break;
                case ADVANCED:
                    expText = " (Advanced)";
                    break;
            }
        }
        
        Label roleLabel = new Label(roleText + expText);
        roleLabel.setFont(Font.font("Arial", 14));
        roleLabel.setTextFill(Color.web("#666666"));
        
        userDetails.getChildren().addAll(usernameLabel, roleLabel);
        
        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: transparent;" +
                        "-fx-text-fill: " + PRIMARY_COLOR + ";" +
                        "-fx-border-color: " + PRIMARY_COLOR + ";" +
                        "-fx-border-radius: 5;");
        
        // Add components to user info box
        userInfoBox.getChildren().addAll(userIcon, userDetails);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        userInfoBox.getChildren().addAll(spacer, logoutButton);
        
        // Mode buttons container
        VBox buttonsBox = new VBox(20);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPrefWidth(300);
        
        // Play Mode Button
        Button playButton = createModeButton("Play Mode", "♪", "#6200EE");
        
        // Create Mode Button
        Button createButton = createModeButton("Create Mode", "✎", "#03DAC5");
        
        // Song Library Button
        Button libraryButton = createModeButton("Song Library", "♫", "#FF9800");
        
        // Add buttons to container
        buttonsBox.getChildren().addAll(playButton, createButton, libraryButton);
        
        // Add components to main content
        content.getChildren().addAll(
            welcomeLabel,
            subtitleLabel,
            new Region() {{ setPrefHeight(20); }},
            userInfoBox,
            new Region() {{ setPrefHeight(30); }},
            buttonsBox
        );
        
        // Button actions
        playButton.setOnAction(e -> {
            // Enter play mode
            homeController.enterPlayMode();
            // This would typically navigate to the Play Mode UI
            // For now, show a placeholder alert
            showPlaceholderAlert("Play Mode", "Play Mode will be implemented soon!");
        });
        
        createButton.setOnAction(e -> {
            // Enter create mode
            homeController.enterCreateMode();
            // This would typically navigate to the Create Mode UI
            // For now, show a placeholder alert
            showPlaceholderAlert("Create Mode", "Create Mode will be implemented soon!");
        });
        
        libraryButton.setOnAction(e -> {
            // Enter song library mode
            homeController.enterSongLibraryMode();
            // This would typically navigate to the Song Library Mode UI
            // For now, show a placeholder alert
            showPlaceholderAlert("Song Library", "Song Library will be implemented soon!");
        });
        
        logoutButton.setOnAction(e -> {
            homeController.logout();
            primaryStage.setScene(createMobileLoginScene());
        });
        
        // Add version text at bottom
        Label versionLabel = new Label("TuneUp v1.0");
        versionLabel.setTextFill(Color.web("#666666"));
        versionLabel.setFont(Font.font(12));
        
        // Add spacing at the bottom
        Region bottomSpacer = new Region();
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);
        content.getChildren().addAll(bottomSpacer, versionLabel);
        
        // Add content to the main container
        mainContainer.getChildren().add(content);
        
        return new Scene(mainContainer, 390, 844);
    }
    
    /**
     * Creates a styled button for mode selection
     */
    private Button createModeButton(String text, String icon, String color) {
        HBox buttonContent = new HBox(15);
        buttonContent.setAlignment(Pos.CENTER_LEFT);
        
        // Icon
        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font("Arial", 24));
        iconLabel.setTextFill(Color.WHITE);
        iconLabel.setPrefWidth(40);
        iconLabel.setPrefHeight(40);
        iconLabel.setAlignment(Pos.CENTER);
        iconLabel.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-background-radius: 20;"
        );
        
        // Text
        Label textLabel = new Label(text);
        textLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        textLabel.setTextFill(Color.web("#333333"));
        
        buttonContent.getChildren().addAll(iconLabel, textLabel);
        
        // Button
        Button button = new Button();
        button.setGraphic(buttonContent);
        button.setPrefHeight(70);
        button.setPrefWidth(300);
        button.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 3);" +
            "-fx-padding: 10 15;"
        );
        
        // Add hover effect
        button.setOnMouseEntered(e -> 
            button.setStyle(
                "-fx-background-color: #f8f8f8;" +
                "-fx-background-radius: 10;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 8, 0, 0, 5);" +
                "-fx-padding: 10 15;"
            )
        );
        
        button.setOnMouseExited(e -> 
            button.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 10;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 3);" +
                "-fx-padding: 10 15;"
            )
        );
        
        return button;
    }
    
    /**
     * Shows a placeholder alert for modes that aren't implemented yet
     */
    private void showPlaceholderAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Helper method to create consistent form fields
     */
    private TextField createFormField(String promptText) {
        TextField field = new TextField();
        field.setPromptText(promptText);
        field.setPrefHeight(50);
        field.setStyle("-fx-font-size: 16px; -fx-background-radius: 8px;");
        field.setMaxWidth(300);
        field.setFocusTraversable(false);
        return field;
    }
    
    /**
     * Helper method to create consistent password fields
     */
    private PasswordField createPasswordField(String promptText) {
        PasswordField field = new PasswordField();
        field.setPromptText(promptText);
        field.setPrefHeight(50);
        field.setStyle("-fx-font-size: 16px; -fx-background-radius: 8px;");
        field.setMaxWidth(300);
        field.setFocusTraversable(false);
        return field;
    }
    
    /**
     * Main method, launches the application
     */
    public static void main(String[] args) {
        try {
            System.out.println("Starting TuneUp Mobile UI...");
            launch(TuneUpMobileUI.class, args);
        } catch (Throwable e) {
            System.err.println("Error launching application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}