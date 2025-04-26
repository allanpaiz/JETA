package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tuneup.TuneUp;
import tuneup.User;

public class PianoViewController {
    private static final Logger logger = Logger.getLogger(PianoController.class.getName());

    @FXML private Button A4;
    @FXML private Button B4;
    @FXML private Button C4;
    @FXML private Button D4;
    @FXML private Button E4;
    @FXML private Button F4;
    @FXML private Button G4;
    // @FXML private Button A5;
    // @FXML private Button B5;
    @FXML private Button C5;
    @FXML private ImageView quarterNote;

    private Stage stage;
    private PianoController pianoController;
    private TuneUp facade;
    private User currentUser;
    
    /**
     * Initialize with business logic controller
     */
    public void initialize(TuneUp facade, User currentUser) {
        this.facade = facade;
        this.currentUser = currentUser;
        this.pianoController = new PianoController(facade);
        quarterNote.setVisible(false);
        
        logger.info("PianoController initialized");
    }
    
    /**
     * Set the application stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void navigateBack() {
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
            controller.initialize(facade, currentUser);
            controller.setStage(stage);
            
            // Create and set scene
            Scene scene = new Scene(root, 390, 600);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
            
            stage.setScene(scene);
            stage.setTitle("Practice Mode");
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error loading Practice Mode", ex);
            // statusLabel.setText("Error loading signup screen");
        }
    }

    @FXML
    public void playA4Note() {
        pianoController.playNote("A4");
        
        quarterNote.setY(-18);
        quarterNote.setVisible(true);
        quarterNote.setRotate(0);
    }
    @FXML
    public void playB4Note() { 
        pianoController.playNote("B4");
        
        quarterNote.setY(14);
        quarterNote.setVisible(true);
        quarterNote.setRotate(180);
    }
    @FXML
    public void playC4Note() { 
        pianoController.playNote("C4");
        
        quarterNote.setY(5);
        quarterNote.setVisible(true);
        quarterNote.setRotate(180);
    }
    @FXML
    public void playD4Note() { 
        pianoController.playNote("D4");
        
        quarterNote.setY(-5);
        quarterNote.setVisible(true);
        quarterNote.setRotate(180);
    }
    @FXML
    public void playE4Note() {
        pianoController.playNote("E4");
        
        quarterNote.setY(-13);
        quarterNote.setVisible(true);
        quarterNote.setRotate(180);
    }
    @FXML
    public void playF4Note() {
        pianoController.playNote("F4");
        
        quarterNote.setY(-22);
        quarterNote.setVisible(true);
        quarterNote.setRotate(180);
    }
    @FXML
    public void playG4Note() {
        pianoController.playNote("G4");
        
        quarterNote.setY(-12);
        quarterNote.setVisible(true);
        quarterNote.setRotate(0);
    }
    @FXML
    public void playC5Note() {
        pianoController.playNote("C5");
        
        quarterNote.setY(5);
        quarterNote.setVisible(true);
        quarterNote.setRotate(180);
    }

}