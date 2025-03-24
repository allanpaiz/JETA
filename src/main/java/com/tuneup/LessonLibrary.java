package com.tuneup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LessonLibrary implements DataConstants {
    private static List<Lesson> lessonLibrary = new ArrayList<>();
    private static boolean isInitialized = false; 

    /**
     * Gets the lesson library sorted by title, loading it first if needed
     * @return List of lessons in the library sorted by title
     */
    public static List<Lesson> getLessonLibrary() {
        if (!isInitialized) {
            loadLessonLibrary();
        }
        
        // Sort songs by title before returning
        List<Lesson> sortedLessons = new ArrayList<>(lessonLibrary);
        sortedLessons.sort(Comparator.comparing(Lesson::getTitle));
        
        return sortedLessons;
    }

    /**
     * Adds a song to the lesson library
     * @param lesson The lesson to add
     */
    public static void addLesson(Lesson lesson) {
        if (!isInitialized) {
            loadLessonLibrary();
        }
        lessonLibrary.add(lesson);
        saveLessonLibrary();
    }

    /**
     * Loads lessons from the lessons.json file using DataLoader
     */
    private static void loadLessonLibrary() {
        // Load songs using DataLoader
        lessonLibrary = DataLoader.loadLessons();
        isInitialized = true;
    }

    /**
     * Saves the song library to the songs.json file
     */
    private static void saveLessonLibrary() {
        DataWriter.saveLessons(lessonLibrary);
    }
    
    /**
     * Reloads the song library (useful after external changes)
     */
    public static void refresh() {
        isInitialized = false;
        loadLessonLibrary();
    }
}
