package com.tuneup;

import java.util.Scanner;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class SongLibraryMode implements Mode {
    private User userProfile;
    private TuneUp facade;
    private Stage stage;
    private TuneUpUI tuneUpUI;

    /**
     * Constructor for SongLibraryMode sets data members
     * 
     * @param userProfile User 
     * @param profileManager ProfileManager
     * @param tuneUpUI TuneUpUI
     */
    public SongLibraryMode(User userProfile, TuneUp facade, Stage stage, TuneUpUI tuneUpUI) {
        this.userProfile = userProfile;
        this.facade = facade;
        this.stage = stage;
        this.tuneUpUI = tuneUpUI;
    }

    /**
     * Handle method
     * This method is called when the Song Library button is clicked
     */
    @Override
    public void handle() {
        stage.setScene(createSongLibraryScene());
    }

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
        // viewLibraryBtn.setOnAction(e -> viewLibrary(layout, statusLabel));
        // addSongBtn.setOnAction(e -> addSong(layout, statusLabel));
        // removeSongBtn.setOnAction(e -> removeSong(layout, statusLabel));

        // Back button, this needs to be the same for all modes
        backBtn.setOnAction(e -> tuneUpUI.showMainMenu(stage));

        // Add all the elements to the layout
        layout.getChildren().addAll(title, viewLibraryBtn, addSongBtn, removeSongBtn, backBtn, statusLabel);
        
        // Return the scene
        return new Scene(layout, 400, 300);
    }



    /**
     * Creates the Song Library menu
     */
    private void createSongLibraryMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Song Library");
            System.out.println("1. View Library");
            System.out.println("2. Add Song");
            System.out.println("3. Remove Song");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewLibrary();
                    break;
                case 2:
                    addSong(scanner);
                    break;
                case 3:
                    removeSong(scanner);
                    break;
                case 4:
                    return; // Exit the song library menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewLibrary() {
        // TODO: Placeholder
        System.out.println("TODO: View Library");
    }

    private void addSong(Scanner scanner) {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine();
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine();
        System.out.print("Enter PDF file path: ");
        String filePath = scanner.nextLine();

        // TODO: Add song logic
        System.out.println("TODO: Add Song");

        // Refresh the menu
        handle();
    }

    private void removeSong(Scanner scanner) {
        // TODO: Placeholder
        System.out.println("Remove Song functionality to be implemented");
    }
}