package com.tuneup;

import java.util.Scanner;

public class PlayMode implements Mode {
    private User userProfile;
    private TuneUp facade;
    
    // Constructor for terminal mode
    public PlayMode(User userProfile, TuneUp facade) {
        this.userProfile = userProfile;
        this.facade = facade;
    }

    @Override
    public void handle() {
        // Placeholder for GUI implementation
        System.out.println("Play mode activated for user: " + userProfile.getUsername());
    }
    
    @Override
    public void handleTerminal(Scanner scanner) {
        System.out.println("\n=== Play Mode ===");
        System.out.println("This mode allows you to play musical instruments.");
        
        boolean inMode = true;
        while (inMode) {
            System.out.println("\nSelect an instrument to play:");
            System.out.println("1. Piano");
            System.out.println("2. Return to Main Menu");
            System.out.print("Choose an option: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                
                switch (choice) {
                    case 1:
                        playInstrument(new PianoStrategy(), scanner);
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
     * Plays the selected instrument with just four notes
     * @param instrument The instrument strategy to use
     * @param scanner Scanner for user input
     */
    private void playInstrument(InstrumentStrategy instrument, Scanner scanner) {
        System.out.println("\n=== Piano Playing Mode ===");
        System.out.println("You can now play notes on the piano!");
        
        // Keep playing until user chooses to exit
        boolean playing = true;
        while (playing) {
            // Show note selection menu each time
            showNoteMenu();
            
            try {
                System.out.print("Select a note to play (1-4) or exit (5): ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                
                switch (choice) {
                    case 1:
                        playNote("C4", instrument);
                        break;
                    case 2:
                        playNote("E4", instrument);
                        break;
                    case 3:
                        playNote("G4", instrument);
                        break;
                    case 4:
                        playNote("C5", instrument);
                        break;
                    case 5:
                        playing = false;
                        instrument.stop();
                        if (instrument instanceof PianoStrategy) {
                            ((PianoStrategy) instrument).close();
                        }
                        System.out.println("Returning to instrument selection...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a number between 1 and 5.");
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    
    /**
     * Displays the available note options
     */
    private void showNoteMenu() {
        System.out.println("\nAvailable Notes:");
        System.out.println("1. C4 (Middle C)");
        System.out.println("2. E4");
        System.out.println("3. G4");
        System.out.println("4. C5");
        System.out.println("5. Exit to instrument selection");
    }
    
    /**
     * Plays a specific note using the provided instrument strategy
     * @param note The note to play
     * @param instrument The instrument strategy to use
     */
    private void playNote(String note, InstrumentStrategy instrument) {
        System.out.println("Playing " + note);
        instrument.playNote(note);
        
        // Let the note play for a moment
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Stop the note
        instrument.stop();
    }
}