package com.tuneup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Keeps a 'library' of songs in the system, interacts with the DataLoader/DataWriter to access songs
 */
public class SongLibrary implements DataConstants {
    private static List<Song> songLibrary = new ArrayList<>();
    private static boolean isInitialized = false;

    /**
     * Gets the song library sorted by title, loading it first if needed
     * @return List of songs in the library sorted by title
     */
    public static List<Song> getSongLibrary() {
        if (!isInitialized) {
            loadSongLibrary();
        }
        
        // Sort songs by title before returning
        List<Song> sortedSongs = new ArrayList<>(songLibrary);
        sortedSongs.sort(Comparator.comparing(Song::getTitle));
        
        return sortedSongs;
    }

    /**
     * Adds a song to the library
     * @param song The song to add
     */
    public static void addSong(Song song) {
        if (!isInitialized) {
            loadSongLibrary();
        }
        songLibrary.add(song);
        saveSongLibrary();
    }

    /**
     * Loads songs from the songs.json file using DataLoader
     */
    private static void loadSongLibrary() {
        // Load songs using DataLoader
        songLibrary = DataLoader.loadSongs();
        isInitialized = true;
    }

    /**
     * Saves the song library to the songs.json file
     */
    private static void saveSongLibrary() {
        DataWriter.saveSongs(songLibrary);
    }
    
    /**
     * Reloads the song library (useful after external changes)
     */
    public static void refresh() {
        isInitialized = false;
        loadSongLibrary();
    }
}