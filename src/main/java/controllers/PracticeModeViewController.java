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

public class PracticeModeViewController {
    private static final Logger logger = Logger.getLogger(PracticeModeViewController.class.getName());

    private Stage stage;
    private PracticeModeController practiceModeController;
    private TuneUp facade;
    
    /**
     * Initialize with business logic controller
     */
    public void initialize(TuneUp facade) {
        this.facade = facade;
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
        // try {
        //     // Load the signup screen using resource path
        //     URL fxmlUrl = getClass().getResource("/fxml/piano.fxml");
        //     URL cssUrl = getClass().getResource("/css/styles.css");
            
        //     if (fxmlUrl == null) {
        //         throw new IOException("Cannot find piano.fxml resource");
        //     }
            
        //     // Load the FXML
        //     FXMLLoader loader = new FXMLLoader(fxmlUrl);
        //     Parent root = loader.load();
            
        //     // Get controller and initialize it
        //     PianoViewController controller = loader.getController();
        //     controller.initialize(facade);
        //     controller.setStage(stage);
            
        //     // Create and set scene
        //     Scene scene = new Scene(root, 390, 700);
        //     if (cssUrl != null) {
        //         scene.getStylesheets().add(cssUrl.toExternalForm());
        //     }
            
        //     stage.setScene(scene);
        //     stage.setTitle("Piano");
            
        // } catch (IOException ex) {
        //     logger.log(Level.SEVERE, "Error loading playable piano", ex);
        //     // statusLabel.setText("Error loading signup screen");
        // }
        showNotImplemented("Piano");
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
}
