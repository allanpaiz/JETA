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
        this.filePath = filePath;
        this.assignedStudents = new ArrayList<>();
        this.assignedExperienceLevels = new ArrayList<>();
    }

    public Song(String title, String artist, String instructor, String filePath) {
        this.title = title;
        this.artist = artist;
        this.instructor = instructor;
        this.filePath = filePath;
        this.isLesson = true;
        this.assignedStudents = new ArrayList<>();
        this.assignedExperienceLevels = new ArrayList<>();
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

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}