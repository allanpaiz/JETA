package com.tuneup;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateMode implements Mode {
    private User userProfile;
    private TuneUp facade;
    private final String[] VALID_NOTES = {"C4", "D4", "E4", "F4", "G4", "A4", "B4", "C5"};
    
    // Constructor for terminal mode
    public CreateMode(User userProfile, TuneUp facade) {
        this.userProfile = userProfile;
        this.facade = facade;
    }

    @Override
    public void handle() {
        // Placeholder for GUI implementation
        System.out.println("Create mode activated for user: " + userProfile.getUsername());
    }
    
    @Override
    public void handleTerminal(Scanner scanner) {
        System.out.println("\n=== Create Mode ===");
        System.out.println("This mode allows you to create new songs.");
        
        boolean inMode = true;
        while (inMode) {
            System.out.println("\nCreate Mode Options:");
            System.out.println("1. Create New Song");
            System.out.println("2. Return to Main Menu");
            System.out.print("Choose an option: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                
                switch (choice) {
                    case 1:
                        createNewSong(scanner);
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
     * Creates a new song by allowing the user to input a sequence of notes
     * @param scanner Scanner for user input
     */
    private void createNewSong(Scanner scanner) {
        System.out.println("\n=== Create New Song ===");
        
        // Get song metadata
        System.out.print("Enter song title: ");
        String title = scanner.nextLine().trim();
        
        if (title.isEmpty()) {
            System.out.println("Song title cannot be empty. Operation cancelled.");
            return;
        }
        
        // Get song notes using improved note selection interface
        List<String> notes = buildSongWithMeasures(scanner);
        
        // Check if any notes were added
        if (notes.isEmpty()) {
            System.out.println("No notes were added to the song. Song creation cancelled.");
            return;
        }
        
        // Create the song object
        Song newSong = new Song(title, userProfile.getId(), notes);
        
        // Add song to the library
        SongLibrary.addSong(newSong);
        
        System.out.println("Song '" + title + "' saved successfully!");
    }
    
    /**
     * Builds a song using a measure-based approach for easier note selection
     * @param scanner Scanner for user input
     * @return List of notes in the song
     */
    private List<String> buildSongWithMeasures(Scanner scanner) {
        List<String> allNotes = new ArrayList<>();
        List<String> currentMeasure = new ArrayList<>();
        int measureCount = 1;
        boolean buildingSong = true;
        
        System.out.println("\n=== Song Builder ===");
        System.out.println("Create your song by adding notes to measures.");
        System.out.println("A song consists of multiple measures, each with a sequence of notes.");
        
        while (buildingSong) {
            System.out.println("\n=== Measure " + measureCount + " ===");
            currentMeasure = buildMeasure(scanner);
            
            if (!currentMeasure.isEmpty()) {
                // Add notes from this measure to the song
                allNotes.addAll(currentMeasure);
                
                // Preview the measure
                previewMeasure(currentMeasure);
                
                measureCount++;
                
                // Ask if user wants to add another measure
                System.out.print("\nAdd another measure? (Y/N): ");
                String answer = scanner.nextLine().trim().toUpperCase();
                
                if (!answer.equals("Y")) {
                    buildingSong = false;
                    System.out.println("\nSong building completed. Total measures: " + (measureCount - 1));
                }
            } else {
                // If measure was empty, ask if user wants to try again or quit
                System.out.print("\nMeasure is empty. Try again or quit building? (T/Q): ");
                String answer = scanner.nextLine().trim().toUpperCase();
                
                if (!answer.equals("T")) {
                    buildingSong = false;
                }
            }
        }
        
        return allNotes;
    }
    
    /**
     * Builds a single measure by allowing the user to select notes by number
     * @param scanner Scanner for user input
     * @return List of notes in the measure
     */
    private List<String> buildMeasure(Scanner scanner) {
        List<String> measure = new ArrayList<>();
        boolean buildingMeasure = true;
        
        displayNoteOptions();
        
        while (buildingMeasure) {
            System.out.print("\nSelect note (1-8) or enter commands: [P]review, [C]lear, [D]one: ");
            String input = scanner.nextLine().trim().toUpperCase();
            
            // Check for commands
            if (input.equals("D")) {
                buildingMeasure = false;
            } else if (input.equals("P")) {
                previewMeasure(measure);
            } else if (input.equals("C")) {
                measure.clear();
                System.out.println("Measure cleared.");
            } else {
                // Try to parse as a number for note selection
                try {
                    int noteIndex = Integer.parseInt(input);
                    if (noteIndex >= 1 && noteIndex <= VALID_NOTES.length) {
                        String selectedNote = VALID_NOTES[noteIndex - 1];
                        measure.add(selectedNote);
                        System.out.println("Added note: " + selectedNote);
                    } else {
                        System.out.println("Invalid selection. Please enter a number between 1 and " + VALID_NOTES.length);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Enter a number or command letter.");
                }
            }
        }
        
        return measure;
    }
    
    /**
     * Displays the available note options with numbers for selection
     */
    private void displayNoteOptions() {
        System.out.println("Available Notes:");
        for (int i = 0; i < VALID_NOTES.length; i++) {
            System.out.println((i + 1) + ". " + VALID_NOTES[i]);
        }
        System.out.println("\nCommands:");
        System.out.println("P - Preview current measure");
        System.out.println("C - Clear current measure");
        System.out.println("D - Done with this measure");
    }
    
    /**
     * Previews a measure by playing the notes in sequence
     * @param measure List of notes in the measure to preview
     */
    private void previewMeasure(List<String> measure) {
        if (measure.isEmpty()) {
            System.out.println("Measure is empty. Nothing to preview.");
            return;
        }
        
        System.out.println("\nPreviewing measure with notes: " + String.join(", ", measure));
        
        // Create piano for playback
        PianoStrategy piano = new PianoStrategy();
        
        try {
            // Play each note in the measure
            for (String note : measure) {
                System.out.print("Playing note: " + note + "  ");
                
                // Play the note
                piano.playNote(note);
                
                // Let the note play for a moment
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                // Stop the note
                piano.stop();
                
                // Small pause between notes
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                System.out.println("✓");
            }
        } finally {
            // Ensure piano resources are released
            piano.close();
        }
        
        System.out.println("Preview complete.");
    }
    
    /**
     * Validates if a note is in the allowed range
     * @param note The note to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidNote(String note) {
        for (String validNote : VALID_NOTES) {
            if (validNote.equalsIgnoreCase(note)) {
                return true;
            }
        }
        return false;
    }
}