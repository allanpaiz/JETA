package com.tuneup;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;

public class DataWriter implements DataConstants {
    private static final Gson gson = new Gson();

    public static void saveUsers(List<User> users) {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public static void saveLessons(List<Song> lessons) {
        // Convert absolute paths to relative paths
        for (Song lesson : lessons) {
            String relativePath = Paths.get("").toUri().relativize(Paths.get(lesson.getFilePath()).toUri()).getPath();
            lesson.setFilePath(relativePath);
        }

        try (FileWriter writer = new FileWriter(LESSONS_FILE)) {
            gson.toJson(lessons, writer);
        } catch (IOException e) {
            System.out.println("Error saving lessons: " + e.getMessage());
        }
    }
}