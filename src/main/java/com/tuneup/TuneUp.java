package com.tuneup;

import java.util.Scanner;

public class TuneUp {
    private static Scanner scanner = new Scanner(System.in);
    private static UserProfile userProfile;
    private static ProfileManager profileManager;

    public static void main(String[] args) {
        System.out.println("Welcome to TuneUp!");
        profileManager = new ProfileManager();
        createProfile();
        while (true) {
            if (!showMainMenu()) {
                break;
            }
        }
        scanner.close();
    }

    private static void createProfile() {
        System.out.println("\nLet's create your profile!");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        // Check if profile already exists
        UserProfile existingProfile = profileManager.getProfile(username);
        if (existingProfile != null) {
            System.out.println("Welcome back, " + username + "!");
            userProfile = existingProfile;
            return;
        }

        System.out.println("\nAre you a:");
        System.out.println("1. Teacher");
        System.out.println("2. Student");
        System.out.print("Please select your role (1-2): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        UserType userType;
        switch (choice) {
            case 1:
                userType = UserType.TEACHER;
                System.out.println("Welcome, Teacher " + username + "!");
                break;
            case 2:
                userType = UserType.STUDENT;
                System.out.println("Welcome, Student " + username + "!");
                break;
            default:
                System.out.println("Invalid selection. Defaulting to Student.");
                userType = UserType.STUDENT;
                break;
        }
        
        userProfile = new UserProfile(username, userType);
        profileManager.addProfile(userProfile);
    }

    private static boolean showMainMenu() {
        System.out.println("\nTuneUp Main Menu");
        System.out.println("1. Learning Area");
        System.out.println("2. Compose Area");
        System.out.println("3. Exit Application");
        System.out.print("Please select an area (1-3): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (choice) {
            case 1:
                System.out.println("Entering Learning Area...");
                LearningArea learningArea = new LearningArea(userProfile, profileManager);
                learningArea.displayMenu();
                return true;
            case 2:
                System.out.println("Entering Compose Area...");
                ComposeArea composeArea = new ComposeArea(userProfile);
                composeArea.displayMenu();
                return true;
            case 3:
                System.out.println("Thank you for using TuneUp!");
                return false;
            default:
                System.out.println("Invalid option selected.");
                return true;
        }
    }
}