package com.tuneup;

import java.util.Scanner;

public class LessonMode implements Mode {
    private UserProfile userProfile;
    private ProfileManager profileManager;
    private Scanner scanner;

    public LessonMode(UserProfile userProfile, ProfileManager profileManager) {
        this.userProfile = userProfile;
        this.profileManager = profileManager;
        scanner = new Scanner(System.in);
    }

    @Override
    public void handle() {
        boolean inLessonMode = true;
        while (inLessonMode) {
            System.out.println("\nLesson Mode");
            System.out.println("1. Assign Lesson");
            System.out.println("2. View Assigned Lessons");
            System.out.println("3. Create Lesson");
            System.out.println("4. View Lessons");
            System.out.println("5. Return to Main Menu");
            System.out.print("Please select an option (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    assignLesson();
                    break;
                case 2:
                    viewAssignedLessons();
                    break;
                case 3:
                    createLesson();
                    break;
                case 4:
                    viewLessons();
                    break;
                case 5:
                    inLessonMode = false;
                    break;
                default:
                    System.out.println("Invalid option selected.");
                    break;
            }
        }
    }

    private void assignLesson() {
        // Implementation for assigning a lesson
    }

    private void viewAssignedLessons() {
        // Implementation for viewing assigned lessons
    }

    private void createLesson() {
        // Implementation for creating a lesson
    }

    private void viewLessons() {
        // Implementation for viewing lessons
    }
}