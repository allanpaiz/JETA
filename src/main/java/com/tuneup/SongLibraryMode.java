package com.tuneup;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SongLibraryMode implements Mode {
    private User userProfile;
    private ProfileManager profileManager;
    private Scanner scanner;

    public SongLibraryMode(User userProfile, ProfileManager profileManager) {
        this.userProfile = userProfile;
        this.profileManager = profileManager;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void handle() {
        boolean inSongLibrary = true;
        while (inSongLibrary) {
            System.out.println("\nSong Library");
            System.out.println("1. View Library");
            System.out.println("2. Add Song");
            System.out.println("3. Remove Song");
            System.out.println("4. Return to Main Menu");
            System.out.print("Please select an option (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewLibrary();
                    break;
                case 2:
                    addSong();
                    break;
                case 3:
                    removeSong();
                    break;
                case 4:
                    inSongLibrary = false;
                    break;
                default:
                    System.out.println("Invalid option selected.");
                    break;
            }
        }
    }

    private void viewLibrary() {
        List<Song> songLibrary = SongLibrary.getSongLibrary();
        if (songLibrary.isEmpty()) {
            System.out.println("The song library is empty.");
        } else {
            System.out.println("Song Library:");
            for (int i = 0; i < songLibrary.size(); i++) {
                Song song = songLibrary.get(i);
                System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist());
            }

            System.out.print("Select a song to view details (1-" + songLibrary.size() + ") or 0 to return: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice > 0 && choice <= songLibrary.size()) {
                viewSongDetails(songLibrary.get(choice - 1));
            }
        }
    }

    private void viewSongDetails(Song song) {
        System.out.println("\nSong Details:");
        System.out.println("Title: " + song.getTitle());
        System.out.println("Artist: " + song.getArtist());
        if (song.isLesson()) {
            System.out.println("Instructor: " + song.getInstructor());
            System.out.println("Assigned Students: " + song.getAssignedStudents());
            System.out.println("Assigned Experience Levels: " + song.getAssignedExperienceLevels());
        }

        System.out.print("Do you want to open the PDF file for this song? (yes/no): ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            openPdfFile(song.getFilePath());
        }
    }

    private void openPdfFile(String filePath) {
        try {
            File pdfFile = new File(filePath);
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.out.println("Desktop is not supported. Cannot open the file.");
                }
            } else {
                System.out.println("File does not exist.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while trying to open the file: " + e.getMessage());
        }
    }

    private void addSong() {
        System.out.print("Enter the title of the song: ");
        String title = scanner.nextLine();
        System.out.print("Enter the artist of the song: ");
        String artist = scanner.nextLine();
        System.out.print("Enter the file path of the PDF: ");
        String filePath = scanner.nextLine();

        Song newSong = new Song(title, artist, filePath);
        SongLibrary.addSong(newSong);
        System.out.println("Song added to the library.");
    }

    private void removeSong() {
        List<Song> songLibrary = SongLibrary.getSongLibrary();
        if (songLibrary.isEmpty()) {
            System.out.println("The song library is empty.");
            return;
        }

        System.out.println("Select the song to remove:");
        for (int i = 0; i < songLibrary.size(); i++) {
            Song song = songLibrary.get(i);
            System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist());
        }

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice < 1 || choice > songLibrary.size()) {
            System.out.println("Invalid selection.");
        } else {
            Song removedSong = songLibrary.remove(choice - 1);
            System.out.println("Removed " + removedSong.getTitle() + " by " + removedSong.getArtist() + " from the library.");
        }
    }
}