package com.tuneup;

/**
 * InstrumentFactory class that creates instances of InstrumentStrategy based on the provided InstrumentType.
 */
public class InstrumentFactory {

    /**
     * Creates an instance of InstrumentStrategy based on the provided InstrumentType.
     * 
     * @param instrumentType The type of instrument to create.
     * @return An instance of InstrumentStrategy corresponding to the provided InstrumentType.
     * @throws IllegalArgumentException if the provided InstrumentType is unknown.
     */
    public static InstrumentStrategy createInstrument(InstrumentType instrumentType) {
        if (instrumentType == InstrumentType.PIANO) {
            return new PianoStrategy();
        } else {
            throw new IllegalArgumentException("Unknown instrument type: " + instrumentType);
        }
    }
}
