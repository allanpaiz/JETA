package com.tuneup;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MusicLibraryMode implements Mode {
    private String libraryModeID;
    private List<String> songList;
    private HashMap<String, SongDetails> songDetails;
    private Scanner scanner;
    private static final String SHEET_MUSIC_PATH = "sheetmusic/";

    public MusicLibraryMode() {
        scanner = new Scanner(System.in);
        songList = loadSheetMusic();
        songDetails = initializeSongDetails();
    }

    private List<String> loadSheetMusic() {
        List<String> files = new ArrayList<>();
        File folder = new File(SHEET_MUSIC_PATH);
        
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdir();
            return files;
        }

        File[] sheetMusicFiles = folder.listFiles((dir, name) -> 
            name.toLowerCase().endsWith(".pdf"));
        
        if (sheetMusicFiles != null) {
            for (File file : sheetMusicFiles) {
                files.add(file.getName());
            }
        }
        return files;
    }

    private HashMap<String, SongDetails> initializeSongDetails() {
        HashMap<String, SongDetails> details = new HashMap<>();
        // Example song details - in production, this would load from a database
        details.put("moonlight_sonata.pdf", new SongDetails("4/4", "C# minor", "1801"));
        details.put("fur_elise.pdf", new SongDetails("3/4", "A minor", "1810"));
        return details;
    }

    @Override
    public void handle() {
        while (true) {
            System.out.println("\nMusic Library Mode");
            System.out.println("1. View Sheet Music List");
            System.out.println("2. Return to Learning Area");
            System.out.print("Select an option (1-2): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewSheetMusic();
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid option selected.");
            }
        }
    }

    public void viewSheetMusic() {
        if (songList.isEmpty()) {
            System.out.println("\nNo sheet music available.");
            return;
        }

        while (true) {
            System.out.println("\nAvailable Sheet Music:");
            for (int i = 0; i < songList.size(); i++) {
                System.out.println((i + 1) + ". " + songList.get(i));
            }
            System.out.println((songList.size() + 1) + ". Return to menu");

            System.out.print("\nSelect a piece (1-" + (songList.size() + 1) + "): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == songList.size() + 1) {
                break;
            }

            if (choice > 0 && choice <= songList.size()) {
                String selectedSong = songList.get(choice - 1);
                showSongOptions(selectedSong);
            } else {
                System.out.println("Invalid selection.");
            }
        }
    }

    private void showSongOptions(String songName) {
        while (true) {
            System.out.println("\nSelected: " + songName);
            System.out.println("1. View Sheet Music");
            System.out.println("2. View Song Details");
            System.out.println("3. Return to Song List");
            System.out.print("Select an option (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    openSheetMusic(songName);
                    break;
                case 2:
                    displaySongDetails(songName);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option selected.");
            }
        }
    }

    private void openSheetMusic(String songName) {
        try {
            File sheetMusic = new File(SHEET_MUSIC_PATH + songName);
            Desktop.getDesktop().open(sheetMusic);
            System.out.println("Opening: " + songName);
        } catch (Exception e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
    }

    private void displaySongDetails(String songName) {
        SongDetails details = songDetails.get(songName);
        if (details != null) {
            details.display();
        } else {
            System.out.println("No details available for this song.");
        }
    }
}