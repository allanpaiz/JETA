package com.tuneup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

/**
 * Represents a musical song with attributes such as title, creator, artist name, notes, tempo, and time signature.
 * This class provides methods to access and modify song properties.
 * 
 * @author allanpaiz
 * @author Terdoo 
 */
public class Song {
    private String id;
    private String title;
    private String creatorId;
    private String artistName;
    private List<String> notes;
    private int tempo = 120; // Default tempo (beats per minute)
    private String timeSignature = "4/4"; // Default time signature

    /**
     * Constructor for creating a Song with all properties.
     * 
     * @param title The title of the song.
     * @param creatorId The ID of the creator of the song.
     * @param artistName The name of the artist of the song.
     * @param notes The notes of the song.
     * @param tempo The tempo of the song in beats per minute (optional).
     * @param timeSignature The time signature of the song (optional).
     */
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
    
    /**
     * Constructor for creating a Song with mandatory properties.
     * 
     * @param title The title of the song.
     * @param creatorId The ID of the creator of the song.
     * @param artistName The name of the artist of the song.
     * @param notes The notes of the song.
     */
    public Song(String title, String creatorId, String artistName, List<String> notes) {
        this(title, creatorId, artistName, notes, null, null);
    }

    /**
     * Default constructor for deserialization.
     */
    public Song() {
    }

    /**
     * Gets the ID of the song.
     * 
     * @return The ID of the song.
     */
    public String getId() {
        return id;
    }
    
    /**
     * Gets the title of the song.
     * 
     * @return The title of the song.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the ID of the creator of the song.
     * 
     * @return The ID of the creator of the song.
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * Gets the name of the artist of the song.
     * 
     * @return The name of the artist of the song.
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * Gets the notes of the song.
     * 
     * @return A list of notes in the song.
     */
    public List<String> getNotes() {
        return notes;
    }
    
    /**
     * Gets the tempo of the song.
     * 
     * @return The tempo of the song in beats per minute.
     */
    public int getTempo() {
        return tempo;
    }
    
    /**
     * Gets the time signature of the song.
     * 
     * @return The time signature of the song.
     */
    public String getTimeSignature() {
        return timeSignature;
    }

    /**
     * Sets the ID of the song.
     * 
     * @param id The new ID of the song.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the title of the song.
     * 
     * @param title The new title of the song.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the ID of the creator of the song.
     * 
     * @param creatorId The new ID of the creator of the song.
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * Sets the name of the artist of the song.
     * 
     * @param artistName The new name of the artist of the song.
     */
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    /**
     * Sets the notes of the song.
     * 
     * @param notes The new list of notes for the song.
     */
    public void setNotes(List<String> notes) {
        this.notes = notes;
    }
    
    /**
     * Sets the tempo of the song.
     * 
     * @param tempo The new tempo of the song in beats per minute.
     */
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
    
    /**
     * Sets the time signature of the song.
     * 
     * @param timeSignature The new time signature of the song.
     */
    public void setTimeSignature(String timeSignature) {
        this.timeSignature = timeSignature;
    }

    /**
     * Returns the sheet music tempo notation based on the BPM tempo of the song.
     * 
     * @return A string representing the tempo notation (e.g., "Largo", "Adagio").
     */
    public String getTempoNotation() {
        if (tempo <= 60) {
            return "Largo";
        } else if (tempo > 60 && tempo <= 80) {
            return "Adagio";
        } else if (tempo > 80 && tempo <= 100) {
            return "Andante";
        } else if (tempo > 100 && tempo <= 120) {
            return "Moderato";
        } else if (tempo > 120 && tempo <= 160) {
            return "Allegro";
        } else {
            return "Presto";
        }
    }
}