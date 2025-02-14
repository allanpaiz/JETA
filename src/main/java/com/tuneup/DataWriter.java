package com.tuneup;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataWriter {
    private static final Gson gson = new Gson();

    public static void saveUsers(List<User> users) {
        try (FileWriter writer = new FileWriter(DataConstants.USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public static void saveLessons(List<Song> lessons) {
        try (FileWriter writer = new FileWriter(DataConstants.LESSONS_FILE)) {
            gson.toJson(lessons, writer);
        } catch (IOException e) {
            System.out.println("Error saving lessons: " + e.getMessage());
        }
    }
}