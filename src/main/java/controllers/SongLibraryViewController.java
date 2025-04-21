package controllers;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import tuneup.TuneUp;

public class SongLibraryViewController {
    private static final Logger logger = Logger.getLogger(SignUpViewController.class.getName());
    
    @FXML private TextField searchBar;
    @FXML private Label statusLabel;
    
    private Stage stage;
    private TuneUp facade;
    private SongLibraryController songLibraryController;
    
    public void initialize(TuneUp facade) {
        this.facade = facade;
        this.songLibraryController = new SongLibraryController(facade);
        
        logger.info("SongLibraryController initialized");
    }
    
    /**
     * Set the application stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}