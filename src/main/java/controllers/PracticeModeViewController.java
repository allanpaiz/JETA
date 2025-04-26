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
import tuneup.TuneUp;
import tuneup.User;

public class PracticeModeViewController {
    private static final Logger logger = Logger.getLogger(PracticeModeViewController.class.getName());

    private Stage stage;
    private PracticeModeController practiceModeController;
    private TuneUp facade;
    private User currentUser;
    
    /**
     * Initialize with business logic controller
     */
    public void initialize(TuneUp facade, User currentUser) {
        this.facade = facade;
        this.currentUser = currentUser;
        this.practiceModeController = new PracticeModeController(facade);
        
        logger.info("PracticeModeController initialized");
    }
    
    /**
     * Set the application stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void navigateToPiano() {
        try {
            // Load the signup screen using resource path
            URL fxmlUrl = getClass().getResource("/fxml/piano.fxml");
            URL cssUrl = getClass().getResource("/css/styles.css");
            
            if (fxmlUrl == null) {
                throw new IOException("Cannot find piano.fxml resource");
            }
            
            // Load the FXML
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            
            // Get controller and initialize it
            PianoViewController controller = loader.getController();
            controller.initialize(facade, currentUser);
            controller.setStage(stage);
            
            // Create and set scene
            Scene scene = new Scene(root, 600, 390);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
            
            stage.setScene(scene);
            stage.setTitle("Piano");
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error loading playable piano", ex);
            // statusLabel.setText("Error loading signup screen");
        }
    }
    @FXML
    public void navigateToGuitar() {
        showNotImplemented("Guitar");
    }
    @FXML
    public void navigateToUkelele() {
        showNotImplemented("Ukelele");
    }

    private void showNotImplemented(String feature) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Feature Not Available");
        alert.setHeaderText(feature + " Coming Soon");
        alert.setContentText("This feature is not implemented yet.");
        alert.showAndWait();
    }

    @FXML
    public void returnHome() {
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
        controller.displayUserData(currentUser);
        
        // Create and set scene
        Scene scene = new Scene(root, 390, 600);
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }
        
        stage.setScene(scene);
        stage.setTitle("TuneUp Home");
        
    } catch (IOException ex) {
        logger.log(Level.SEVERE, "Error navigating to home", ex);
    }
    }

    @FXML
    public void navigateToProfile() {
        try {            
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
            controller.initialize(practiceModeController.getFacade(), currentUser);
            controller.setStage(stage);
            
            // Create and set scene
            Scene scene = new Scene(root, 390, 600);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
            
            stage.setScene(scene);
            stage.setTitle("TuneUp Profile");
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error navigating to profile", ex);
        }
    }
}
