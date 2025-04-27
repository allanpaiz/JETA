package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import tuneup.DataWriter;
import tuneup.PianoStrategy;
import tuneup.Song;
import tuneup.SongLibrary;
import tuneup.TuneUp;
import tuneup.User;

public class CreateModeViewController {
    private static final Logger logger = Logger.getLogger(CreateModeViewController.class.getName());
    
    // Setup Screen FXML elements
    @FXML private TextField songTitleField;
    @FXML private Slider tempoSlider;
    @FXML private Label tempoValueLabel;
    @FXML private ComboBox<String> timeSignatureCombo;
    
    // Composition Screen FXML elements
    @FXML private Label songTitleDisplay;
    @FXML private Label tempoDisplay;
    @FXML private Label timeSignatureDisplay;
    @FXML private ListView<String> measureNotesListView;
    @FXML private ListView<String> songMeasuresListView;
    @FXML private Label measureCountLabel;
    @FXML private Label statusLabel;
    @FXML private ImageView quarterNote;
    
    private Timeline noteAnimation;
    private Stage stage;
    private TuneUp facade;
    private User currentUser;
    private PianoStrategy piano;
    private ObservableList<String> measureNotes = FXCollections.observableArrayList();
    private List<List<String>> allMeasures = new ArrayList<>();
    private String currentTimeSignature = "4/4";
    private int beatsPerMeasure = 4;
    private int currentTempo = 120;

    public void initialize(TuneUp facade, User user) {
        this.facade = facade;
        this.currentUser = user;
        this.piano = new PianoStrategy();
        
        if (timeSignatureCombo != null) {
            setupInitialScreen();
        }
        
        logger.info("CreateModeViewController initialized");
    }

    private void setupInitialScreen() {
        timeSignatureCombo.setItems(FXCollections.observableArrayList("4/4", "3/4", "6/8"));
        timeSignatureCombo.setValue("4/4");
        
        tempoSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            currentTempo = newVal.intValue();
            tempoValueLabel.setText(currentTempo + " BPM");
        });
    }

    public void initializeComposition(String title, int tempo, String timeSignature, 
                                    TuneUp facade, User user) {
        this.facade = facade;
        this.currentUser = user;
        this.currentTempo = tempo;
        this.currentTimeSignature = timeSignature;
        this.beatsPerMeasure = getBeatsPerMeasure(timeSignature);
        this.piano = new PianoStrategy();
        
        songTitleDisplay.setText(title);
        tempoDisplay.setText(tempo + " BPM");
        timeSignatureDisplay.setText(timeSignature);
        measureNotesListView.setItems(measureNotes);
        songMeasuresListView.getItems().clear();
        updateMeasureCountLabel();
        
        logger.info("Composition screen initialized");
    }

    @FXML
    public void continueToComposition() {
        String title = songTitleField.getText().trim();
        if (title.isEmpty()) {
            showError("Missing Title", "Please enter a song title");
            return;
        }
        
        try {
            cleanup();
            
            URL fxmlUrl = getClass().getResource("/fxml/createmode_compose.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            
            CreateModeViewController controller = loader.getController();
            controller.initializeComposition(title, currentTempo, 
                timeSignatureCombo.getValue(), facade, currentUser);
            controller.setStage(stage);
            
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.setTitle("TuneUp - Creating: " + title);
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error loading composition interface", ex);
            showError("Navigation Error", "Could not load composition interface");
        }
    }

    @FXML
    public void onPianoKeyPressed(javafx.scene.input.MouseEvent event) {
        Button button = (Button) event.getSource();
        String note = button.getText();
        addAndPlayNote(note);
    }

    private void addAndPlayNote(String note) {
        if (measureNotes.size() < beatsPerMeasure) {
            piano.playNote(note);
            measureNotes.add(note);
            updateMeasureCountLabel();
            showNoteOnStaff(note);
            showStatus("Added note: " + note, false);
        } else {
            showStatus("Measure is full", true);
        }
    }

    private void showNoteOnStaff(String note) {
        if (noteAnimation != null) {
            noteAnimation.stop();
        }
        
        double yPos = switch (note) {
            case "C4" -> 18.0;
            case "D4" -> 10.0;
            case "E4" -> 2.0;
            case "F4" -> -6.0;
            case "G4" -> -14.0;
            case "A4" -> -22.0;
            case "B4" -> -30.0;
            case "C5" -> -38.0;
            default -> 18.0;
        };
        
        quarterNote.setLayoutY(yPos);
        quarterNote.setOpacity(1.0);
        
        noteAnimation = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(quarterNote.opacityProperty(), 1.0)),
            new KeyFrame(Duration.seconds(1), new KeyValue(quarterNote.opacityProperty(), 0.0))
        );
        
        noteAnimation.play();
    }

    @FXML
    public void playMeasure() {
        if (!measureNotes.isEmpty()) {
            playNoteSequence(measureNotes);
        }
    }

    @FXML
    public void playSong() {
        if (allMeasures.isEmpty()) {
            showStatus("No measures to play", true);
            return;
        }
        
        List<String> allNotes = new ArrayList<>();
        for (List<String> measure : allMeasures) {
            allNotes.addAll(measure);
        }
        
        playNoteSequence(allNotes);
    }

    private void playNoteSequence(List<String> notes) {
        new Thread(() -> {
            for (String note : notes) {
                Platform.runLater(() -> {
                    piano.playNote(note);
                    showNoteOnStaff(note);
                });
                try {
                    Thread.sleep((60000 / currentTempo) - 50);
                    Platform.runLater(() -> piano.stop());
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }).start();
    }

    @FXML
    public void addMeasureToSong() {
        if (measureNotes.isEmpty()) {
            showStatus("Cannot add empty measure", true);
            return;
        }
        
        allMeasures.add(new ArrayList<>(measureNotes));
        songMeasuresListView.getItems().add(String.join(" ", measureNotes));
        measureNotes.clear();
        updateMeasureCountLabel();
        showStatus("Measure added to song", false);
    }

    @FXML
    public void clearMeasure() {
        measureNotes.clear();
        updateMeasureCountLabel();
        showStatus("Measure cleared", false);
    }

    @FXML
    public void saveSong() {
        if (allMeasures.isEmpty()) {
            showStatus("Cannot save empty song", true);
            return;
        }
        
        try {
            List<String> allNotes = new ArrayList<>();
            for (List<String> measure : allMeasures) {
                allNotes.addAll(measure);
            }

            Song newSong = new Song();
            newSong.setTitle(songTitleDisplay.getText().trim());
            newSong.setCreatorId(currentUser.getId());
            newSong.setArtistName(currentUser.getUsername());
            newSong.setNotes(allNotes);
            newSong.setTempo(currentTempo);
            newSong.setTimeSignature(currentTimeSignature);
            newSong.setId(UUID.randomUUID().toString());

            List<Song> songs = SongLibrary.getSongLibrary();
            if (songs == null) songs = new ArrayList<>();
            songs.add(newSong);
            DataWriter.saveSongs(songs);

            showStatus("Song saved successfully!", false);
            Platform.runLater(() -> {
                try {
                    Thread.sleep(1500);
                    navigateToHome();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error saving song", e);
            showStatus("Error saving song", true);
        }
    }

    private void updateMeasureCountLabel() {
        if (measureCountLabel != null) {
            measureCountLabel.setText(String.format("(%d/%d beats)", 
                measureNotes.size(), beatsPerMeasure));
        }
    }

    private void showStatus(String message, boolean isError) {
        if (statusLabel != null) {
            statusLabel.setText(message);
            statusLabel.setTextFill(isError ? Color.RED : Color.GREEN);
        }
    }

    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private int getBeatsPerMeasure(String timeSignature) {
        return switch (timeSignature) {
            case "3/4" -> 3;
            case "6/8" -> 6;
            default -> 4;
        };
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void navigateToHome() {
        try {
            cleanup();
            
            URL fxmlUrl = getClass().getResource("/fxml/home.fxml");
            URL cssUrl = getClass().getResource("/css/styles.css");
            
            if (fxmlUrl == null) {
                throw new IOException("Cannot find home.fxml");
            }
            
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();
            
            HomeViewController controller = loader.getController();
            controller.initialize(new HomeController(facade));
            controller.setStage(stage);
            controller.displayUserData(currentUser);
            
            // Create scene with correct size for home screen
            Scene scene = new Scene(root, 390, 600);
            
            // Apply CSS styles
            if (cssUrl != null) {
                scene.getStylesheets().clear();
                scene.getStylesheets().add(cssUrl.toExternalForm());
            }
            
            // Set scene and resize window
            stage.setScene(scene);
            stage.setTitle("TuneUp Home");
            stage.sizeToScene();
            stage.centerOnScreen();
            
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error returning to home", ex);
            showError("Navigation Error", "Could not return to home screen");
        }
    }
    public void cleanup() {
        // Stop and cleanup piano
        if (piano != null) {
            piano.stop();
            piano.close();
            piano = null;
        }
    
        // Stop any running animations
        if (noteAnimation != null) {
            noteAnimation.stop();
            noteAnimation = null;
        }
    
        // Clear collections
        if (measureNotes != null) {
            measureNotes.clear();
        }
        if (allMeasures != null) {
            allMeasures.clear();
        }
        if (songMeasuresListView != null && songMeasuresListView.getItems() != null) {
            songMeasuresListView.getItems().clear();
        }
    
        logger.info("CreateModeViewController cleanup completed");
    }
}