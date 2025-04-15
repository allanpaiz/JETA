package tuneup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * LessonLibrary
 * organizes lessons & interacts with DataReader & DataWriter
 * @author jaychubb
 */
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
        // Load lessons using DataLoader
        lessonLibrary = DataLoader.loadLessons();

        /** if lessonLibrary is null create an empty list
         *  @author allanpaiz
         */  
        if (lessonLibrary == null) {
            lessonLibrary = new ArrayList<>();
        }
        
        isInitialized = true;
    }

    /**
     * Saves the lesson library to the lessons.json file
     */
    private static void saveLessonLibrary() {
        DataWriter.saveLessons(lessonLibrary);
    }
    
    /**
     * Reloads the lesson library (useful after external changes)
     */
    public static void refresh() {
        isInitialized = false;
        loadLessonLibrary();
    }
}
