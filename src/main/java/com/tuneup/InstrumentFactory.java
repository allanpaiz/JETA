package com.tuneup;

public class InstrumentFactory {
    public static InstrumentStrategy createInstrument(InstrumentType instrumentType) {
        if (instrumentType == InstrumentType.PIANO) {
            return new PianoStrategy();
        } else if (instrumentType == InstrumentType.GUITAR) {
            return new GuitarStrategy();
        } else {
            throw new IllegalArgumentException("Unknown instrument type: " + instrumentType);
        }
    }
}
