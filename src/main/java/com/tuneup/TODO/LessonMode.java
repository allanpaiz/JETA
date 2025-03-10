package com.tuneup;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
            if ("Teacher".equalsIgnoreCase(userProfile.getRole())) {
                System.out.println("2. Add Lesson");
                System.out.println("3. Remove Lesson");
                System.out.println("4. Assign Lesson to Student");
                System.out.println("5. Assign Lesson to Group");
                System.out.println("6. Return to Main Menu");
            } else {
                System.out.println("2. Return to Main Menu");
            }
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if ("Teacher".equalsIgnoreCase(userProfile.getRole())) {
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
                        assignLessonToStudent();
                        break;
                    case 5:
                        assignLessonToGroup();
                        break;
                    case 6:
                        inLessonMode = false;
                        break;
                    default:
                        System.out.println("Invalid option selected.");
                        break;
                }
            } else {
                switch (choice) {
                    case 1:
                        viewLessons();
                        break;
                    case 2:
                        inLessonMode = false;
                        break;
                    default:
                        System.out.println("Invalid option selected.");
                        break;
                }
            }
        }
    }

    private void viewLessons() {
        List<Song> lessons = DataLoader.loadLessons();
        ExperienceLevel userExperienceLevel = userProfile.getExperienceLevel();

        List<Song> filteredLessons;
        if ("Teacher".equalsIgnoreCase(userProfile.getRole())) {
            filteredLessons = lessons;
        } else {
            filteredLessons = lessons.stream()
                    .filter(lesson -> lesson.getAssignedExperienceLevels().contains(userExperienceLevel))
                    .collect(Collectors.toList());
        }

        if (filteredLessons.isEmpty()) {
            System.out.println("No lessons available for your experience level.");
        } else {
            System.out.println("Lessons:");
            for (int i = 0; i < filteredLessons.size(); i++) {
                Song lesson = filteredLessons.get(i);
                System.out.println((i + 1) + ". " + lesson.getTitle() + " by " + lesson.getArtist() + " (Instructor: " + lesson.getInstructor() + ")");
            }
        }
    }

    private void addLesson() {
        if (!"Teacher".equalsIgnoreCase(userProfile.getRole())) {
            System.out.println("Only teachers can add lessons.");
            return;
        }

        List<Song> songLibrary = SongLibrary.getSongLibrary();
        if (songLibrary.isEmpty()) {
            System.out.println("The song library is empty.");
            return;
        }

        System.out.println("Select a song to create a lesson:");
        for (int i = 0; i < songLibrary.size(); i++) {
            Song song = songLibrary.get(i);
            System.out.println((i + 1) + ". " + song.getTitle() + " by " + song.getArtist());
        }

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice < 1 || choice > songLibrary.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Song selectedSong = songLibrary.get(choice - 1);

        System.out.print("Enter the instructor of the lesson: ");
        String instructor = scanner.nextLine();

        selectedSong = new Song(selectedSong.getTitle(), selectedSong.getArtist(), instructor, selectedSong.getFilePath());

        saveLesson(selectedSong);
        System.out.println("Lesson added.");
    }

    private void removeLesson() {
        if (!"Teacher".equalsIgnoreCase(userProfile.getRole())) {
            System.out.println("Only teachers can remove lessons.");
            return;
        }

        List<Song> lessons = DataLoader.loadLessons();
        if (lessons.isEmpty()) {
            System.out.println("No lessons available.");
            return;
        }

        System.out.println("Select the lesson to remove:");
        for (int i = 0; i < lessons.size(); i++) {
            Song lesson = lessons.get(i);
            System.out.println((i + 1) + ". " + lesson.getTitle() + " by " + lesson.getArtist() + " (Instructor: " + lesson.getInstructor() + ")");
        }

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice < 1 || choice > lessons.size()) {
            System.out.println("Invalid selection.");
        } else {
            Song removedLesson = lessons.remove(choice - 1);
            DataWriter.saveLessons(lessons);
            System.out.println("Removed " + removedLesson.getTitle() + " by " + removedLesson.getArtist() + " (Instructor: " + removedLesson.getInstructor() + ") from the lessons.");
        }
    }

    private void assignLessonToStudent() {
        if (!"Teacher".equalsIgnoreCase(userProfile.getRole())) {
            System.out.println("Only teachers can assign lessons to students.");
            return;
        }

        List<Song> lessons = DataLoader.loadLessons();
        if (lessons.isEmpty()) {
            System.out.println("No lessons available.");
            return;
        }

        System.out.println("Select the lesson to assign:");
        for (int i = 0; i < lessons.size(); i++) {
            Song lesson = lessons.get(i);
            System.out.println((i + 1) + ". " + lesson.getTitle() + " by " + lesson.getArtist() + " (Instructor: " + lesson.getInstructor() + ")");
        }

        int lessonChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (lessonChoice < 1 || lessonChoice > lessons.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Song selectedLesson = lessons.get(lessonChoice - 1);

        List<User> students = profileManager.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        System.out.println("Select the students to assign the lesson to (comma-separated):");
        for (int i = 0; i < students.size(); i++) {
            User student = students.get(i);
            System.out.println((i + 1) + ". " + student.getUsername());
        }

        String[] studentChoices = scanner.nextLine().split(",");
        for (String choiceStr : studentChoices) {
            int studentIndex = Integer.parseInt(choiceStr.trim()) - 1;
            if (studentIndex < 0 || studentIndex >= students.size()) {
                System.out.println("Invalid selection: " + choiceStr);
                continue;
            }
            User student = students.get(studentIndex);
            selectedLesson.assignStudent(student.getId());
        }

        DataWriter.saveLessons(lessons);
        System.out.println("Assigned lesson to selected students.");
    }

    private void assignLessonToGroup() {
        if (!"Teacher".equalsIgnoreCase(userProfile.getRole())) {
            System.out.println("Only teachers can assign lessons to groups.");
            return;
        }

        List<Song> lessons = DataLoader.loadLessons();
        if (lessons.isEmpty()) {
            System.out.println("No lessons available.");
            return;
        }

        System.out.println("Select the lesson to assign:");
        for (int i = 0; i < lessons.size(); i++) {
            Song lesson = lessons.get(i);
            System.out.println((i + 1) + ". " + lesson.getTitle() + " by " + lesson.getArtist() + " (Instructor: " + lesson.getInstructor() + ")");
        }

        int lessonChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (lessonChoice < 1 || lessonChoice > lessons.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Song selectedLesson = lessons.get(lessonChoice - 1);

        System.out.println("Select the experience level to assign the lesson to:");
        System.out.println("1. Beginner");
        System.out.println("2. Intermediate");
        System.out.println("3. Advanced");
        System.out.print("Please select the experience level (1-3): ");

        int expChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        ExperienceLevel experienceLevel;
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
                System.out.println("Invalid selection.");
                return;
        }

        selectedLesson.assignExperienceLevel(experienceLevel);
        DataWriter.saveLessons(lessons);
        System.out.println("Assigned lesson to " + experienceLevel + " students.");
    }

    private void saveLesson(Song lesson) {
        List<Song> lessons = DataLoader.loadLessons();
        lessons.add(lesson);
        DataWriter.saveLessons(lessons);
    }
}