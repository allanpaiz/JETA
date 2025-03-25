package com.tuneup;

import java.util.List;
import java.util.Scanner;

public class SongLibraryMode implements Mode {
    private User userProfile;
    private TuneUp facade;
    
    // Constructor for terminal mode
    public SongLibraryMode(User userProfile, TuneUp facade) {
        this.userProfile = userProfile;
        this.facade = facade;
    }

    @Override
    public void handle() {
        // Placeholder for GUI implementation
        System.out.println("Song Library mode activated for user: " + userProfile.getUsername());
    }
    
    @Override
    public void handleTerminal(Scanner scanner) {
        System.out.println("\n=== Song Library Mode ===");
        System.out.println("This mode displays all songs in the library.");
        
        boolean inMode = true;
        while (inMode) {
            System.out.println("\nSong Library Options:");
            System.out.println("1. Browse All Songs");
            System.out.println("2. Return to Main Menu");
            System.out.print("Choose an option: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                
                switch (choice) {
                    case 1:
                        browseSongs(scanner);
                        break;
                    case 2:
                        inMode = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    
    /**
     * Browses songs and allows for selection and playback
     * @param scanner Scanner for user input
     */
    private void browseSongs(Scanner scanner) {
        // Get all songs from the SongLibrary (already sorted by title)
        List<Song> songs = SongLibrary.getSongLibrary();
        
        if (songs.isEmpty()) {
            System.out.println("No songs found in the library. Try creating some songs first!");
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        // Display all songs
        displayAllSongs(songs);
        
        // Allow selection and playback
        System.out.print("\nEnter the number of a song to play (or 0 to return): ");
        try {
            int selection = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            
            if (selection > 0 && selection <= songs.size()) {
                // Play the selected song
                playSong(songs.get(selection - 1), scanner);
            } else if (selection != 0) {
                System.out.println("Invalid selection.");
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Please enter a valid number.");
            scanner.nextLine(); // Clear the invalid input
        }
    }
    
    /**
     * Displays all songs in the library (sorted by title)
     * @param songs List of songs to display
     */
    private void displayAllSongs(List<Song> songs) {
        System.out.println("\n=== All Songs in Library (Sorted by Title) ===");
        
        // Print header
        System.out.println("\n-------------------------------------------------");
        System.out.printf("%-5s %-25s %-20s\n", "No.", "Title", "Creator Username");
        System.out.println("-------------------------------------------------");
        
        // Print each song
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            String creatorUsername = facade.getUsernameById(song.getCreatorId());
            System.out.printf("%-5d %-25s %-20s\n", 
                (i+1), 
                truncate(song.getTitle(), 24), 
                truncate(creatorUsername, 19));
        }
        
        System.out.println("-------------------------------------------------");
        
        // Print total number of songs
        System.out.println("\nTotal songs: " + songs.size());
    }
    
    /**
     * Plays a selected song
     * @param song The song to play
     * @param scanner Scanner for user input
     */
    private void playSong(Song song, Scanner scanner) {
        System.out.println("\n=== Now Playing: " + song.getTitle() + " ===");
        System.out.println("Creator ID: " + song.getCreatorId());
        
        List<String> notes = song.getNotes();
        
        if (notes.isEmpty()) {
            System.out.println("This song has no notes to play.");
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        System.out.println("\nNotes in song:");
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i+1) + ". " + notes.get(i));
        }
        
        System.out.println("\nPlaying song...");
        
        // Create piano for playback
        PianoStrategy piano = new PianoStrategy();
        
        try {
            // Play each note in sequence
            for (String note : notes) {
                System.out.print("Playing note: " + note + "  ");
                
                // Play the note
                piano.playNote(note);
                
                // Let the note play for a moment
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                // Stop the note
                piano.stop();
                
                // Small pause between notes
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                System.out.println("✓");
            }
            
            System.out.println("\nSong playback complete!");
        } finally {
            // Ensure piano resources are released
            piano.close();
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Truncates a string if it's longer than the specified length
     * @param text The text to truncate
     * @param maxLength The maximum length
     * @return Truncated string
     */
    private String truncate(String text, int maxLength) {
        if (text == null || text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }
}