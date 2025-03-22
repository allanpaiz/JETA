package com.tuneup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class Song {
    private String id;
    private String title;
    private String creatorId;
    private List<String> notes;
    private int tempo = 120; // Default tempo (beats per minute)
    private String timeSignature = "4/4"; // Default time signature

    @JsonCreator
    public Song(
        @JsonProperty("title") String title,
        @JsonProperty("creatorId") String creatorId,
        @JsonProperty("notes") List<String> notes,
        @JsonProperty("tempo") Integer tempo,
        @JsonProperty("timeSignature") String timeSignature) {
        
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.creatorId = creatorId;
        this.notes = notes;
        
        // Set optional properties with defaults if null
        this.tempo = (tempo != null) ? tempo : 120;
        this.timeSignature = (timeSignature != null) ? timeSignature : "4/4";
    }
    
    // Simpler constructor for backward compatibility
    public Song(String title, String creatorId, List<String> notes) {
        this(title, creatorId, notes, null, null);
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
    
    public int getTempo() {
        return tempo;
    }
    
    public String getTimeSignature() {
        return timeSignature;
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
    
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
    
    public void setTimeSignature(String timeSignature) {
        this.timeSignature = timeSignature;
    }
}