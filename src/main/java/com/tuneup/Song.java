package com.tuneup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class Song {
    private String id;
    private String title;
    private String creatorId; // Change from creator to creatorId to be more explicit
    private List<String> notes;

    @JsonCreator
    public Song(
        @JsonProperty("title") String title,
        @JsonProperty("creatorId") String creatorId,
        @JsonProperty("notes") List<String> notes) {
        
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.creatorId = creatorId;
        this.notes = notes;
    }

    // For Jackson deserialization
    public Song() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }
}