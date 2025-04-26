package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tuneup.TuneUp;
import tuneup.User;

/**
 * View controller for the home screen
 */
public class HomeViewController {
    private static final Logger logger = Logger.getLogger(HomeViewController.class.getName());
    
    @FXML private Label usernameLabel;
    @FXML private Label roleLabel;
    
    private Stage stage;
    private HomeController homeController;
    private User currentUser; // Add a reference to current user
    private TuneUp facade;
    
    /**
     * Initialize with business logic controller
     */
    public void initialize(HomeController controller) {
        this.homeController = controller;
        logger.info("HomeViewController initialized");
    }
    
    /**
     * Set the application stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Display user data in the UI and store reference
     */
    public void displayUserData(User user) {
        this.currentUser = user; // Store reference to user
        
        if (user != null) {
            usernameLabel.setText(user.getUsername());
            
            String roleText = (user.getRole() == tuneup.UserType.TEACHER) ? "Teacher" : "Student";
            String expText = "";
            
            if (user.getRole() == tuneup.UserType.STUDENT) {
                if (user.getExperienceLevel() != null) {
                    expText = " (" + user.getExperienceLevel().toString() + ")";
                }
            }
            
            roleLabel.setText(roleText + expText);
            logger.info("Displayed user data for: " + user.getUsername());
        } else {
            logger.warning("Attempted to display null user data");
            usernameLabel.setText("Guest");
            roleLabel.setText("Not logged in");
        }
    }
    
    /**
     * Navigate to user profile
     */
    @FXML
    public void navigateToProfile() {
        try {
            // Make sure we have a user before trying to navigate
            if (currentUser == null) {
                logger.warning("Cannot navigate to profile: User is null");
                showError("Session Error", "User session not found. Please log in again.");
                handleLogout();
                return;
            }
            
            logger.info("Navigating to profile for: " + currentUser.getUsername());
            
            // Load the profile screen using resource path
            URL fxmlUrl = getClass().getResource("/fxml/profile.fxml");
            URL cssUrl = getClass().getResource("/css/styles.css");
            
            if (fxmlUrl == null) {
                throw new IOException("Cannot find profile.fxml resource");
            }
            
            // Load the FXML
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            
            // Get controller and initialize it
            ProfileViewController controller = loader.getController();
            controller.initialize(homeController.getFacade(), currentUser); // Pass the stored user
            controller.setStage(stage);
            
            // Create and set scene
            Scene scene = new Scene(root, 390, 700);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
            
            stage.setScene(scene);
            stage.setTitle("TuneUp Profile");
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error navigating to profile", ex);
            showError("Navigation Error", "Could not navigate to profile screen");
        }
    }
    
    // Other methods (handleLogout, showError, etc.) remain the same...

    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("TuneUp Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    /**
 * Handle logout button click
 */
@FXML
public void handleLogout() {
    try {
        // Log user out via controller
        if (homeController != null) {
            homeController.logout();
        }
        
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
        controller.initialize(homeController != null ? homeController.getFacade() : null);
        controller.setStage(stage);
        
        // Create and set scene
        Scene scene = new Scene(root, 390, 600);
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
        
        stage.setScene(scene);
        stage.setTitle("TuneUp Login");
        
    } catch (Exception ex) {
        logger.log(Level.SEVERE, "Error during logout", ex);
        showError("Navigation Error", "Could not return to login screen");
    }
}
    // Navigation stubs for other screens
    @FXML
    public void navigateToPractice() {
        try {
            // Load the signup screen using resource path
            URL fxmlUrl = getClass().getResource("/fxml/practicemode.fxml");
            URL cssUrl = getClass().getResource("/css/styles.css");
            
            if (fxmlUrl == null) {
                throw new IOException("Cannot find practicemode.fxml resource");
            }
            
            // Load the FXML
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            
            // Get controller and initialize it
            PracticeModeViewController controller = loader.getController();
            controller.initialize(facade);
            controller.setStage(stage);
            
            // Create and set scene
            Scene scene = new Scene(root, 390, 700);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
            
            stage.setScene(scene);
            stage.setTitle("Practice Mode");
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error loading practice mode", ex);
            // statusLabel.setText("Error loading signup screen");
        }
    }
    
    @FXML
    public void navigateToCreate() {
        try {
            // Load the create mode setup screen
            URL fxmlUrl = getClass().getResource("/fxml/createmode_setup.fxml");
            URL cssUrl = getClass().getResource("/css/styles.css");
            
            if (fxmlUrl == null) {
                throw new IOException("Cannot find createmode_setup.fxml");
            }
            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            
            CreateModeViewController controller = loader.getController();
            controller.initialize(homeController.getFacade(), currentUser);
            controller.setStage(stage);
            
            Scene scene = new Scene(root, 390, 600);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
            
            stage.setScene(scene);
            stage.setTitle("TuneUp - Create Mode Setup");
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error loading create mode setup", ex);
            showError("Navigation Error", "Could not navigate to Create Mode");
        }
    }
    
    @FXML
    public void navigateToSongLibrary() {
        try {
            // Load the signup screen using resource path
            URL fxmlUrl = getClass().getResource("/fxml/songlibrary.fxml");
            URL cssUrl = getClass().getResource("/css/styles.css");
            
            if (fxmlUrl == null) {
                throw new IOException("Cannot find songlibrary.fxml resource");
            }
            
            // Load the FXML
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            
            // Get controller and initialize it
            SongLibraryViewController controller = loader.getController();
            controller.initialize(facade, currentUser);
            controller.setStage(stage);
            
            // Create and set scene
            Scene scene = new Scene(root, 390, 700);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
            
            stage.setScene(scene);
            stage.setTitle("Song Library Mode");
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error loading song library mode", ex);
            // statusLabel.setText("Error loading signup screen");
        }
    }
    
    private void showNotImplemented(String feature) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Feature Not Available");
        alert.setHeaderText(feature + " Coming Soon");
        alert.setContentText("This feature is not implemented yet.");
        alert.showAndWait();
    }
}