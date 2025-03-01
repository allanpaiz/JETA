package com.tuneup;

public interface InstrumentStrategy {
    void play();
    void playNote(String note);
    void stop(); // Add this method
}