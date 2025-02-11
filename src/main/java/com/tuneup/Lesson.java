package com.tuneup;

public class Lesson {
    private String title;
    private SongDetails songDetails;
    private int difficultyLevel;
    private String practiceNotes;
    private String sheetMusicFile;

    public Lesson(String title, SongDetails songDetails, int difficultyLevel, 
                 String practiceNotes, String sheetMusicFile) {
        this.title = title;
        this.songDetails = songDetails;
        this.difficultyLevel = difficultyLevel;
        this.practiceNotes = practiceNotes;
        this.sheetMusicFile = sheetMusicFile;
    }

    public void display() {
        System.out.println("\nLesson Details:");
        System.out.println("Title: " + title);
        System.out.println("Sheet Music: " + sheetMusicFile);
        System.out.println("Difficulty Level: " + difficultyLevel);
        System.out.println("Practice Notes: " + practiceNotes);
        songDetails.display();
    }

    public String getTitle() {
        return title;
    }

    public String getSheetMusicFile() {
        return sheetMusicFile;
    }
}