package com.tuneup;

public interface InstrumentStrategy {
    void play();
    default void playNote(String note) {
        System.out.println("Playing " + note);
    }
}