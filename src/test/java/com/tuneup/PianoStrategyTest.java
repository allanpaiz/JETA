package com.tuneup;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for PianoStrategy
 * Tests the functionality of the PianoStrategy class which implements
 * the InstrumentStrategy interface.
 */
public class PianoStrategyTest {
    
    private PianoStrategy pianoStrategy;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    @Before
    public void setUp() {
        // Initialize the piano strategy
        pianoStrategy = new PianoStrategy();
        
        // Set up System.out capture for testing console output
        System.setOut(new PrintStream(outContent));
    }
    
    /**
     * Restore original System.out after each test
     */
    @org.junit.After
    public void tearDown() {
        System.setOut(originalOut);
        pianoStrategy.close(); // Ensure synthesizer is closed
    }
    
    /**
     * Test that PianoStrategy implements InstrumentStrategy
     */
    @Test
    public void testImplementsInstrumentStrategy() {
        assertTrue("PianoStrategy should implement InstrumentStrategy", 
                pianoStrategy instanceof InstrumentStrategy);
    }
    
    /**
     * Test that play method exists and can be called without exceptions
     */
    @Test
    public void testPlay() {
        try {
            pianoStrategy.play();
            // If we reach here without exception, the test passes
            assertTrue(true);
        } catch (Exception e) {
            fail("play() method should not throw exceptions");
        }
    }
    
    /**
     * Test playing a specific note with valid input
     */
    @Test
    public void testPlayNote() {
        pianoStrategy.playNote("C4");
        String output = outContent.toString().trim();
        assertTrue("Playing C4 should output MIDI note 60", 
                output.contains("Playing MIDI note: 60"));
    }
    
    /**
     * Test playing a note with sharp
     */
    @Test
    public void testPlaySharpNote() {
        pianoStrategy.playNote("C#4");
        String output = outContent.toString().trim();
        assertTrue("Playing C#4 should output MIDI note 61", 
                output.contains("Playing MIDI note: 61"));
    }
    
    /**
     * Test handling of null note
     */
    @Test
    public void testPlayNullNote() {
        pianoStrategy.playNote(null);
        String output = outContent.toString().trim();
        assertTrue("Playing null note should default to MIDI note 60", 
                output.contains("Playing MIDI note: 60"));
    }
    
    /**
     * Test handling of empty note
     */
    @Test
    public void testPlayEmptyNote() {
        pianoStrategy.playNote("");
        String output = outContent.toString().trim();
        assertTrue("Playing empty note should default to MIDI note 60", 
                output.contains("Playing MIDI note: 60"));
    }
    
    /**
     * Test stop method
     * This is tricky to test directly, but we can check if currentNote gets reset
     */
    @Test
    public void testStop() {
        try {
            // First play a note
            pianoStrategy.playNote("C4");
            
            // Get access to the private currentNote field using reflection
            Field currentNoteField = PianoStrategy.class.getDeclaredField("currentNote");
            currentNoteField.setAccessible(true);
            
            // Check if currentNote is set after playing
            int currentNoteAfterPlay = (int) currentNoteField.get(pianoStrategy);
            assertEquals("Current note should be 60 after playing C4", 60, currentNoteAfterPlay);
            
            // Now stop the note
            pianoStrategy.stop();
            
            // Check if currentNote is reset after stopping
            int currentNoteAfterStop = (int) currentNoteField.get(pianoStrategy);
            assertEquals("Current note should be -1 after stopping", -1, currentNoteAfterStop);
            
        } catch (Exception e) {
            fail("Exception should not occur when testing stop: " + e.getMessage());
        }
    }
    
    /**
     * Test note to MIDI conversion for various notes
     * Since getMidiNote is private, we'll test it indirectly via playNote
     */
    @Test
    public void testNoteToMidiConversion() {
        // Test standard notes across octaves
        testNoteToMidiHelper("C4", 60); // Middle C
        testNoteToMidiHelper("D4", 62);
        testNoteToMidiHelper("E4", 64);
        testNoteToMidiHelper("F4", 65);
        testNoteToMidiHelper("G4", 67);
        testNoteToMidiHelper("A4", 69);
        testNoteToMidiHelper("B4", 71);
        
        // Test sharp notes
        testNoteToMidiHelper("C#4", 61);
        testNoteToMidiHelper("F#4", 66);
        
        // Test different octaves
        testNoteToMidiHelper("C3", 48);
        testNoteToMidiHelper("C5", 72);
    }
    
    /**
     * Helper method to test note to MIDI conversion
     */
    private void testNoteToMidiHelper(String note, int expectedMidi) {
        outContent.reset(); // Clear previous output
        pianoStrategy.playNote(note);
        String output = outContent.toString().trim();
        assertTrue("Playing " + note + " should output MIDI note " + expectedMidi, 
                output.contains("Playing MIDI note: " + expectedMidi));
    }
    
    /**
     * Test handling of invalid note letters
     */
    @Test
    public void testInvalidNoteLetter() {
        pianoStrategy.playNote("X4");
        String output = outContent.toString().trim();
        assertTrue("Playing invalid note X4 should default to MIDI note 60", 
                output.contains("Playing MIDI note: 60"));
    }
    
    /**
     * Test close method
     * Difficult to test directly, but we can verify it doesn't throw exceptions
     */
    @Test
    public void testClose() {
        try {
            pianoStrategy.close();
            // If we get here without exception, test passes
            assertTrue(true);
            
            // Call close again to test handling of already closed synthesizer
            pianoStrategy.close();
            assertTrue(true);
        } catch (Exception e) {
            fail("close() method should not throw exceptions: " + e.getMessage());
        }
    }
    
    /**
     * Test full MIDI note range
     */
    @Test
    public void testNoteRange() {
        // Test lowest note C0
        testNoteToMidiHelper("C0", 12);
        
        // Test highest note G9
        testNoteToMidiHelper("G9", 127);
        
        // These should be the limits of MIDI range (0-127)
    }
}