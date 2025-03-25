package com.tuneup;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Lesson Class - contains lesson information
 * 
 * @author jaychubb
 */
public class Lesson {
    private String id;
    private String title;
    private String instructor;
    private Song song;
    private List<ExperienceLevel> assignedLevels;
    private List<User> assignedUsers;

    public Lesson(
        @JsonProperty("id") String id,
        @JsonProperty("title") String title, 
        @JsonProperty("instructor") String instructor, 
        @JsonProperty("song") Song song, 
        @JsonProperty("assignedExperienceLevel") List<ExperienceLevel> assignedLevels, 
        @JsonProperty("assignedUsers")List<User> assignedUsers) {
        setId(id);
        setTitle(title);
        setInstructor(instructor);
        setSong(song);
        setAssignedLevels(assignedLevels);
        setAssignedUsers(assignedUsers);
    }

    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
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