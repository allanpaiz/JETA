package tuneup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

// import tuneup.DataLoader;
// import tuneup.DataWriter;
// import tuneup.FileUtils;
// import tuneup.Song;

/**
 * Test class for Song
 * Tests the functionality of the Song class which represents a musical piece
 * in the TuneUp application.
 */
public class SongTest {
    
    private Song song;
    private String title;
    private String creatorId;
    private String artistName;
    private List<String> notes;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private static final String SONGS_FILE_PATH = "data/songs.json";
    
    /**
     * Set up test environment before each test
     */
    @Before
    public void setUp() {
        // Set up test data
        title = "Test Song";
        creatorId = "user123";
        artistName = "Test Artist";
        notes = Arrays.asList("C4", "D4", "E4", "F4");
        
        // Create song instance
        song = new Song(title, creatorId, artistName, notes);
        
        // Set up System.out capture
        System.setOut(new PrintStream(outContent));
        
        // Ensure data directory exists and clear previous test data
        clearSongsFile();
    }
    
    /**
     * Clean up resources after each test
     */
    @After
    public void tearDown() {
        // Restore original System.out
        System.setOut(originalOut);
        
        // Clear test data from file system
        clearSongsFile();
    }
    
    /**
     * Helper method to clear songs.json file
     */
    private void clearSongsFile() {
        try {
            // Ensure data directory exists
            FileUtils.ensureDataFolderExists();
            
            File songsFile = new File(SONGS_FILE_PATH);
            if (songsFile.exists()) {
                // Create empty array to clear the file
                List<Song> emptySongs = Arrays.asList();
                DataWriter.saveSongs(emptySongs);
                
                // Verify file is cleared
                String content = new String(Files.readAllBytes(Paths.get(SONGS_FILE_PATH)));
                assertTrue("Songs file should be empty or contain an empty array", 
                           content.trim().equals("[]") || content.trim().isEmpty());
            }
        } catch (IOException e) {
            System.err.println("Error clearing songs file: " + e.getMessage());
        }
    }
    
    /**
     * Test constructors
     */
    @Test
    public void testConstructorWithAllProperties() {
        Song fullSong = new Song(title, creatorId, artistName, notes, 140, "3/4");
        assertNotNull("Full song should not be null", fullSong);
        assertEquals("Title should match", title, fullSong.getTitle());
        assertEquals("Creator ID should match", creatorId, fullSong.getCreatorId());
        assertEquals("Artist name should match", artistName, fullSong.getArtistName());
        assertEquals("Notes should match", notes, fullSong.getNotes());
        assertEquals("Tempo should match", 140, fullSong.getTempo());
        assertEquals("Time signature should match", "3/4", fullSong.getTimeSignature());
    }
    
    @Test
    public void testConstructorWithDefaultValues() {
        assertNotNull("Song with default values should not be null", song);
        assertEquals("Title should match", title, song.getTitle());
        assertEquals("Creator ID should match", creatorId, song.getCreatorId());
        assertEquals("Artist name should match", artistName, song.getArtistName());
        assertEquals("Notes should match", notes, song.getNotes());
        assertEquals("Default tempo should be 120", 120, song.getTempo());
        assertEquals("Default time signature should be 4/4", "4/4", song.getTimeSignature());
    }
    
    @Test
    public void testDefaultConstructor() {
        Song emptySong = new Song();
        assertNotNull("Empty song should not be null", emptySong);
        assertNotNull("Empty song ID should be generated", emptySong.getId());
    }
    
    /**
     * Test getters and setters
     */
    @Test
    public void testGetId() {
        assertNotNull("Song ID should not be null", song.getId());
        assertFalse("Song ID should not be empty", song.getId().isEmpty());
    }
    
    @Test
    public void testSetId() {
        String newId = "newId123";
        song.setId(newId);
        assertEquals("ID should be updated", newId, song.getId());
    }
    
    @Test
    public void testSetTitle() {
        String newTitle = "New Test Song";
        song.setTitle(newTitle);
        assertEquals("Title should be updated", newTitle, song.getTitle());
    }
    
    @Test
    public void testSetCreatorId() {
        String newCreatorId = "newUser456";
        song.setCreatorId(newCreatorId);
        assertEquals("Creator ID should be updated", newCreatorId, song.getCreatorId());
    }
    
    @Test
    public void testSetArtistName() {
        String newArtistName = "New Test Artist";
        song.setArtistName(newArtistName);
        assertEquals("Artist name should be updated", newArtistName, song.getArtistName());
    }
    
    @Test
    public void testSetNotes() {
        List<String> newNotes = Arrays.asList("G4", "A4", "B4", "C5");
        song.setNotes(newNotes);
        assertEquals("Notes should be updated", newNotes, song.getNotes());
    }
    
    @Test
    public void testSetTempo() {
        int newTempo = 180;
        song.setTempo(newTempo);
        assertEquals("Tempo should be updated", newTempo, song.getTempo());
    }
    
    @Test
    public void testSetTimeSignature() {
        String newTimeSignature = "6/8";
        song.setTimeSignature(newTimeSignature);
        assertEquals("Time signature should be updated", newTimeSignature, song.getTimeSignature());
    }
    
    /**
     * Test tempo notation
     */
    @Test
    public void testGetTempoNotation() {
        // Test all tempo notation ranges
        song.setTempo(50);
        assertEquals("Tempo 50 should be Largo", "Largo", song.tempoNotation());
        
        song.setTempo(70);
        assertEquals("Tempo 70 should be Adagio", "Adagio", song.tempoNotation());
        
        song.setTempo(90);
        assertEquals("Tempo 90 should be Andante", "Andante", song.tempoNotation());
        
        song.setTempo(110);
        assertEquals("Tempo 110 should be Moderato", "Moderato", song.tempoNotation());
        
        song.setTempo(140);
        assertEquals("Tempo 140 should be Allegro", "Allegro", song.tempoNotation());
        
        song.setTempo(180);
        assertEquals("Tempo 180 should be Presto", "Presto", song.tempoNotation());
    }
    
    @Test
    public void testTempoNotationEdgeCases() {
        // Test edge cases
        song.setTempo(60);
        assertEquals("Tempo 60 should be Largo", "Largo", song.tempoNotation());
        
        song.setTempo(61);
        assertEquals("Tempo 61 should be Adagio", "Adagio", song.tempoNotation());
        
        song.setTempo(80);
        assertEquals("Tempo 80 should be Adagio", "Adagio", song.tempoNotation());
        
        song.setTempo(81);
        assertEquals("Tempo 81 should be Andante", "Andante", song.tempoNotation());
        
        song.setTempo(100);
        assertEquals("Tempo 100 should be Andante", "Andante", song.tempoNotation());
        
        song.setTempo(101);
        assertEquals("Tempo 101 should be Moderato", "Moderato", song.tempoNotation());
        
        song.setTempo(120);
        assertEquals("Tempo 120 should be Moderato", "Moderato", song.tempoNotation());
        
        song.setTempo(121);
        assertEquals("Tempo 121 should be Allegro", "Allegro", song.tempoNotation());
        
        song.setTempo(160);
        assertEquals("Tempo 160 should be Allegro", "Allegro", song.tempoNotation());
        
        song.setTempo(161);
        assertEquals("Tempo 161 should be Presto", "Presto", song.tempoNotation());
    }
    
    /**
     * Test data persistence
     */
    @Test
    public void testSongSerialization() {
        // Create a song with specific ID for easy verification
        Song testSong = new Song("Persistence Test", creatorId, artistName, notes);
        String uniqueId = "test-song-id-123";
        testSong.setId(uniqueId);
        
        // Save to file system
        List<Song> songs = Arrays.asList(testSong);
        DataWriter.saveSongs(songs);
        
        // Load from file system
        List<Song> loadedSongs = DataLoader.loadSongs();
        
        // Verify loaded data
        assertNotNull("Loaded songs should not be null", loadedSongs);
        assertEquals("Should have 1 song", 1, loadedSongs.size());
        
        Song loadedSong = loadedSongs.get(0);
        assertEquals("ID should match", uniqueId, loadedSong.getId());
        assertEquals("Title should match", "Persistence Test", loadedSong.getTitle());
        assertEquals("Creator ID should match", creatorId, loadedSong.getCreatorId());
        assertEquals("Artist name should match", artistName, loadedSong.getArtistName());
        assertEquals("Notes should match", notes, loadedSong.getNotes());
    }
    
    /**
     * Test handling of null or invalid values
     */
    @Test
    public void testNullValuesHandling() {
        Song nullSong = new Song(null, null, null, null);
        assertNotNull("Song with null values should not be null itself", nullSong);
        assertNotNull("ID should be generated even with null values", nullSong.getId());
    }
    
    /**
     * Test string representation
     */
    @Test
    public void testToString() {
        String songString = song.toString();
        assertNotNull("toString() should not return null", songString);
        assertTrue("toString() should contain song title", songString.contains(title));
    }
}