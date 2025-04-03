package com.tuneup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * SongLibrary class that manages a collection of songs in the system.
 * 
 * This class interacts with the {@link DataLoader} and {@link DataWriter} to load and save songs
 * from/to the `songs.json` file. It provides methods to retrieve, add, and refresh the song library.
 * 
 * @author Terdoo
 */
public class SongLibrary implements DataConstants {
    public static List<Song> songLibrary = new ArrayList<>();
    public static boolean isInitialized = false;

    /**
     * Gets the song library sorted by title.
     * 
     * If the library is not yet initialized, it will load the songs from the `songs.json` file.
     * 
     * @return A list of songs in the library, sorted by title.
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
     * Adds a song to the library.
     * 
     * If the library is not yet initialized, it will load the songs first. After adding the song,
     * the library is saved to the `songs.json` file.
     * 
     * @param song The {@link Song} object to add to the library.
     */
    public static void addSong(Song song) {
        if (!isInitialized) {
            loadSongLibrary();
        }
        songLibrary.add(song);
        saveSongLibrary();
    }

    /**
     * Loads songs from the `songs.json` file using the {@link DataLoader}.
     * 
     * This method initializes the song library by reading the data from the JSON file.
     */
    public static void loadSongLibrary() {
        // Load songs using DataLoader
        songLibrary = DataLoader.loadSongs();
        isInitialized = true;
    }

    /**
     * Saves the song library to the `songs.json` file using the {@link DataWriter}.
     * 
     * This method writes the current state of the song library to the JSON file.
     */
    public static void saveSongLibrary() {
        DataWriter.saveSongs(songLibrary);
    }
    
    /**
     * Reloads the song library from the `songs.json` file.
     * 
     * This method is useful if external changes are made to the song library, ensuring the
     * in-memory library is updated with the latest data from the JSON file.
     */
    public static void refresh() {
        isInitialized = false;
        loadSongLibrary();
    }
}