package com.tuneup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * {@code DataLoader} loads data from their JSON files.
 */
public class DataLoader implements DataConstants {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Loads and returns the list of {@code Users}
     * 
     * @return List of {@code User} objects
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
     * Loads and returns the list of {@code Songs}
     * 
     * @return List of {@code Song} objects
     */
    public static List<Song> loadLessons() {
        try (FileReader reader = new FileReader(LESSONS_FILE)) {
            List<Song> lessons = mapper.readValue(reader, new TypeReference<List<Song>>() {});
            System.out.println("Loaded " + lessons.size() + " lessons from lessons.json"); // Debug statement
            return lessons;
        } catch (IOException e) {
            System.out.println("Error loading lessons: " + e.getMessage());
            return null;
        }
    }
}