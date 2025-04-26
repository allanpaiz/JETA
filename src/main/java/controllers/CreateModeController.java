package controllers;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import tuneup.CreateMode;
import tuneup.PianoStrategy;
import tuneup.Song;
import tuneup.SongLibrary;
import tuneup.TuneUp;
import tuneup.User;

/**
 * Controller for Create Mode business logic
 */
public class CreateModeController {
    private static final Logger logger = Logger.getLogger(CreateModeController.class.getName());
    
    private TuneUp facade;
    private CreateMode createMode;
    private User currentUser;
    private PianoStrategy piano;
    
    // Constants from CreateMode
    private final String[] VALID_NOTES = {"C4", "D4", "E4", "F4", "G4", "A4", "B4", "C5"};
    
    /**
     * Constructor initializes with app facade and current user
     */
    public CreateModeController(TuneUp facade, User user) {
        this.facade = facade;
        this.currentUser = user;
        this.createMode = new CreateMode(user, facade);
        this.piano = new PianoStrategy();
        logger.info("CreateModeController initialized for user: " + 
                    (user != null ? user.getUsername() : "unknown"));
    }
    
    /**
     * Get array of valid notes
     */
    public String[] getValidNotes() {
        return VALID_NOTES;
    }
    
    /**
     * Check if a note is valid
     */
    public boolean isValidNote(String note) {
        return createMode.isValidNote(note);
    }
    
    /**
     * Calculate beats per measure based on time signature
     */
    public int getBeatsPerMeasure(String timeSignature) {
        return createMode.getBeatsPerMeasure(timeSignature);
    }
    
    /**
     * Play a single note
     */
    public void playNote(String note) {
        if (isValidNote(note)) {
            try {
                piano.playNote(note);
                Thread.sleep(500); // Play for half a second
                piano.stop();
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error playing note", e);
            }
        } else {
            logger.warning("Attempted to play invalid note: " + note);
        }
    }
    
    /**
     * Play a sequence of notes
     */
    public void playNotes(List<String> notes, int tempo) {
        if (notes == null || notes.isEmpty()) {
            logger.warning("Attempted to play empty note sequence");
            return;
        }
        
        // Calculate note duration based on tempo (in milliseconds)
        int noteDuration = (int) (60000 / tempo);
        
        new Thread(() -> {
            try {
                for (String note : notes) {
                    if (isValidNote(note)) {
                        piano.playNote(note);
                        
                        // Hold note based on tempo
                        try {
                            Thread.sleep(noteDuration * 3/4); // Play note for 75% of the beat
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                        
                        // Stop the note
                        piano.stop();
                        
                        // Brief silence between notes
                        try {
                            Thread.sleep(noteDuration * 1/4); // 25% of the beat for silence
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error during playback", e);
            }
        }).start();
    }
    
    /**
     * Save a song to the library
     */
    public boolean saveSong(String title, List<String> notes, int tempo, String timeSignature) {
        try {
            if (title == null || title.trim().isEmpty()) {
                logger.warning("Attempted to save song with empty title");
                return false;
            }
            
            if (notes == null || notes.isEmpty()) {
                logger.warning("Attempted to save song with no notes");
                return false;
            }
            
            // Create the song
            Song newSong = new Song(
                title,
                currentUser.getId(),
                currentUser.getUsername(),
                notes,
                tempo,
                timeSignature
            );
            
            try {
                // Add to library directly - SongLibrary handles saving
                SongLibrary.songLibrary.add(newSong);
                logger.info("Song saved: " + title);
                return true;
            } catch (Exception e) {
                logger.warning("Failed to add song to library");
                return false;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error saving song", e);
            return false;
        }
    }
    
    /**
     * Clean up resources
     */
    public void cleanup() {
        if (piano != null) {
            piano.close();
        }
    }
    
    /**
     * Get the application facade
     */
    public TuneUp getFacade() {
        return facade;
    }
    
    /**
     * Get the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }
}
