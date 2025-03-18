package com.tuneup;

import java.util.List;
import java.util.UUID;

public class Lesson {
    private String id;
    private String title;
    private String instructor;
    private Song song;
    private List<ExperienceLevel> assignedLevels;
    private List<User> assignedUsers;
    // not including LessonDetails for rn because it could probably be condensed just into this fileS

    public Lesson(String title, String instructor, Song song, List<ExperienceLevel> assignedLevels, List<User> assignedUsers) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.instructor = instructor;
        this.song = song;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructor() {
        return instructor;
    }

    public Song getSong() {
        return song;
    }

    public List<ExperienceLevel> getAssignedLevels() {
        return assignedLevels;
    }

    public List<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public void setAssignedLevels(List<ExperienceLevel> assignedLevels) {
        this.assignedLevels = assignedLevels;
    }

    public void setAssignedUsers(List<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }


}