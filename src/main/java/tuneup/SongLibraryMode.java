package tuneup;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * SongLibraryMode displays all songs in the library and allows for playback.
 *  Users can also search for songs by artist and print songs to text files.
 * 
 * @author edwinjwood
 * @author jaychubb
 * @author allanpaiz
 */
public class SongLibraryMode implements Mode {
    private User userProfile;
    private TuneUp facade;
    
    /**
     * Constructor for terminal mode
     * 
     * @param userProfile User profile
     * @param facade TuneUp facade
     */
    public SongLibraryMode(User userProfile, TuneUp facade) {
        this.userProfile = userProfile;
        this.facade = facade;
    }

    /**
     * Placeholder method for GUI implementation
     */
    @Override
    public void handle() {
        // Placeholder for GUI implementation
        System.out.println("Song Library mode activated for user: " + userProfile.getUsername());
    }
    
    /**
     * Handles the Song Library Mode for the terminal application
     * 
     * @param scanner Scanner for user input
     */
    @Override
    public void handleTerminal(Scanner scanner) {
        System.out.println("\n=== Song Library Mode ===");
        System.out.println("This mode displays all songs in the library.");
        
        boolean inMode = true;
        while (inMode) {
            System.out.println("\nSong Library Options:");
            System.out.println("1. Browse All Songs");
            System.out.println("2. Search by Artist");
            System.out.println("3. Print a song to text file");
            System.out.println("4. Return to Main Menu");
            System.out.print("Choose an option: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                switch (choice) {
                    case 1:
                        browseSongs(scanner);
                        break;
                    case 2:
                        searchSongs(scanner);
                        break;
                    case 3:
                        printSongToFileSelector(scanner);
                        break;
                    case 4:
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
     * Browses songs and allows for selection and playback
     * 
     * @param scanner Scanner for user input
     */
    public void browseSongs(Scanner scanner) {
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
            scanner.nextLine();
            
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
            scanner.nextLine();
        }
    }

    /**
     * Displays all songs in the library (sorted by title)
     * 
     * @param songs List of songs to display
     */
    private void displayAllSongs(List<Song> songs) {
    
        // Print header
        System.out.println("\n=== All Songs in Library (Sorted by Title) ===");
        System.out.println("\n----------------------------------------------------------------------");
        System.out.printf("%-5s %-30s %-20s %-20s\n", "No.", "Title", "Artist Name", "Creator Username");
        System.out.println("----------------------------------------------------------------------");

     
        // Print each song
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            String creatorUsername = facade.getUsernameById(song.getCreatorId());
            System.out.printf("%-5d %-30s %-20s %-20s\n",
                (i + 1),
                truncate(song.getTitle(), 29),
                truncate(song.getArtistName(), 19),
                truncate(creatorUsername, 19));
        }

        System.out.println("----------------------------------------------------------------------");
        System.out.println("\nTotal songs: " + songs.size());
    }
    
    /**
     * Plays a selected song
     * 
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
     * 
     * @param text The text to truncate
     * @param maxLength The maximum length
     * @return Truncated string
     */
    public String truncate(String text, int maxLength) {
        if (text == null || text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }

    /**
     * Searches for songs by artist
     * 
     * @param scanner Scanner for user input
     * @author jaychubb
     */
    public void searchSongs(Scanner scanner) {
        System.out.println("Who would you like to search for?");
        String artist = scanner.nextLine().trim();

        List<Song> songs = SongLibrary.getSongLibrary();
        List<Song> songsByArtist = new ArrayList<>();
        for(int i = 0; i < songs.size(); ++i) {
            if(songs.get(i).getArtistName().equals(artist)) {
                songsByArtist.add(songs.get(i));
            }
        }

        displayAllSongs(songsByArtist);

        System.out.print("\nEnter the number of a song to play (or 0 to return): ");
        try {
            int selection = scanner.nextInt();
            scanner.nextLine();
            
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
            scanner.nextLine();
        }
    }

    /**
     * Prints a song to a text file
     * @param scanner Scanner
     * 
     * @author allanpaiz
     */
    private void printSongToFileSelector(Scanner scanner) {       
        List<Song> songs = SongLibrary.getSongLibrary();
        displayAllSongs(songs);
        System.out.println("\nEnter the number of the song to print (or 0 to return): ");

        try {
            int selection = scanner.nextInt();
            scanner.nextLine();
            
            if (selection > 0 && selection <= songs.size()) {
                Song song = songs.get(selection - 1);
                String filename = song.getTitle() + ".txt";
                printSongToFile(song, filename);
                System.out.println("\nPrinted " + song.getTitle() +  " to text file.");
                System.out.println("Saved to songs/" + filename);
            } else if (selection != 0) {
                System.out.println("Invalid selection.");
            }
        } catch (Exception e) {
            System.out.println("Please enter a valid number.");
            scanner.nextLine();
        }
    }

    /**
     * Prints a song to a text file
     * @param song Song to print
     * @param filename String
     * 
     * @author allanpaiz
     */
    private void printSongToFile(Song song, String filename) {
        try {
            List<String> notes = song.getNotes();
            String timeSignature = song.getTimeSignature();
            int beatsPerMeasure = Integer.parseInt(timeSignature.split("/")[0]);

            java.io.FileWriter fileWriter = new java.io.FileWriter("./songs/" + filename);
            java.io.PrintWriter printWriter = new java.io.PrintWriter(fileWriter);

            printWriter.println(song.getTitle());
            printWriter.println("by " + song.getArtistName());
            printWriter.println();
            printWriter.println("Tempo: " + song.getTempo());
            printWriter.println("Time Signature: " + timeSignature);
            printWriter.println();
            printWriter.println(song.getTempoNotation());

            for (int i = 0; i < notes.size(); i += beatsPerMeasure) {
                printWriter.print("Measure " + (i / beatsPerMeasure + 1) + ": ");
                for (int j = 0; j < beatsPerMeasure; j++) {
                    if (i + j < notes.size()) {
                        printWriter.print(notes.get(i + j) + " ");
                    }
                }
                printWriter.println();
            }

            printWriter.println();
            String creatorUsername = facade.getUsernameById(song.getCreatorId());
            printWriter.println("Uploaded by: " + creatorUsername);
            printWriter.close();
        } catch (java.io.IOException e) {
            System.out.println("Error writing to file: " + filename);
        }
    }

    /**
     * Gets the user profile
     * 
     * @return User userProfile
     * 
     * @author allanpaiz
     */
    public User getUserProfile() {
        return userProfile;
    }
}