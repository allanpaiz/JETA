package com.tuneup;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for SongLibrary 
 * 
 * @author allanpaiz
 */
public class SongLibraryTest {
    private SongLibrary testSongLibrary;
    private SongLibrary emptySongLibrary;

    // private List<User> testProfiles;
    // private List<User> testEmptyProfiles;

    // @Before
    // public void setup() {
    //     testSongLibrary = new ArrayList<>();
    //     testEmptyLibrary = new ArrayList<>();
    //     boolean isInitialized = false;
    //
    //     testSongLibrary.add(new Song("title1", "creator1", "artist1", [ "C4", "C4", "E4", "F4" ], 55, "4/4"));
    //     testSongLibrary.add(new Song("title2", "creator2", "artist2", [ "F4", "C4", "C4", "F4" ], 100, "3/4"));
    //     testSongLibrary.add(new Song("title3", "creator3", "artist3", [ "F4", "F4", "C4", "C4" ], 170, "6/8"));
    //
    //     testSongLibrary = new SongLibrary(testSongLibrary);
    //     emptySongLibrary = new SongLibrary(emptySongLibrary);
    // }

    @Test
    public void testGetSongLibrary() {
        // test method is returning list of songs
        List<Song> testSongs = testSongLibrary.getSongLibrary();
        assertNotNull(testSongs);
        assertEquals(9, testSongs.size());
        assertEquals("wildflower", testSongs.get(0).getTitle());
    }

    @Test
    public void testAddSong() {
        // test method is adding new song 
        Song testSong1 = new Song("title1", "creator1", "artist1", [ "C4", "C4", "E4", "F4" ], 55, "4/4");
        testSongLibrary.addSong(testSong1);
        assertNotNull(testSongLibrary.songLibrary);
        assertEquals(10, testSongLibrary.songLibrary.size());
        assertEquals("title1", testSongs.get(9).getTitle());

        // test method is adding new song without time signature and tempo constructor
        Song testSong2 = new Song("title2", "creator2", "artist2", [ "C4", "C4", "E4", "F4" ]);
        testSongLibrary.addSong(testSong2);
        assertNotNull(testSongLibrary.songLibrary);
        assertEquals(10, testSongLibrary.songLibrary.size());
        assertEquals("title1", testSongs.get(9).getTitle());
        // test song is added with defaults
        assertEquals(120, testSongs.get(9).getTempo());
        assertEquals("4/4", testSongs.get(9).getTimeSignature());
    }

    @Test
    public void testAddSong() {
        // test method is adding new song 
        Song testSong1 = new Song("title1", "creator1", "artist1", [ "C4", "C4", "E4", "F4" ], 55, "4/4");
        testSongLibrary.addSong(testSong1);
        assertNotNull(testSongLibrary.songLibrary);
        assertEquals(10, testSongLibrary.songLibrary.size());
        assertEquals("title1", testSongs.get(9).getTitle());

        // test method is adding new song without time signature and tempo constructor
        Song testSong2 = new Song("title2", "creator2", "artist2", [ "C4", "C4", "E4", "F4" ]);
        testSongLibrary.addSong(testSong2);
        assertNotNull(testSongLibrary.songLibrary);
        assertEquals(10, testSongLibrary.songLibrary.size());
        assertEquals("title1", testSongs.get(9).getTitle());
        // test song is added with defaults
        assertEquals(120, testSongs.get(9).getTempo());
        assertEquals("4/4", testSongs.get(9).getTimeSignature());
    }
}
