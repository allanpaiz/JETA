package com.tuneup;

import java.util.List;
import java.util.Scanner;

public class MusicLibraryMode implements Mode {
    private User userProfile;
    private ProfileManager profileManager;
    private Scanner scanner;

    public MusicLibraryMode(User userProfile, ProfileManager profileManager) {
        this.userProfile = userProfile;
        this.profileManager = profileManager;
        this.scanner = new Scanner(System.in);
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
        List<Music> musicLibrary = MusicLibrary.getMusicLibrary();
        if (musicLibrary.isEmpty()) {
            System.out.println("The music library is empty.");
        } else {
            System.out.println("Music Library:");
            for (int i = 0; i < musicLibrary.size(); i++) {
                Music music = musicLibrary.get(i);
                System.out.println((i + 1) + ". " + music.getTitle() + " by " + music.getArtist());
            }
        }
    }

    private void addMusic() {
        System.out.print("Enter the title of the music: ");
        String title = scanner.nextLine();
        System.out.print("Enter the artist of the music: ");
        String artist = scanner.nextLine();

        Music newMusic = new Music(title, artist);
        MusicLibrary.addMusic(newMusic);
        System.out.println("Music added to the library.");
    }

    private void removeMusic() {
        List<Music> musicLibrary = MusicLibrary.getMusicLibrary();
        if (musicLibrary.isEmpty()) {
            System.out.println("The music library is empty.");
            return;
        }

        System.out.println("Select the music to remove:");
        for (int i = 0; i < musicLibrary.size(); i++) {
            Music music = musicLibrary.get(i);
            System.out.println((i + 1) + ". " + music.getTitle() + " by " + music.getArtist());
        }

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice < 1 || choice > musicLibrary.size()) {
            System.out.println("Invalid selection.");
        } else {
            Music removedMusic = musicLibrary.remove(choice - 1);
            System.out.println("Removed " + removedMusic.getTitle() + " by " + removedMusic.getArtist() + " from the library.");
        }
    }
}