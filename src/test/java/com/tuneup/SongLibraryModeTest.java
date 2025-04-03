package com.tuneup;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Test class for SongLibraryMode
 * 
 * @author allanpaiz
 */
public class SongLibraryModeTest {
    private SongLibraryMode songLibraryMode;
    private User testUser;
    private TuneUp tuneUp;
    private ByteArrayOutputStream outContent;

    @Before
    public void setup() {
        testUser = new User("1", "user1", "password", "test@test.com", UserType.STUDENT, ExperienceLevel.BEGINNER);
        tuneUp = new TuneUp();
        songLibraryMode = new SongLibraryMode(testUser, tuneUp);

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        SongLibrary.songLibrary = new ArrayList<>();
        SongLibrary.songLibrary.add(new Song("SongA", "creator1", "artist1", List.of("C4", "D4")));
        SongLibrary.songLibrary.add(new Song("SongB", "creator2", "artist2", List.of("E4", "F4")));
        SongLibrary.isInitialized = true;
    }

    @Test
    public void testHandle() {
        // test method returns string with correct username
        songLibraryMode.handle();
        String output = outContent.toString();
        assertTrue(output.contains("Song Library mode activated for user: user1"));
    }

    @Test
    public void testHandleTerminal() {
        // test method displays
        Scanner scanner = new Scanner("4\n");  // break while loop with "4" (return to main menu)
        songLibraryMode.handleTerminal(scanner);
        String output = outContent.toString();
        assertTrue(output.contains("=== Song Library Mode ==="));
    }

    @Test
    public void testBrowseSongs() {
        // don't know why these wont work with the same scanner
        Scanner scanner = new Scanner("0\n");
        Scanner scanner2 = new Scanner("0\n");

        // test browseSongs() output contains the test song titles
        songLibraryMode.browseSongs(scanner);
        String output1 = outContent.toString();
        assertTrue(output1.contains("SongA"));
        assertTrue(output1.contains("SongB"));

        // test browseSongs() output when songLibrary is empty
        SongLibrary.songLibrary = new ArrayList<>();
        songLibraryMode.browseSongs(scanner2);
        String output2 = outContent.toString();
        assertTrue(output2.contains("No songs found in the library"));
    }

    @Test
    public void testSearchSongs() {
        // test method's output contains and does not contain song titles after searching
        Scanner scanner = new Scanner("artist1\n0\n");
        songLibraryMode.searchSongs(scanner);
        String output = outContent.toString();
        assertTrue(output.contains("SongA"));
        assertFalse(output.contains("SongB"));
    }

    @Test
    public void testGetUserProfile() {
        // test gettor returns the correct user profile
        User user = songLibraryMode.getUserProfile();
        assertNotNull(user);
        assertEquals("user1", user.getUsername());
    }

    @Test
    public void testTruncate() {
        // test null input
        String nullTest = songLibraryMode.truncate(null, 10);
        assertNull(nullTest);

        // test empty string
        String emptyTest = songLibraryMode.truncate("", 5);
        assertEquals("", emptyTest);

        // test when string is equal to maxLength
        String sameSize = songLibraryMode.truncate("Hello", 5);
        assertEquals("Hello", sameSize);

        // normal case
        String normal = songLibraryMode.truncate("HelloWorld", 7);
        assertEquals("Hell...", normal);
    }
}

// Source for OutputStream: https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println