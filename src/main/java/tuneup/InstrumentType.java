package tuneup;

/**
 * Enum for instrument types
 * 
 * @author edwinjwood
 */
public enum InstrumentType {
    PIANO("Piano");

    private final String displayName;

    /**
     * Constructor
     * 
     * @param displayName String instrument type
     */
    InstrumentType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Get the display name of the instrument type
     * 
     * @return String displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get the display name of the instrument type
     * 
     * @return String displayName
     */
    @Override
    public String toString() {
        return displayName;
    }
}