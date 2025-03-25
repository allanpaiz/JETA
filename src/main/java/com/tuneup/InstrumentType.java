package com.tuneup;

/**
 * Enum representing the piano in the system.
 */
public enum InstrumentType {
    PIANO("Piano");

    private final String displayName;

    InstrumentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}