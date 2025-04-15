package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tuneup.ExperienceLevel;
import tuneup.TuneUp;
import tuneup.User;
import tuneup.UserType;

public class SignUpViewController {
    private static final Logger logger = Logger.getLogger(SignUpViewController.class.getName());
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField emailField;
    @FXML private RadioButton studentRadio;
    @FXML private RadioButton teacherRadio;
    @FXML private ToggleGroup roleGroup;
    @FXML private ComboBox<String> experienceLevelComboBox;
    @FXML private VBox experienceLevelContainer;
    @FXML private Label statusLabel;
    
    private Stage stage;
    private TuneUp facade;
    private SignUpController signUpController;
    
    /**
     * Initialize controller with application facade
     */
    public void initialize(TuneUp facade) {
        this.facade = facade;
        this.signUpController = new SignUpController(facade);
        
        // Initialize combo box with experience levels
        experienceLevelComboBox.setItems(FXCollections.observableArrayList(
            "Beginner", "Intermediate", "Advanced"
        ));
        experienceLevelComboBox.setValue("Beginner");
        
        // Add listener to role selection to show/hide experience level
        roleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue != null) {
                    boolean isStudent = studentRadio.isSelected();
                    experienceLevelContainer.setVisible(isStudent);
                    experienceLevelContainer.setManaged(isStudent);
                }
            }
        });
        
        logger.info("SignUpViewController initialized");
    }
    
    /**
     * Set the application stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Handle sign up button click
     */
    @FXML
    public void handleSignUp() {
        try {
            // Get form values
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String email = emailField.getText().trim();
            String role = teacherRadio.isSelected() ? "Teacher" : "Student";
            String experienceLevel = experienceLevelComboBox.getValue();
            
            // Validate input using controller
            String validationError = signUpController.validateRegistrationData(
                username, password, confirmPassword, email, role, experienceLevel
            );
            
            if (!validationError.isEmpty()) {
                statusLabel.setText(validationError);
                statusLabel.setTextFill(Color.RED);
                return;
            }
            
            // Register user
            User newUser = signUpController.registerUser(
                username, password, confirmPassword, email, role, experienceLevel
            );
            
            if (newUser != null) {
                statusLabel.setText("Registration successful!");
                statusLabel.setTextFill(Color.GREEN);
                
                // Slight delay before redirecting to login
                Thread.sleep(1500);
                
                // Navigate back to login
                loadLoginScreen();
            } else {
                statusLabel.setText("Registration failed. Username may already exist.");
                statusLabel.setTextFill(Color.RED);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error during registration", ex);
            statusLabel.setText("An error occurred during registration");
            statusLabel.setTextFill(Color.RED);
        }
    }
    
    /**
     * Navigate back to login screen
     */
    @FXML
    public void navigateToLogin() {
        loadLoginScreen();
    }
    
    /**
 * Load login screen
 */
private void loadLoginScreen() {
    try {
        // Load login screen using resource path
        URL fxmlUrl = getClass().getResource("/fxml/login.fxml");
        URL cssUrl = getClass().getResource("/css/styles.css");
        
        if (fxmlUrl == null) {
            throw new IOException("Cannot find login.fxml resource");
        }
        
        // Load the FXML
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        
        // Get controller and initialize it
        LoginViewController controller = loader.getController();
        controller.initialize(facade);
        controller.setStage(stage);
        
        // Create and set scene
        Scene scene = new Scene(root, 390, 600);
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
        
        stage.setScene(scene);
        stage.setTitle("TuneUp Login");
        
    } catch (Exception ex) {
        logger.log(Level.SEVERE, "Error loading login screen", ex);
        statusLabel.setText("Error returning to login screen");
    }
}
}