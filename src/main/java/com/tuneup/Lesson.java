package com.tuneup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Lesson {
    private String title;
    private String content;

    public Lesson(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public static List<Lesson> loadLessons() {
        try (FileReader reader = new FileReader(DataConstants.LESSONS_FILE)) {
            Type lessonListType = new TypeToken<List<Lesson>>() {}.getType();
            return new Gson().fromJson(reader, lessonListType);
        } catch (IOException e) {
            System.out.println("Error reading lessons: " + e.getMessage());
            return null;
        }
    }
}