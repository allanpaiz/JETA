package com.tuneup;

// import javafx.stage.Stage;  // Comment out JavaFX import
import java.util.Scanner;

public class LessonMode implements Mode {
    private User userProfile;
    private TuneUp facade;
    // private Stage stage;      // Comment out JavaFX field
    // private TuneUpUI tuneUpUI;  // Comment out JavaFX field

    /* Comment out GUI constructor
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
        this.facade = facade;
        // this.stage = null;      // Comment out JavaFX reference
        // this.tuneUpUI = null;   // Comment out JavaFX reference
    }

    @Override
    public void handle() {
        // Implement the lesson functionality here for GUI - placeholder only
        System.out.println("Lesson mode activated for user: " + userProfile.getUsername());
        // JavaFX specific code would go here but is commented out for now
    }
    
    @Override
    public void handleTerminal(Scanner scanner) {
        System.out.println("\n=== Lesson Mode ===");
        System.out.println("This mode allows you to access and complete lessons.");
        
        boolean inMode = true;
        while (inMode) {
            System.out.println("\nLesson Mode Options:");
            System.out.println("1. View Available Lessons");
            System.out.println("2. Start a Lesson");
            System.out.println("3. View Completed Lessons");
            System.out.println("4. Return to Main Menu");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            
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
        }
    }
}