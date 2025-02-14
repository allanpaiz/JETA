package com.tuneup;

import java.util.Scanner;

public class MusicLibraryMode implements Mode {
    private UserProfile userProfile;
    private ProfileManager profileManager;
    private Scanner scanner;

    public MusicLibraryMode(UserProfile userProfile, ProfileManager profileManager) {
        this.userProfile = userProfile;
        this.profileManager = profileManager;
        scanner = new Scanner(System.in);
    }

    @Override
    public void handle() {
        boolean inMusicLibrary = true;
        while (inMusicLibrary) {
            System.out.println("\nMusic Library Mode");
            System.out.println("1. View Library");
            System.out.println("2. Add Music");
            System.out.println("3. Remove Music");
            System.out.println("4. Return to Main Menu");
            System.out.print("Please select an option (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewLibrary();
                    break;
                case 2:
                    addMusic();
                    break;
                case 3:
                    removeMusic();
                    break;
                case 4:
                    inMusicLibrary = false;
                    break;
                default:
                    System.out.println("Invalid option selected.");
                    break;
            }
        }
    }

    private void viewLibrary() {
        // Implementation for viewing the music library
    }

    private void addMusic() {
        // Implementation for adding music to the library
    }

    private void removeMusic() {
        // Implementation for removing music from the library
    }
}