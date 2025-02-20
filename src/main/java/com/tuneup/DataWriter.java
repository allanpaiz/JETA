package com.tuneup;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataWriter implements DataConstants {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveUsers(List<User> users) {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public static void saveLessons(List<Song> lessons) {
        try (FileWriter writer = new FileWriter(LESSONS_FILE)) {
            gson.toJson(lessons, writer);
        } catch (IOException e) {
            System.out.println("Error saving lessons: " + e.getMessage());
        }
    }
}