package com.tuneup;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
// import java.util.Comparator;
// import javafx.stage.Stage;

/**
 * Lesson Mode
 * Allows students to view assigned lessons & teachers to assign lessons
 * 
 * @author edwinjwood
 * @author jaychubb
 */
public class LessonMode implements Mode {
    private User userProfile;
    // private TuneUp facade;
    // private static List<Lesson> lessonLibrary = new ArrayList<>();
    // private static boolean isInitialized = false;
    // private Stage stage;
    // private TuneUpUI tuneUpUI;

    /*
    // Constructor for GUI mode
    public LessonMode(User userProfile, TuneUp facade, Stage stage, TuneUpUI tuneUpUI) {
        this.userProfile = userProfile;
        this.facade = facade;
        this.stage = stage;
        this.tuneUpUI = tuneUpUI;
    }
    */
    
    // Constructor for terminal mode
    public LessonMode(User userProfile, TuneUp facade) {
        this.userProfile = userProfile;
        // this.facade = facade;
        // this.stage = null;
        // this.tuneUpUI = null;
    }

    /**
     * Activates Lesson Mode
     */
    @Override
    public void handle() {
        // Implement the lesson functionality here for GUI - placeholder only
        System.out.println("Lesson mode activated for user: " + userProfile.getUsername());
    }
    
    /**
     * Creates main LessonMode menu in terminal
     * 
     * @param scanner
     */
    @Override
    public void handleTerminal(Scanner scanner) {
        System.out.println("\n=== Lesson Mode ===");
        System.out.println("This mode is still in beta. Currently, teachers can only assign lessons.");
        System.out.println("Students will be able to view their lessons and Teachers will be able to see lessons they've assigned in final version");
        
        boolean inMode = true;
        while (inMode) {
            if(userProfile.getRole() == UserType.STUDENT) {
                System.out.println("This mode allows you to access and complete lessons.");
                System.out.println("\nLesson Mode Options:");
                System.out.println("1. View Available Lessons");
                System.out.println("2. Start a Lesson");
                System.out.println("3. View Completed Lessons");
                System.out.println("4. Return to Main Menu");
                System.out.print("Choose an option: ");
            
                int choice = scanner.nextInt();
                scanner.nextLine();
            
                switch (choice) {
                    case 1:
                        System.out.println("\nAvailable Lessons:");
                        System.out.println("This feature is still under development in terminal version.");
                        break;
                    case 2:
                        System.out.println("\nStarting a lesson...");
                        System.out.println("This feature is still under development in terminal version.");
                        break;
                    case 3:
                        System.out.println("\nCompleted Lessons:");
                        System.out.println("This feature is still under development in terminal version.");
                        break;
                    case 4:
                        inMode = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("This mode allows you to access and assign lessons.");
                System.out.println("\nLesson Mode Options:");
                System.out.println("1. View Lessons You Have Assigned");
                System.out.println("2. Assign a New Lesson");
                System.out.println("3. Return to Main Menu");
                System.out.print("Choose an option: ");
            
                int choice = scanner.nextInt();
                scanner.nextLine();
            
                switch (choice) {
                    case 1:
                        System.out.println("\nLessons Assigned:");
                        System.out.println("This feature is still under development in terminal version.");
                        break;
                    case 2:
                        System.out.println("\nAssigning a New Lesson...");
                        assignLesson(scanner);
                        break;
                    case 3:
                        inMode = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    /**
     * Allows Students to see lesssons assigned to them
     */
    public void viewAssignedLessons() {
        // check all lesssons assigned to their experience level
        // check all lesssons assigned to their id (make sure none of them are repeated)
    }

    /**
     * Allows Teachers to assign a Lesson
     * 
     * @param scanner allows for user input
     */
    public void assignLesson(Scanner scanner) {
        System.out.println("\n=== Create New Lesson ===");
        
        // Get song title
        System.out.print("Enter song title: ");
        String title = scanner.nextLine().trim();
        
        if (title.isEmpty()) {
            System.out.println("Lesson title cannot be empty. Operation cancelled.");
            return;
        }

        // choosing a song to use in lesson
        List<Song> songs = SongLibrary.getSongLibrary();
        
        if (songs.isEmpty()) {
            System.out.println("No songs found in the library. Try creating some songs first!");
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
            return;
        }
        
        // Display all songs
        displayAllSongs(songs);

        System.out.print("\nSelect the song to be added to the lesson: ");
        Song selectedSong = null;
        try {
            int selection = scanner.nextInt();
            scanner.nextLine();
            
            if (selection > 0 && selection <= songs.size()) {
                // Chose this song
                selectedSong = songs.get(selection - 1);
            } else {
                System.out.println("Invalid selection.");
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        } catch (Exception e) {
            System.out.println("Please enter a valid number.");
            scanner.nextLine();
        }

        System.out.println("Would you like to assign this lesson to a specific experience level? (Y/N): ");
        String answer = scanner.nextLine().trim().toUpperCase();
        List<ExperienceLevel> assignedLevel = new ArrayList<>();

        if (answer.equals("Y")) {
            System.out.println("Which experience level would you like to assign this lesson to?");
            System.out.println("1. Beginner");
            System.out.println("2. Intermediate");
            System.out.println("3. Advanced");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    assignedLevel.add(ExperienceLevel.BEGINNER);
                    break;
                case 2:
                    assignedLevel.add(ExperienceLevel.INTERMEDIATE);
                    break;
                case 3:
                    assignedLevel.add(ExperienceLevel.ADVANCED);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } else {
            assignedLevel.add(ExperienceLevel.NULL);
        }

        System.out.println("Would you like to assign this lesson to a specific user? (Y/N): ");
        answer = scanner.nextLine().trim().toUpperCase();
        List<User> assignedUser = new ArrayList<>();

        if (answer.equals("Y")) {
            System.out.println("This feature is still under development in terminal version. Defaulting to none...");
        }

        String id = java.util.UUID.randomUUID().toString();
        String instructor = userProfile.getUsername();

        System.out.println("\nCreating Lesson...");
        Lesson newLesson = new Lesson(id, title, instructor, selectedSong, assignedLevel, assignedUser);
        LessonLibrary.addLesson(newLesson);
    }

    /**
     * Displays all songs in the library (sorted by title) to choose one for a Lesson
     * 
     * @param songs List of songs to display
     */
    private void displayAllSongs(List<Song> songs) {
        System.out.println("\n=== All Songs in Library (Sorted by Title) ===");
        
        // Print header
        System.out.println("\n-------------------------------------------------");
        System.out.printf("%-5s %-25s %-20s\n", "No.", "Title", "Creator ID");
        System.out.println("-------------------------------------------------");
        
        // Print each song
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            System.out.printf("%-5d %-25s %-20s\n", 
                (i+1), 
                truncate(song.getTitle(), 24), 
                truncate(song.getCreatorId(), 19));
        }
        
        System.out.println("-------------------------------------------------");
        
        // Print total number of songs
        System.out.println("\nTotal songs: " + songs.size());
    }

    /**
     * Truncates a string if it's longer than the specified length
     * 
     * @param text The text to truncate
     * @param maxLength The maximum length
     * @return Truncated string
     */
    private String truncate(String text, int maxLength) {
        if (text == null || text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength - 3) + "...";
    }
}