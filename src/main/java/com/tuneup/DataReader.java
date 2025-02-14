package com.tuneup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class DataReader {
    private static final Gson gson = new Gson();

    public static List<UserProfile> readUsers() {
        try (FileReader reader = new FileReader(DataConstants.USERS_FILE)) {
            Type userListType = new TypeToken<List<UserProfile>>() {}.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            System.out.println("Error reading users: " + e.getMessage());
            return null;
        }
    }

    public static List<Lesson> readLessons() {
        try (FileReader reader = new FileReader(DataConstants.LESSONS_FILE)) {
            Type lessonListType = new TypeToken<List<Lesson>>() {}.getType();
            return gson.fromJson(reader, lessonListType);
        } catch (IOException e) {
            System.out.println("Error reading lessons: " + e.getMessage());
            return null;
        }
    }
}