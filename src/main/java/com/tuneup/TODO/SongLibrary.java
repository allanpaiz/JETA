package com.tuneup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SongLibrary {
    private static List<Song> songLibrary = new ArrayList<>();

    public static List<Song> getSongLibrary() {
        loadSongLibrary();
        return songLibrary;
    }

    public static void addSong(Song song) {
        songLibrary.add(song);
    }

    public static void removeSong(Song song) {
        songLibrary.remove(song);
    }

    private static void loadSongLibrary() {
        songLibrary.clear();
        File folder = new File(DataConstants.SONGS_FOLDER);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.getName();
                        String title = fileName.substring(0, fileName.lastIndexOf('.'));
                        String artist = "Unknown Artist"; // Default artist name
                        String filePath = new File(DataConstants.SONGS_FOLDER, fileName).getPath();
                        songLibrary.add(new Song(title, artist, filePath));
                    }
                }
            }
        }
    }
}