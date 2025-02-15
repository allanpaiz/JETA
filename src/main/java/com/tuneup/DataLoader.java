package com.tuneup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataLoader implements DataConstants {
    private static final Gson gson = new Gson();

    public static List<User> loadUsers() {
        try (FileReader reader = new FileReader(USERS_FILE)) {
            Type userListType = new TypeToken<List<User>>() {}.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            System.out.println("Error reading users: " + e.getMessage());
            return null;
        }
    }

    public static List<Song> loadLessons() {
        try (FileReader reader = new FileReader(LESSONS_FILE)) {
            Type listType = new TypeToken<ArrayList<Song>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.out.println("Error loading lessons: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}