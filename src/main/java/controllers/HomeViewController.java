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
import tuneup.User;

public class HomeViewController {
    private static final Logger logger = Logger.getLogger(HomeViewController.class.getName());
    
    @FXML private Label usernameLabel;
    @FXML private Label roleLabel;
    
    private Stage stage;
    private HomeController homeController;
    
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
     * Display user data in the UI
     */
    public void displayUserData(User user) {
        if (user != null) {
            usernameLabel.setText(user.getUsername());
            
            String roleText = (user.getRole() == tuneup.UserType.TEACHER) ? "Teacher" : "Student";
            String expText = "";
            
            if (user.getRole() == tuneup.UserType.STUDENT) {
                // Add experience level if available
                if (user.getExperienceLevel() != null) {
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
            }
            
            roleLabel.setText(roleText + expText);
        }
    }
    
    /**
 * Handle logout button click
 */
@FXML
public void handleLogout() {
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
        controller.initialize(homeController.getFacade());
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
    
    /**
     * Show error alert
     */
    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("TuneUp Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    @FXML
    public void navigateToPractice() {
        showNotImplemented("Play Mode");
    }
    
    @FXML
    public void navigateToCreate() {
        showNotImplemented("Create Mode");
    }
    
    @FXML
    public void navigateToSongLibrary() {
        showNotImplemented("Song Library");
    }
    
    private void showNotImplemented(String feature) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Feature Not Available");
        alert.setHeaderText(feature + " Coming Soon");
        alert.setContentText("This feature is not implemented yet.");
        alert.showAndWait();
    }
}