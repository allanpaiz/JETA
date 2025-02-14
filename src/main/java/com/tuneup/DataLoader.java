package com.tuneup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class DataLoader {
    private static final Gson gson = new Gson();

    public static List<User> loadUsers() {
        try (FileReader reader = new FileReader(DataConstants.USERS_FILE)) {
            Type userListType = new TypeToken<List<User>>() {}.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            System.out.println("Error reading users: " + e.getMessage());
            return null;
        }
    }

    public static void saveUsers(List<User> users) {
        try (FileWriter writer = new FileWriter(DataConstants.USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public static List<Lesson> loadLessons() {
        try (FileReader reader = new FileReader(DataConstants.LESSONS_FILE)) {
            Type lessonListType = new TypeToken<List<Lesson>>() {}.getType();
            return gson.fromJson(reader, lessonListType);
        } catch (IOException e) {
            System.out.println("Error reading lessons: " + e.getMessage());
            return null;
        }
    }

    public static void saveLessons(List<Lesson> lessons) {
        try (FileWriter writer = new FileWriter(DataConstants.LESSONS_FILE)) {
            gson.toJson(lessons, writer);
        } catch (IOException e) {
            System.out.println("Error saving lessons: " + e.getMessage());
        }
    }
}