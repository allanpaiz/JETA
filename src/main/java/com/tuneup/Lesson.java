package com.tuneup;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Lesson Class representing a musical lesson.
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

    //Gets the ID of the lesson.
    public String getId() {
        return id;
    }

    //Gets the title of the lesson.
    public String getTitle() {
        return title;
    }

    //Gets the instructor of the lesson.
    public String getInstructor() {
        return instructor;
    }

    //Gets the song of the lesson.
    public Song getSong() {
        return song;
    }

    //Gets the assigned experience levels of the lesson.
    public List<ExperienceLevel> getAssignedLevels() {
        return assignedLevels;
    }

    //Gets the assigned users of the lesson.
    public List<User> getAssignedUsers() {
        return assignedUsers;
    }

    //Sets the ID of the lesson.
    public void setId(String id) {
        this.id = id;
    }

    //Sets the title of the lesson.
    public void setTitle(String title) {
        this.title = title;
    }

    //Sets the instructor of the lesson.
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    //Sets the song of the lesson.
    public void setSong(Song song) {
        this.song = song;
    }

    //Sets the assigned experience levels of the lesson.
    public void setAssignedLevels(List<ExperienceLevel> assignedLevels) {
        this.assignedLevels = assignedLevels;
    }

    //Sets the assigned users of the lesson.
    public void setAssignedUsers(List<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }


}