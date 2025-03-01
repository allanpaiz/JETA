package com.tuneup;

public class InstrumentFactory {
    public static InstrumentStrategy createInstrument(InstrumentType instrumentType) {
        if (instrumentType == InstrumentType.PIANO) {
            return new PianoStrategy();
        } else {
            throw new IllegalArgumentException("Unknown instrument type: " + instrumentType);
        }
    }
}
