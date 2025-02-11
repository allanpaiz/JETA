package com.tuneup;

import java.util.Scanner;

public class LearningArea {
    private String learningID;
    private UserProfile userProfile;
    private Mode mode;
    private Scanner scanner;

    public LearningArea(UserProfile userProfile, ProfileManager profileManager) {
        this.userProfile = userProfile;
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nLearning Area Menu");
            System.out.println("1. Music Library Mode");
            System.out.println("2. Lesson Mode");
            System.out.println("3. Exit");
            System.out.print("Please select a mode (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Entering Music Library Mode...");
                    mode = new MusicLibraryMode();
                    mode.handle();
                    break;
                case 2:
                    System.out.println("Entering Lesson Mode...");
                    mode = new LessonMode(userProfile, null);
                    mode.handle();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option selected.");
                    break;
            }
        }
    }

    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}