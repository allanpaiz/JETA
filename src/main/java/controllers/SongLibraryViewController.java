package controllers;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;


import tuneup.Song;
import tuneup.SongLibrary;
import tuneup.TuneUp;

public class SongLibraryViewController {
    private static final Logger logger = Logger.getLogger(SignUpViewController.class.getName());
    
    @FXML private TextField searchBar;
    @FXML private Label statusLabel;
    @FXML private VBox carouselContainer;
    @FXML private VBox libraryContainer;
    
    private Stage stage;
    private TuneUp facade;
    private SongLibraryController songLibraryController;
    
    public void initialize(TuneUp facade) {
        this.facade = facade;
        this.songLibraryController = new SongLibraryController(facade);
        
        libraryContainer.setVisible(false);
        
        logger.info("SongLibraryController initialized");
    }
    
    /**
     * Set the application stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void browseAllSongs() {
        carouselContainer.setVisible(false);
        libraryContainer.setVisible(true);

        libraryContainer.getChildren().clear();
        Label titleLabel = new Label("All Songs");
        titleLabel.getStyleClass().add("sl-title");
        libraryContainer.getChildren().add(titleLabel);

        List<Song> songs = SongLibrary.getSongLibrary();

        for (Song song : songs) {
            HBox songRow = new HBox(10);
            songRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            
            Label songLabel = new Label(song.getTitle());
            songLabel.setStyle("-fx-font-size: 14px; -fx-padding: 2px;");
            
            Button playButton = new Button("▷");
            playButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            playButton.setOnAction(e -> {
                System.out.println("play");
            });
            
            Button saveButton = new Button("💾");
            saveButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
            saveButton.setOnAction(e -> {
                System.out.println("save");
            });
            
            songRow.getChildren().addAll(songLabel, playButton, saveButton);
            libraryContainer.getChildren().add(songRow);
        }
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
            
            // Create and set scene
            Scene scene = new Scene(root, 390, 600);
            if (cssUrl != null) {
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
            
            stage.setScene(scene);
            stage.setTitle("TuneUp Home");
            
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error loading home screen", ex);
        }
    }

}