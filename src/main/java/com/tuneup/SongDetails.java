package com.tuneup;

public class SongDetails {
    private String timeSignature;
    private String key;
    private String yearComposed;

    public SongDetails(String timeSignature, String key, String yearComposed) {
        this.timeSignature = timeSignature;
        this.key = key;
        this.yearComposed = yearComposed;
    }
    // ... rest of the class

    public void display() {
        System.out.println("\nSong Attributes:");
        System.out.println("Time Signature: " + timeSignature);
        System.out.println("Key: " + key);
        System.out.println("Year Composed: " + yearComposed);
    }
}
