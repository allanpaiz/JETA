package com.tuneup;

public class Lesson {
    private String title;
    private String instructor;

    public Lesson(String title, String instructor) {
        this.title = title;
        this.instructor = instructor;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructor() {
        return instructor;
    }
}