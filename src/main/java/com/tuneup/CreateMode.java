package com.tuneup;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CreateMode allows users to create new songs.
 * 
 * @author edwinjwood
 */
public class CreateMode implements Mode {
    private User userProfile;
    // private TuneUp facade;
    private final String[] VALID_NOTES = {"C4", "D4", "E4", "F4", "G4", "A4", "B4", "C5"};
    
    // Default music parameters
    private static final int DEFAULT_TEMPO = 120; // beats per minute
    private static final String DEFAULT_TIME_SIGNATURE = "4/4"; // common time
    
    // Constructor for terminal mode
    public CreateMode(User userProfile, TuneUp facade) {
        this.userProfile = userProfile;
        // this.facade = facade;
    }

    /**
     * Handles the Create Mode for the GUI
     */
    @Override
    public void handle() {
        // Placeholder for GUI implementation
        System.out.println("Create mode activated for user: " + userProfile.getUsername());
    }
    
    /**
     * Handles the Create Mode for the terminal interface
     * 
     * @param scanner Scanner for user input
     */
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
                scanner.nextLine();
                
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
                scanner.nextLine();
            }
        }
    }
    
    /**
     * Creates a new song by allowing the user to input a sequence of notes
     * 
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
        
        // Get tempo and time signature
        int tempo = getTempo(scanner);
        String timeSignature = getTimeSignature(scanner);
        
        // Get song notes using improved note selection interface
        List<String> notes = buildSongWithMeasures(scanner, timeSignature);
        
        // Check if any notes were added
        if (notes.isEmpty()) {
            System.out.println("No notes were added to the song. Song creation cancelled.");
            return;
        }
        
        // Create the song object with all properties
        Song newSong = new Song(title, userProfile.getId(), userProfile.getUsername(), notes, tempo, timeSignature);
        
        // Add song to the library
        SongLibrary.addSong(newSong);
        
        // Preview completed song
        previewCompleteSong(newSong);
        
        System.out.println("Song '" + title + "' saved successfully!");
    }
    
    /**
     * Gets tempo from user input
     * 
     * @param scanner Scanner for user input
     * @return The chosen tempo (beats per minute)
     */
    private int getTempo(Scanner scanner) {
        int tempo = DEFAULT_TEMPO;
        
        System.out.println("\n=== Set Tempo ===");
        System.out.println("Tempo determines the speed of your song (beats per minute).");
        System.out.println("Common tempos: Slow (60-80), Medium (90-120), Fast (130-160)");
        System.out.print("Enter tempo (or press Enter for default " + DEFAULT_TEMPO + " BPM): ");
        
        String input = scanner.nextLine().trim();
        
        if (!input.isEmpty()) {
            try {
                tempo = Integer.parseInt(input);
                if (tempo < 40 || tempo > 240) {
                    System.out.println("Tempo should be between 40-240 BPM. Using default tempo: " + DEFAULT_TEMPO);
                    tempo = DEFAULT_TEMPO;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid tempo. Using default tempo: " + DEFAULT_TEMPO);
            }
        }
        
        System.out.println("Tempo set to: " + tempo + " BPM");
        return tempo;
    }
    
    /**
     * Gets time signature from user input
     * 
     * @param scanner Scanner for user input
     * @return The chosen time signature
     */
    private String getTimeSignature(Scanner scanner) {
        String timeSignature = DEFAULT_TIME_SIGNATURE;
        
        System.out.println("\n=== Set Time Signature ===");
        System.out.println("Time signature determines the rhythm structure of your song.");
        System.out.println("Common time signatures: 4/4 (common), 3/4 (waltz), 6/8 (compound)");
        System.out.println("1. 4/4 (Common time)");
        System.out.println("2. 3/4 (Waltz time)");
        System.out.println("3. 6/8 (Compound time)");
        System.out.print("Choose time signature (1-3) or press Enter for default 4/4: ");
        
        String input = scanner.nextLine().trim();
        
        if (!input.isEmpty()) {
            try {
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        timeSignature = "4/4";
                        break;
                    case 2:
                        timeSignature = "3/4";
                        break;
                    case 3:
                        timeSignature = "6/8";
                        break;
                    default:
                        System.out.println("Invalid choice. Using default time signature: " + DEFAULT_TIME_SIGNATURE);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Using default time signature: " + DEFAULT_TIME_SIGNATURE);
            }
        }
        
        System.out.println("Time signature set to: " + timeSignature);
        return timeSignature;
    }
    
    /**
     * Builds a song using a measure-based approach for easier note selection
     * 
     * @param scanner Scanner for user input
     * @param timeSignature The time signature for the song
     * @return List of notes in the song
     */
    private List<String> buildSongWithMeasures(Scanner scanner, String timeSignature) {
        List<String> allNotes = new ArrayList<>();
        List<String> currentMeasure = new ArrayList<>();
        int measureCount = 1;
        boolean buildingSong = true;
        
        // Calculate beats per measure based on time signature
        int beatsPerMeasure = getBeatsPerMeasure(timeSignature);
        
        System.out.println("\n=== Song Builder ===");
        System.out.println("Create your song by adding notes to measures.");
        System.out.println("Time signature " + timeSignature + " means " + beatsPerMeasure + " beats per measure.");
        
        while (buildingSong) {
            System.out.println("\n=== Measure " + measureCount + " ===");
            currentMeasure = buildMeasure(scanner, beatsPerMeasure);
            
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
     * Calculates beats per measure based on time signature
     * 
     * @param timeSignature The time signature string
     * @return Number of beats per measure
     */
    public int getBeatsPerMeasure(String timeSignature) {
        // Extract the numerator from time signature (e.g., "4" from "4/4")
        try {
            return Integer.parseInt(timeSignature.split("/")[0]);
        } catch (Exception e) {
            return 4; // Default to 4 beats if parsing fails
        }
    }
    
    /**
     * Builds a single measure by allowing the user to select notes by number
     * 
     * @param scanner Scanner for user input
     * @param beatsPerMeasure Number of beats in this measure
     * @return List of notes in the measure
     */
    private List<String> buildMeasure(Scanner scanner, int beatsPerMeasure) {
        List<String> measure = new ArrayList<>();
        boolean buildingMeasure = true;
        
        displayNoteOptions();
        System.out.println("\nThis measure needs " + beatsPerMeasure + " beats. Each note counts as 1 beat.");
        System.out.println("Current beats: 0/" + beatsPerMeasure);
        
        while (buildingMeasure) {
            System.out.print("\nSelect note (1-8) or enter commands: [P]review, [C]lear, [D]one: ");
            String input = scanner.nextLine().trim().toUpperCase();
            
            // Check for commands
            if (input.equals("D")) {
                if (measure.size() < beatsPerMeasure) {
                    System.out.println("Warning: Measure has " + measure.size() + " beats but needs " + beatsPerMeasure);
                    System.out.print("Continue anyway? (Y/N): ");
                    String confirm = scanner.nextLine().trim().toUpperCase();
                    if (!confirm.equals("Y")) {
                        continue;
                    }
                }
                buildingMeasure = false;
            } else if (input.equals("P")) {
                previewMeasure(measure);
            } else if (input.equals("C")) {
                measure.clear();
                System.out.println("Measure cleared.");
                System.out.println("Current beats: 0/" + beatsPerMeasure);
            } else {
                // Try to parse as a number for note selection
                try {
                    int noteIndex = Integer.parseInt(input);
                    if (noteIndex >= 1 && noteIndex <= VALID_NOTES.length) {
                        String selectedNote = VALID_NOTES[noteIndex - 1];
                        measure.add(selectedNote);
                        System.out.println("Added note: " + selectedNote);
                        System.out.println("Current beats: " + measure.size() + "/" + beatsPerMeasure);
                        
                        // Warn or auto-complete when measure is full
                        if (measure.size() == beatsPerMeasure) {
                            System.out.println("Measure is now full. You can press D to finish this measure.");
                        } else if (measure.size() > beatsPerMeasure) {
                            System.out.println("Warning: Measure has exceeded " + beatsPerMeasure + " beats.");
                        }
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
     * 
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
     * Previews the entire completed song
     * 
     * @param song The song to preview
     */
    private void previewCompleteSong(Song song) {
        System.out.println("\n=== Previewing Completed Song: " + song.getTitle() + " ===");
        
        List<String> notes = song.getNotes();
        int tempo = song.getTempo();
        
        // Calculate note duration based on tempo (in milliseconds)
        int noteDuration = (int) (60000 / tempo);
        
        System.out.println("Song has " + notes.size() + " notes at tempo " + tempo + " BPM");
        System.out.println("Playing...");
        
        // Create piano for playback
        PianoStrategy piano = new PianoStrategy();
        
        try {
            for (String note : notes) {
                System.out.print(note + " ");
                
                // Play the note
                piano.playNote(note);
                
                // Hold note based on tempo
                try {
                    Thread.sleep(noteDuration * 3/4); // Play note for 75% of the beat
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                // Stop the note
                piano.stop();
                
                // Brief silence between notes
                try {
                    Thread.sleep(noteDuration * 1/4); // 25% of the beat for silence
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("\nSong preview complete!");
        } finally {
            // Ensure piano resources are released
            piano.close();
        }
    }
    
    /**
     * Validates if a note is in the allowed range
     * 
     * @param note The note to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidNote(String note) {
        for (String validNote : VALID_NOTES) {
            if (validNote.equalsIgnoreCase(note)) {
                return true;
            }
        }
        return false;
    }
}