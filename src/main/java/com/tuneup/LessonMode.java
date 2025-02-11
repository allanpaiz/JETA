package com.tuneup;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LessonMode implements Mode {
    private String lessonModeID;
    private List<Lesson> lessons;
    private Map<String, List<Lesson>> studentAssignments;
    private Scanner scanner;
    private UserProfile userProfile;
    private static final String SHEET_MUSIC_PATH = "sheetmusic/";
    private ProfileManager profileManager; // Add this field

    public LessonMode(UserProfile userProfile, ProfileManager profileManager) {
        this.userProfile = userProfile;
        this.profileManager = profileManager;
        scanner = new Scanner(System.in);
        lessons = new ArrayList<>();
        studentAssignments = new HashMap<>();
    }

    @Override
    public void handle() {
        while (true) {
            System.out.println("\nLesson Mode");
            if (userProfile.getUserType() == UserType.TEACHER) {
                System.out.println("1. Create New Lesson");
                System.out.println("2. View Lessons");
                System.out.println("3. Assign Lesson");
                System.out.println("4. Return to Learning Area");
                System.out.print("Select an option (1-4): ");
            } else {
                System.out.println("1. View My Lessons");
                System.out.println("2. Return to Learning Area");
                System.out.print("Select an option (1-2): ");
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (userProfile.getUserType() == UserType.TEACHER) {
                switch (choice) {
                    case 1:
                        createLesson();
                        break;
                    case 2:
                        viewLessons();
                        break;
                    case 3:
                        assignLesson();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid option selected.");
                }
            } else {
                switch (choice) {
                    case 1:
                        viewAssignedLessons();
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("Invalid option selected.");
                }
            }
        }
    }

        private void assignLesson() {
        if (lessons.isEmpty()) {
            System.out.println("\nNo lessons available to assign.");
            return;
        }

        List<UserProfile> students = profileManager.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("\nNo students found in profiles.");
            return;
        }

        // Display available students
        System.out.println("\nAvailable Students:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getUsername());
        }

        // Select multiple students
        List<String> selectedStudents = new ArrayList<>();
        boolean selecting = true;

        while (selecting) {
            System.out.print("\nSelect student number (0 to finish): ");
            try {
                int studentChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (studentChoice == 0) {
                    selecting = false;
                } else if (studentChoice > 0 && studentChoice <= students.size()) {
                    String username = students.get(studentChoice - 1).getUsername();
                    if (!selectedStudents.contains(username)) {
                        selectedStudents.add(username);
                        System.out.println("Selected: " + username);
                    } else {
                        System.out.println("Student already selected.");
                    }
                } else {
                    System.out.println("Invalid selection.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }

        if (selectedStudents.isEmpty()) {
            System.out.println("No students selected. Assignment cancelled.");
            return;
        }

        // Display available lessons
        System.out.println("\nAvailable Lessons:");
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println((i + 1) + ". " + lessons.get(i).getTitle());
        }

        // Select lesson to assign
        System.out.print("Select lesson to assign (1-" + lessons.size() + "): ");
        try {
            int lessonChoice = scanner.nextInt();
            scanner.nextLine();

            if (lessonChoice > 0 && lessonChoice <= lessons.size()) {
                Lesson selectedLesson = lessons.get(lessonChoice - 1);
                
                // Assign lesson to all selected students
                for (String studentUsername : selectedStudents) {
                    studentAssignments.computeIfAbsent(studentUsername, k -> new ArrayList<>())
                                    .add(selectedLesson);
                    System.out.println("Lesson '" + selectedLesson.getTitle() + 
                                     "' assigned to student: " + studentUsername);
                }
            } else {
                System.out.println("Invalid lesson selection.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid number.");
            scanner.nextLine(); // Clear invalid input
        }
    }
    private void viewAssignedLessons() {
        String username = userProfile.getUsername(); // You'll need to pass the username from Profile
        List<Lesson> assignedLessons = studentAssignments.get(username);
        
        if (assignedLessons == null || assignedLessons.isEmpty()) {
            System.out.println("\nNo lessons assigned to you.");
            return;
        }

        System.out.println("\nYour Assigned Lessons:");
        for (int i = 0; i < assignedLessons.size(); i++) {
            System.out.println((i + 1) + ". " + assignedLessons.get(i).getTitle());
        }

        System.out.print("\nSelect a lesson to view (1-" + assignedLessons.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice > 0 && choice <= assignedLessons.size()) {
            Lesson selectedLesson = assignedLessons.get(choice - 1);
            selectedLesson.display();
            openSheetMusic(selectedLesson.getSheetMusicFile());
        } else {
            System.out.println("Invalid selection.");
        }
    }


    private String selectSheetMusic() {
        List<String> sheetMusicFiles = loadSheetMusicFiles();
        
        if (sheetMusicFiles.isEmpty()) {
            System.out.println("No sheet music files available. Please add PDF files to the sheetmusic folder.");
            return null;
        }

        System.out.println("\nAvailable Sheet Music:");
        for (int i = 0; i < sheetMusicFiles.size(); i++) {
            System.out.println((i + 1) + ". " + sheetMusicFiles.get(i));
        }

        System.out.print("Select sheet music (1-" + sheetMusicFiles.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice > 0 && choice <= sheetMusicFiles.size()) {
            return sheetMusicFiles.get(choice - 1);
        }
        return null;
    }

    private List<String> loadSheetMusicFiles() {
        List<String> files = new ArrayList<>();
        File folder = new File(SHEET_MUSIC_PATH);
        
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdir();
            return files;
        }

        File[] sheetMusicFiles = folder.listFiles((dir, name) -> 
            name.toLowerCase().endsWith(".pdf"));
        
        if (sheetMusicFiles != null) {
            for (File file : sheetMusicFiles) {
                files.add(file.getName());
            }
        }
        return files;
    }

    private void createLesson() {
        System.out.println("\nCreate New Lesson");
        String sheetMusicFile = selectSheetMusic();
        
        if (sheetMusicFile == null) {
            System.out.println("Lesson creation cancelled - no sheet music selected.");
            return;
        }

        System.out.print("Enter lesson title: ");
        String title = scanner.nextLine();
        
        System.out.print("Enter time signature (e.g., 4/4): ");
        String timeSignature = scanner.nextLine();
        
        System.out.print("Enter key (e.g., C major): ");
        String key = scanner.nextLine();
        
        System.out.print("Enter difficulty level (1-5): ");
        int difficulty = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Enter practice notes: ");
        String notes = scanner.nextLine();

        // Create SongDetails with just the basic attributes
        SongDetails songDetails = new SongDetails(timeSignature, key, "");
        Lesson newLesson = new Lesson(title, songDetails, difficulty, notes, sheetMusicFile);
        lessons.add(newLesson);
        
        System.out.println("Lesson created successfully!");
    }

    private void viewLessons() {
        if (lessons.isEmpty()) {
            System.out.println("\nNo lessons available.");
            return;
        }

        System.out.println("\nAvailable Lessons:");
        for (int i = 0; i < lessons.size(); i++) {
            System.out.println((i + 1) + ". " + lessons.get(i).getTitle());
        }

        System.out.print("\nSelect a lesson to view details (1-" + lessons.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice > 0 && choice <= lessons.size()) {
            Lesson selectedLesson = lessons.get(choice - 1);
            selectedLesson.display();
            openSheetMusic(selectedLesson.getSheetMusicFile());
        } else {
            System.out.println("Invalid selection.");
        }
    }

    private void openSheetMusic(String sheetMusicFile) {
        try {
            File file = new File(SHEET_MUSIC_PATH + sheetMusicFile);
            if (file.exists()) {
                java.awt.Desktop.getDesktop().open(file);
                System.out.println("Opening sheet music: " + sheetMusicFile);
            } else {
                System.out.println("Sheet music file not found: " + sheetMusicFile);
            }
        } catch (Exception e) {
            System.out.println("Error opening sheet music: " + e.getMessage());
        }
    }
}