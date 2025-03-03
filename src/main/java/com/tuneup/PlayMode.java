package com.tuneup;

import java.util.Scanner;

public class PlayMode implements Mode {
    private User userProfile;
    private InstrumentStrategy instrument;
    private Scanner scanner;
    private boolean isPlaying;

    public PlayMode(User userProfile, InstrumentStrategy instrument) {
        this.userProfile = userProfile;
        this.instrument = instrument;
        this.scanner = new Scanner(System.in);
        this.isPlaying = false;
    }

    @Override
    public void handle() {
        boolean inPlayMode = true;
        while (inPlayMode) {
            System.out.println("\nPlay Mode");
            System.out.println("1. Play Note");
            System.out.println("2. Stop Playing");
            System.out.println("3. Return to Main Menu");
            System.out.print("Please select an option (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    playNotes();
                    break;
                case 2:
                    stopPlaying();
                    break;
                case 3:
                    inPlayMode = false;
                    break;
                default:
                    System.out.println("Invalid option selected.");
                    break;
            }
        }
    }

    private void playNotes() {
        boolean continuePlaying = true;
        while (continuePlaying) {
            System.out.print("Enter note to play (C, D, E, F, G, A, B, HIGH C) or 'STOP' to stop: ");
            String note = scanner.nextLine().toUpperCase();
            if (note.equals("STOP")) {
                continuePlaying = false;
                stopPlaying();
            } else {
                instrument.playNote(note);
                isPlaying = true;
            }
        }
    }

    private void stopPlaying() {
        if (isPlaying) {
            instrument.stop();
            isPlaying = false;
            System.out.println("Stopped playing.");
        } else {
            System.out.println("No note is currently being played.");
        }
    }
}