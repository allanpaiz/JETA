package com.tuneup;

import java.util.Scanner;
import java.util.List;

public class LessonMode implements Mode {
    private User userProfile;
    private ProfileManager profileManager;
    private Scanner scanner;

    public LessonMode(User userProfile, ProfileManager profileManager) {
        this.userProfile = userProfile;
        this.profileManager = profileManager;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void handle() {
        boolean inLessonMode = true;
        while (inLessonMode) {
            System.out.println("\nLesson Mode");
            System.out.println("1. View Lessons");
            System.out.println("2. Add Lesson");
            System.out.println("3. Remove Lesson");
            System.out.println("4. Return to Main Menu");
            System.out.print("Please select an option (1-4): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewLessons();
                    break;
                case 2:
                    addLesson();
                    break;
                case 3:
                    removeLesson();
                    break;
                case 4:
                    inLessonMode = false;
                    break;
                default:
                    System.out.println("Invalid option selected.");
                    break;
            }
        }
    }

    private void viewLessons() {
        List<Lesson> lessons = LessonLibrary.getLessons();
        if (lessons.isEmpty()) {
            System.out.println("No lessons available.");
        } else {
            System.out.println("Lessons:");
            for (int i = 0; i < lessons.size(); i++) {
                Lesson lesson = lessons.get(i);
                System.out.println((i + 1) + ". " + lesson.getTitle() + " by " + lesson.getInstructor());
            }
        }
    }

    private void addLesson() {
        System.out.print("Enter the title of the lesson: ");
        String title = scanner.nextLine();
        System.out.print("Enter the instructor of the lesson: ");
        String instructor = scanner.nextLine();

        Lesson newLesson = new Lesson(title, instructor);
        LessonLibrary.addLesson(newLesson);
        System.out.println("Lesson added.");
    }

    private void removeLesson() {
        List<Lesson> lessons = LessonLibrary.getLessons();
        if (lessons.isEmpty()) {
            System.out.println("No lessons available.");
            return;
        }

        System.out.println("Select the lesson to remove:");
        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            System.out.println((i + 1) + ". " + lesson.getTitle() + " by " + lesson.getInstructor());
        }

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice < 1 || choice > lessons.size()) {
            System.out.println("Invalid selection.");
        } else {
            Lesson removedLesson = lessons.remove(choice - 1);
            System.out.println("Removed " + removedLesson.getTitle() + " by " + removedLesson.getInstructor() + " from the lessons.");
        }
    }
}