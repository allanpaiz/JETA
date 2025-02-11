package com.tuneup;

import java.util.Scanner;

public class PlayMode implements Mode {
    private UserProfile userProfile;
    private InstrumentStrategy instrument;
    private Scanner scanner;
    private boolean isPlaying;

    public PlayMode(UserProfile userProfile, InstrumentStrategy instrument) {
        this.userProfile = userProfile;
        this.instrument = instrument;
        scanner = new Scanner(System.in);
    }

    @Override
    public void handle() {
        System.out.println("\nPlay Mode - Press keys to play notes");
        System.out.println("Available keys:");
        System.out.println("a - C note    s - D note    d - E note");
        System.out.println("f - F note    g - G note    h - A note");
        System.out.println("j - B note    k - High C");
        System.out.println("q - Quit play mode");

        isPlaying = true;
        while (isPlaying) {
            String input = scanner.nextLine().toLowerCase();
            
            if (input.length() > 0) {
                char key = input.charAt(0);
                switch (key) {
                    case 'a':
                        playNote("C");
                        break;
                    case 's':
                        playNote("D");
                        break;
                    case 'd':
                        playNote("E");
                        break;
                    case 'f':
                        playNote("F");
                        break;
                    case 'g':
                        playNote("G");
                        break;
                    case 'h':
                        playNote("A");
                        break;
                    case 'j':
                        playNote("B");
                        break;
                    case 'k':
                        playNote("High C");
                        break;
                    case 'q':
                        isPlaying = false;
                        // Clean up resources when quitting
                        if (instrument instanceof PianoStrategy) {
                            ((PianoStrategy) instrument).close();
                        }
                        break;
                    default:
                        System.out.println("Invalid key pressed");
                }
            }
        }
    }

    private void playNote(String note) {
        if (instrument != null) {
            instrument.playNote(note);
        }
    }

    public void close() {
        if (scanner != null) {
            scanner.close();
        }
        if (instrument instanceof PianoStrategy) {
            ((PianoStrategy) instrument).close();
        }
    }
}