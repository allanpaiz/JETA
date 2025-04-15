package tuneup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DataLoader class that handles loading data from JSON files.
 * It uses Jackson ObjectMapper to parse JSON data into Java objects.
 * Implements DataConstants for file path constants.
 * 
 * @see com.fasterxml.jackson.databind.ObjectMapper
 * @see com.fasterxml.jackson.core.type.TypeReference
 */
public class DataLoader implements DataConstants {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Loads user profiles from the JSON file specified in USERS_FILE.
     * 
     * @return List of User objects if loading is successful, null otherwise.
     */
    public static List<User> loadUsers() {
        try (FileReader reader = new FileReader(USERS_FILE)) {
            List<User> users = mapper.readValue(reader, new TypeReference<List<User>>() {});
            System.out.println("Loaded " + users.size() + " users from users.json"); // Debug statement
            for (User user : users) {
                System.out.println("User: " + user.getUsername()); // Debug statement
            }
            return users;
        } catch (IOException e) {
            System.out.println("Error reading users: " + e.getMessage());
            return null;
        }
    }

    /**
     * Loads songs from the JSON file specified in SONGS_FILE.
     * 
     * @return List of Song objects if loading is successful, an empty list otherwise.
     */
    public static List<Song> loadSongs() {
        try (FileReader reader = new FileReader(SONGS_FILE)) {
            List<Song> songs = mapper.readValue(reader, new TypeReference<List<Song>>() {});
            System.out.println("Loaded " + songs.size() + " songs from songs.json"); // Debug statement
            return songs;
        } catch (IOException e) {
            System.out.println("Error loading songs: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Loads lessons from the JSON file specified in LESSONS_FILE.
     * 
     * @return List of Lesson objects if loading is successful, null otherwise.
     */
    public static List<Lesson> loadLessons() {
        try (FileReader reader = new FileReader(LESSONS_FILE)) {
            List<Lesson> lessons = mapper.readValue(reader, new TypeReference<List<Lesson>>() {});
            System.out.println("Loaded " + lessons.size() + " lessons from lessons.json"); // Debug statement
            return lessons;
        } catch (IOException e) {
            System.out.println("Error loading lessons: " + e.getMessage());
            return null;
        }
    }
}