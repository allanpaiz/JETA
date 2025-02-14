package com.tuneup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicLibrary {
    private static List<Music> musicLibrary = new ArrayList<>();

    public static List<Music> getMusicLibrary() {
        loadMusicLibrary();
        return musicLibrary;
    }

    public static void addMusic(Music music) {
        musicLibrary.add(music);
    }

    public static void removeMusic(Music music) {
        musicLibrary.remove(music);
    }

    private static void loadMusicLibrary() {
        musicLibrary.clear();
        File folder = new File("sheetmusic");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.getName();
                        String title = fileName.substring(0, fileName.lastIndexOf('.'));
                        String artist = "Unknown Artist"; // Default artist name
                        musicLibrary.add(new Music(title, artist));
                    }
                }
            }
        }
    }
}