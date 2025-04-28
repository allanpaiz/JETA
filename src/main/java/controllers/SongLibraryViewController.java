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
import tuneup.User;

/**
 * View Controller Class for Song Library
 *
 * @author allanpaiz
 */
public class SongLibraryViewController {
    private static final Logger logger = Logger.getLogger(SignUpViewController.class.getName());

    @FXML private TextField searchBar;
    @FXML private Label statusLabel;
    @FXML private VBox carouselContainer;
    @FXML private VBox libraryContainer;

    private Stage stage;
    private TuneUp facade;
    private SongLibraryController songLibraryController;
    private User currentUser;

    /**
     * Initialize method for UI
     *
     * @param facade TuneUp
     * @param currentUser User
     */
    public void initialize(TuneUp facade, User currentUser) {
        this.facade = facade;
        this.currentUser = currentUser;
        this.songLibraryController = new SongLibraryController(facade);

        libraryContainer.setVisible(false);

        logger.info("SongLibraryController initialized");
    }

    /**
     * Sets application stage 
     *
     * @param stage Stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     *  Browse All Songs Button functionality
     */
    @FXML
    public void browseAllSongs() {
        carouselContainer.getChildren().clear();
        carouselContainer.setVisible(false);
        libraryContainer.setVisible(true);

        libraryContainer.getChildren().clear();
        Label titleLabel = new Label("All Songs");
        titleLabel.getStyleClass().add("sl-title");
        libraryContainer.getChildren().add(titleLabel);

        List<Song> songs = songLibraryController.getSongs("-ALL-");
        displayLibrary(songs);

    }

    /**
     *  Search Songs functionality
     */
    @FXML
    public void browseFilteredSongs() {
        String searchText = searchBar.getText().trim();

        if (searchText.isEmpty()) {
            browseAllSongs();
            return;
        }

        carouselContainer.getChildren().clear();
        carouselContainer.setVisible(false);
        libraryContainer.setVisible(true);

        libraryContainer.getChildren().clear();
        Label titleLabel = new Label("Search : " + searchText);
        titleLabel.getStyleClass().add("sl-title");
        libraryContainer.getChildren().add(titleLabel);

        List<Song> songs = songLibraryController.getSongs(searchText);
        displayLibrary(songs);
    }

    /**
     * Home button functionality
     */
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

    /**
     * Profile button functionality
     */
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
            controller.initialize(songLibraryController.getFacade(), currentUser);
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

    /**
     * Helper function that displays all songs or filtered library
     *
     * @param songs List<Song>
     */
    public void displayLibrary(List<Song> songs) {
        for (Song song : songs) {
            HBox songRow = new HBox();
            songRow.getStyleClass().add("sl-list-row");
            songRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            songRow.setMaxWidth(Double.MAX_VALUE);
            
            VBox songInfo = new VBox();
            songInfo.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            Label songLabel = new Label(song.getTitle() + " by " + song.getArtistName());
            songInfo.getChildren().addAll(songLabel);
            
            HBox buttonContainer = new HBox(5);
            buttonContainer.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
            
            Button playButton = new Button("▶");
            playButton.getStyleClass().add("sl-play-button");
            playButton.setOnAction(e -> {songLibraryController.playSong(song);});
            
            Button saveButton = new Button("💾");
            saveButton.getStyleClass().add("sl-save-button");
            saveButton.setOnAction(e -> {songLibraryController.printSongToFile(song);});
            
            buttonContainer.getChildren().addAll(playButton, saveButton);
            HBox.setHgrow(songInfo, javafx.scene.layout.Priority.ALWAYS);
            songRow.getChildren().addAll(songInfo, buttonContainer);
            libraryContainer.getChildren().add(songRow);
        }
    }
}
