package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tuneup.TuneUp;
import tuneup.User;

public class LoginViewController {
    private static final Logger logger = Logger.getLogger(LoginViewController.class.getName());
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;
    
    private Stage stage;
    private TuneUp facade;
    private LoginController loginController;
    
    /**
     * Initialize controller with application facade
     */
    public void initialize(TuneUp facade) {
        this.facade = facade;
        this.loginController = new LoginController(facade);
        logger.info("LoginViewController initialized");
    }
    
    /**
     * Set the application stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Handle login button click
     */
    @FXML
    public void handleLogin() {
        try {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            
            // Use LoginController to validate format
            String validationError = loginController.validateCredentials(username, password);
            if (!validationError.isEmpty()) {
                statusLabel.setText(validationError);
                statusLabel.setTextFill(Color.RED);
                return;
            }
            
            // Authenticate using LoginController
            User user = loginController.authenticateUser(username, password);
            
            if (user != null) {
                statusLabel.setText("Login successful!");
                statusLabel.setTextFill(Color.GREEN);
                
                // Navigate to home screen with authenticated user
                loadHomeScreen(user);
            } else {
                statusLabel.setText("Invalid username or password");
                statusLabel.setTextFill(Color.RED);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error during login", ex);
            statusLabel.setText("An error occurred during login");
            statusLabel.setTextFill(Color.RED);
        }
    }
    
    /**
 * Navigate to registration screen
 */
@FXML
public void navigateToRegistration() {
    try {
        // Load the signup screen using resource path
        URL fxmlUrl = getClass().getResource("/fxml/signup.fxml");
        URL cssUrl = getClass().getResource("/css/styles.css");
        
        if (fxmlUrl == null) {
            throw new IOException("Cannot find signup.fxml resource");
        }
        
        // Load the FXML
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        
        // Get controller and initialize it
        SignUpViewController controller = loader.getController();
        controller.initialize(facade);
        controller.setStage(stage);
        
        // Create and set scene
        Scene scene = new Scene(root, 390, 700);
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
        
        stage.setScene(scene);
        stage.setTitle("TuneUp Sign Up");
        
    } catch (IOException ex) {
        logger.log(Level.SEVERE, "Error loading signup screen", ex);
        statusLabel.setText("Error loading signup screen");
    }
}

/**
 * Load home screen with user data
 */
private void loadHomeScreen(User user) {
    try {
        // Load the home screen using resource path
        URL fxmlUrl = getClass().getResource("/fxml/home.fxml");
        URL cssUrl = getClass().getResource("/css/styles.css");
        
        if (fxmlUrl == null) {
            throw new IOException("Cannot find home.fxml resource");
        }
        
        // Load the FXML
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        
        // Get controller and initialize it
        HomeViewController controller = loader.getController();
        controller.initialize(new HomeController(facade));
        controller.setStage(stage);
        controller.displayUserData(user); // Explicitly pass user here
        
        // Create and set scene
        Scene scene = new Scene(root, 390, 600);
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
        
        stage.setScene(scene);
        stage.setTitle("TuneUp Home");
        
    } catch (IOException ex) {
        logger.log(Level.SEVERE, "Error loading home screen", ex);
        statusLabel.setText("Error loading home screen");
    }
}
}