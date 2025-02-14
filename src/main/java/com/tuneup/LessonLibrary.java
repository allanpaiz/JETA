package com.tuneup;

import java.util.ArrayList;
import java.util.List;

public class LessonLibrary {
    private static List<Lesson> lessons = new ArrayList<>();

    public static List<Lesson> getLessons() {
        return lessons;
    }

    public static void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public static void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }
}