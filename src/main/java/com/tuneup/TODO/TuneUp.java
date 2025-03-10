package com.tuneup;

import java.util.Scanner;

public class TuneUp {
    private static Scanner scanner = new Scanner(System.in);
    private static User userProfile;
    private static ProfileManager profileManager;

    public static void main(String[] args) {
        // Ensure the json folder exists
        FileUtils.ensureDataFolderExists();

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
        User existingProfile = profileManager.getProfile(username);
        if (existingProfile != null) {
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            if (existingProfile.verifyPassword(password)) {
                System.out.println("Welcome back, " + username + "!");
                userProfile = existingProfile;
                return;
            } else {
                System.out.println("Incorrect password. Please try again.");
                createProfile(); // Retry profile creation
                return;
            }
        }

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.println("\nAre you a:");
        System.out.println("1. Teacher");
        System.out.println("2. Student");
        System.out.print("Please select your role (1-2): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        String role;
        ExperienceLevel experienceLevel;
        switch (choice) {
            case 1:
                role = "Teacher";
                experienceLevel = ExperienceLevel.ADVANCED;
                System.out.println("Welcome, Teacher " + username + "!");
                break;
            case 2:
                role = "Student";
                System.out.println("Welcome, Student " + username + "!");
                System.out.println("\nSelect your experience level:");
                System.out.println("1. Beginner");
                System.out.println("2. Intermediate");
                System.out.println("3. Advanced");
                System.out.print("Please select your experience level (1-3): ");
                
                int expChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (expChoice) {
                    case 1:
                        experienceLevel = ExperienceLevel.BEGINNER;
                        break;
                    case 2:
                        experienceLevel = ExperienceLevel.INTERMEDIATE;
                        break;
                    case 3:
                        experienceLevel = ExperienceLevel.ADVANCED;
                        break;
                    default:
                        System.out.println("Invalid selection. Defaulting to Beginner.");
                        experienceLevel = ExperienceLevel.BEGINNER;
                        break;
                }
                break;
            default:
                System.out.println("Invalid selection. Defaulting to Student.");
                role = "Student";
                experienceLevel = ExperienceLevel.BEGINNER;
                break;
        }
        
        userProfile = new User(username, password, email, role, experienceLevel);
        profileManager.addProfile(userProfile);
    }

    private static boolean showMainMenu() {
        System.out.println("\nTuneUp Main Menu");
        System.out.println("1. Song Library");
        System.out.println("2. Lessons");
        System.out.println("3. Create");
        System.out.println("4. Play");
        System.out.println("5. Exit Application");
        System.out.print("Please select an area (1-5): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        switch (choice) {
            case 1:
                enterSongLibrary();
                return true;
            case 2:
                enterLessons();
                return true;
            case 3:
                enterCreate();
                return true;
            case 4:
                enterPlay();
                return true;
            case 5:
                System.out.println("Thank you for using TuneUp!");
                return false;
            default:
                System.out.println("Invalid option selected.");
                return true;
        }
    }

    private static void enterSongLibrary() {
        System.out.println("Entering Song Library...");
        Mode mode = new SongLibraryMode(userProfile, profileManager);
        mode.handle();
    }

    private static void enterLessons() {
        System.out.println("Entering Lessons...");
        Mode mode = new LessonMode(userProfile, profileManager);
        mode.handle();
    }

    private static void enterCreate() {
        System.out.println("Entering Create...");
        Mode mode = new CreateMode(userProfile, InstrumentFactory.createInstrument(InstrumentType.PIANO));
        mode.handle();
    }

    private static void enterPlay() {
        System.out.println("Entering Play...");
        Mode mode = new PlayMode(userProfile, InstrumentFactory.createInstrument(InstrumentType.PIANO));
        mode.handle();
    }
}