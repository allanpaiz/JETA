package com.tuneup;

public class InstrumentType {
    public static final InstrumentType PIANO = new InstrumentType("Piano");
    public static final InstrumentType GUITAR = new InstrumentType("Guitar");

    private final String displayName;

    private InstrumentType(String displayName) {
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
