package com.tuneup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public class Song {
    private String id;
    private String title;
    private String creatorId;
    private String artistName;
    private List<String> notes;
    private int tempo = 120; // Default tempo (beats per minute)
    private String timeSignature = "4/4"; // Default time signature

    @JsonCreator
    public Song(
        @JsonProperty("title") String title,
        @JsonProperty("creatorId") String creatorId,
        @JsonProperty("artistName") String artistName,
        @JsonProperty("notes") List<String> notes,
        @JsonProperty("tempo") Integer tempo,
        @JsonProperty("timeSignature") String timeSignature) {
        
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.creatorId = creatorId;
        this.artistName = artistName;
        this.notes = notes;
        
        // Set optional properties with defaults if null
        this.tempo = (tempo != null) ? tempo : 120;
        this.timeSignature = (timeSignature != null) ? timeSignature : "4/4";
    }
    
    // Simpler constructor for backward compatibility
    public Song(String title, String creatorId, String artistName, List<String> notes) {
        this(title, creatorId, artistName, notes, null, null);
    }

    // For Jackson deserialization
    public Song() {
    }

    // Gets the ID of the song.
    public String getId() {
        return id;
    }
    
    //Gets the title of the song.
    public String getTitle() {
        return title;
    }

    // Gets the ID of the creator of the song.
    public String getCreatorId() {
        return creatorId;
    }

    // Gets the name of the artist of the song.
    public String getArtistName() {
        return artistName;
    }

    // Gets the notes of the song.
    public List<String> getNotes() {
        return notes;
    }
    
    // Gets the tempo of the song.
    public int getTempo() {
        return tempo;
    }
    
    // Gets the time signature of the song.
    public String getTimeSignature() {
        return timeSignature;
    }

    // Sets the ID of the song.
    public void setId(String id) {
        this.id = id;
    }

    // Sets the title of the song.
    public void setTitle(String title) {
        this.title = title;
    }

    // Sets the ID of the creator of the song.
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    // Sets the notes of the song.
    public void setNotes(List<String> notes) {
        this.notes = notes;
    }
    
    // Sets the tempo of the song.
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
    
    // Sets the time signature of the song.
    public void setTimeSignature(String timeSignature) {
        this.timeSignature = timeSignature;
    }
}