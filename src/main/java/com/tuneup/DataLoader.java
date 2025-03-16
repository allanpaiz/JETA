package com.tuneup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader implements DataConstants {
    private static final ObjectMapper mapper = new ObjectMapper();

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