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

public class PianoViewController {
    private static final Logger logger = Logger.getLogger(PianoController.class.getName());

    private Stage stage;
    private PianoController pianoController;
    private TuneUp facade;
    
    /**
     * Initialize with business logic controller
     */
    public void initialize(TuneUp facade) {
        this.facade = facade;
        this.pianoController = new PianoController(facade);
        
        logger.info("PianoController initialized");
    }
    
    /**
     * Set the application stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}