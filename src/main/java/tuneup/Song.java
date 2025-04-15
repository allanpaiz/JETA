package tuneup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

/**
 * Represents a musical song in the TuneUp application
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
     * Full constructor with all properties
     * 
     * @param title Song title
     * @param creatorId ID of the creator
     * @param artistName Name of the artist
     * @param notes List of musical notes
     * @param tempo Tempo in beats per minute (optional)
     * @param timeSignature Time signature (optional)
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
     * Simpler constructor with default values for tempo and time signature
     * 
     * @param title Song title
     * @param creatorId ID of the creator
     * @param artistName Name of the artist
     * @param notes List of musical notes
     */
    public Song(String title, String creatorId, String artistName, List<String> notes) {
        this(title, creatorId, artistName, notes, null, null);
    }
    
    /**
     * Default constructor for Jackson deserialization
     */
    public Song() {
        this.id = UUID.randomUUID().toString();
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
     * Gets the artist name.
     * 
     * @return The artist name.
     */
    public String getArtistName() {
        return artistName;
    }
    
    /**
     * Gets the notes of the song.
     * 
     * @return The notes of the song.
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
     * @param id The ID of the song.
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Sets the title of the song.
     * 
     * @param title The title of the song.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Sets the ID of the creator of the song.
     * 
     * @param creatorId The ID of the creator of the song.
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
    
    /**
     * Sets the artist name.
     * 
     * @param artistName The artist name.
     */
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    
    /**
     * Sets the notes of the song.
     * 
     * @param notes The notes of the song.
     */
    public void setNotes(List<String> notes) {
        this.notes = notes;
    }
    
    /**
     * Sets the tempo of the song.
     * 
     * @param tempo The tempo of the song in beats per minute.
     */
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
    
    /**
     * Sets the time signature of the song.
     * 
     * @param timeSignature The time signature of the song.
     */
    public void setTimeSignature(String timeSignature) {
        this.timeSignature = timeSignature;
    }
    
    /**
     * Converts the numeric tempo to a musical tempo notation
     * 
     * @return String representation of the tempo (e.g., "Allegro", "Moderato")
     */
    public String getTempoNotation() {
        if (tempo <= 60) {
            return "Largo";
        } else if (tempo <= 80) {
            return "Adagio";
        } else if (tempo <= 100) {
            return "Andante";
        } else if (tempo <= 120) {
            return "Moderato";
        } else if (tempo <= 160) {
            return "Allegro";
        } else {
            return "Presto";
        }
    }
}