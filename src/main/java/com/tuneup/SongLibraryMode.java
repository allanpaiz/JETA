package com.tuneup;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// import java.util.List;

public class SongLibraryMode implements Mode {
    private User userProfile;
    private ProfileManager profileManager;
    private Stage primaryStage;
    private TuneUpUI tuneUpUI;

    /**
     * Constructor for SongLibraryMode sets data members
     * 
     * @param userProfile User 
     * @param profileManager ProfileManager
     * @param primaryStage Stage
     * @param tuneUpUI TuneUpUI
     */
    public SongLibraryMode(User userProfile, ProfileManager profileManager, Stage primaryStage, TuneUpUI tuneUpUI) {
        this.userProfile = userProfile;
        this.profileManager = profileManager;
        this.primaryStage = primaryStage;
        this.tuneUpUI = tuneUpUI;
    }

    /**
     * Handle method
     * This method is called when the Song Library button is clicked
     */
    @Override
    public void handle() {
        primaryStage.setScene(createSongLibraryScene());
    }

    /**
     * Creates the Song Library scene
     * 
     * @return Scene
     */
    private Scene createSongLibraryScene() {
        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(20));

        // Components
        Label title = new Label("Song Library");
        Button viewLibraryBtn = new Button("View Library");
        Button addSongBtn = new Button("Add Song");
        Button removeSongBtn = new Button("Remove Song");
        Button backBtn = new Button("Back to Main Menu");
        Label statusLabel = new Label();

        // Event handlers
        viewLibraryBtn.setOnAction(e -> viewLibrary(layout, statusLabel));
        addSongBtn.setOnAction(e -> addSong(layout, statusLabel));
        removeSongBtn.setOnAction(e -> removeSong(layout, statusLabel));

        // Back button, this needs to be the same for all modes
        backBtn.setOnAction(e -> primaryStage.setScene(new TuneUpUI().createMainMenuScene(primaryStage)));

        // Add all the elements to the layout
        layout.getChildren().addAll(title, viewLibraryBtn, addSongBtn, removeSongBtn, backBtn, statusLabel);
        
        // Return the scene
        return new Scene(layout, 400, 300);
    }

    private void viewLibrary(VBox layout, Label statusLabel) {
        // TODO: Placeholder
        statusLabel.setText("TODO: View Library");
    }

    private void addSong(VBox layout, Label statusLabel) {
        // Clear previous layout
        layout.getChildren().clear();

        // Components
        TextField titleField = new TextField();
        titleField.setPromptText("Song Title");
        TextField artistField = new TextField();
        artistField.setPromptText("Artist");
        TextField filePathField = new TextField();
        filePathField.setPromptText("PDF File Path");
        Button submitBtn = new Button("Add Song");
        Button backBtn = new Button("Back");

        // Event handlers
        submitBtn.setOnAction(e -> {
            statusLabel.setText("TODO: Add Song");
            handle();   // Refresh the scene basically a back button
        });

        // Back button to Song Library scene
        backBtn.setOnAction(e -> handle());

        // Add all the elements to the layout
        layout.getChildren().addAll(titleField, artistField, filePathField, submitBtn, backBtn, statusLabel);
    }

    private void removeSong(VBox layout, Label statusLabel) {
        // TODO: Placeholder
        statusLabel.setText("Remove Song functionality to be implemented");
    }
}