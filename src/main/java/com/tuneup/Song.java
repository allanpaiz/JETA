package com.tuneup;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private String title;
    private String artist;
    private boolean isLesson;
    private String instructor;
    private List<String> assignedStudents;
    private List<ExperienceLevel> assignedExperienceLevels;
    private String filePath;

    public Song(String title, String artist, String filePath) {
        this.title = title;
        this.artist = artist;
        this.isLesson = false;
        this.instructor = "";
        this.assignedStudents = new ArrayList<>();
        this.assignedExperienceLevels = new ArrayList<>();
        this.filePath = filePath;
    }

    public Song(String title, String artist, String instructor, String filePath) {
        this.title = title;
        this.artist = artist;
        this.isLesson = true;
        this.instructor = instructor;
        this.assignedStudents = new ArrayList<>();
        this.assignedExperienceLevels = new ArrayList<>();
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public boolean isLesson() {
        return isLesson;
    }

    public String getInstructor() {
        return instructor;
    }

    public List<String> getAssignedStudents() {
        return assignedStudents;
    }

    public List<ExperienceLevel> getAssignedExperienceLevels() {
        return assignedExperienceLevels;
    }

    public String getFilePath() {
        return filePath;
    }

    public void assignStudent(String studentUsername) {
        assignedStudents.add(studentUsername);
    }

    public void assignExperienceLevel(ExperienceLevel experienceLevel) {
        assignedExperienceLevels.add(experienceLevel);
    }
}