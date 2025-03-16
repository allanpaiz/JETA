package com.tuneup;

import java.util.List;
import java.util.Scanner;

public class TuneUp {
    private ProfileManager profileManager;

    public TuneUp() {
        profileManager = new ProfileManager();
    }

    public User login(String username, String password) {
        User user = profileManager.getProfile(username);
        if (user != null) {
            System.out.println("User found: " + username); // Debug statement
            if (user.verifyPassword(password)) {
                System.out.println("Password verified for user: " + username); // Debug statement
                return user;
            } else {
                System.out.println("Invalid password for user: " + username); // Debug statement
            }
        } else {
            System.out.println("User not found: " + username); // Debug statement
        }
        return null;
    }

    public User register(String username, String password, String email, String role, ExperienceLevel experienceLevel) {
        User user = new User(java.util.UUID.randomUUID().toString(), username, PasswordUtils.hashPassword(password), email, role, experienceLevel);
        profileManager.addProfile(user);
        return user;
    }

    public List<User> getAllStudents() {
        return profileManager.getAllStudents();
    }

    public List<User> getStudentsByExperienceLevel(ExperienceLevel experienceLevel) {
        return profileManager.getStudentsByExperienceLevel(experienceLevel);
    }

    private void viewProfile(User currentUser, Scanner scanner) {
        System.out.println("\n=== Profile Information ===");
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Role: " + currentUser.getRole());
        System.out.println("Experience Level: " + currentUser.getExperienceLevel());
        System.out.println("\nPress Enter to return to the main menu...");
        scanner.nextLine();
    }

    // New method for terminal-based interaction
    public void runTerminal() {
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;
        boolean running = true;

        System.out.println("Welcome to TuneUp Terminal Mode");

        while (running) {
            if (currentUser == null) {
                System.out.println("\n1. Login");
                System.out.println("2. Register");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter username: ");
                        String username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String password = scanner.nextLine();
                        
                        currentUser = login(username, password);
                        if (currentUser != null) {
                            System.out.println("Login successful!");
                        } else {
                            System.out.println("Login failed. Invalid username or password.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter username: ");
                        username = scanner.nextLine();
                        System.out.print("Enter password: ");
                        password = scanner.nextLine();
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter role (Teacher/Student): ");
                        String role = scanner.nextLine();
                        
                        ExperienceLevel experienceLevel = ExperienceLevel.BEGINNER;
                        if (role.equalsIgnoreCase("Student")) {
                            System.out.print("Enter experience level (Beginner/Intermediate/Advanced): ");
                            String expLevel = scanner.nextLine();
                            experienceLevel = ExperienceLevel.valueOf(expLevel.toUpperCase());
                        } else {
                            experienceLevel = ExperienceLevel.ADVANCED;
                        }
                        
                        currentUser = register(username, password, email, role, experienceLevel);
                        System.out.println("Registration successful!");
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("\nLogged in as: " + currentUser.getUsername() + " (" + currentUser.getRole() + ")");
                System.out.println("=== Main Menu ===");
                System.out.println("1. Play Mode");
                System.out.println("2. Song Library");
                System.out.println("3. Lesson Mode");
                System.out.println("4. Create Mode");
                System.out.println("5. View Profile");
                System.out.println("6. List Students"); // Only relevant for teachers
                System.out.println("7. Logout");
                System.out.println("8. Exit");
                System.out.print("Choose an option: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                
                switch (choice) {
                    case 1:
                        Mode playMode = new PlayMode(currentUser, this);
                        playMode.handleTerminal(scanner);
                        break;
                    case 2:
                        Mode songLibraryMode = new SongLibraryMode(currentUser, this);
                        songLibraryMode.handleTerminal(scanner);
                        break;
                    case 3:
                        Mode lessonMode = new LessonMode(currentUser, this);
                        lessonMode.handleTerminal(scanner);
                        break;
                    case 4:
                        Mode createMode = new CreateMode(currentUser, this);
                        createMode.handleTerminal(scanner);
                        break;
                    case 5:
                        viewProfile(currentUser, scanner);
                        break;
                    case 6:
                        if (currentUser.getRole().equalsIgnoreCase("Teacher")) {
                            List<User> students = getAllStudents();
                            System.out.println("\n=== All Students ===");
                            if (students.isEmpty()) {
                                System.out.println("No students found.");
                            } else {
                                for (User student : students) {
                                    System.out.println(student.getUsername() + " - " + student.getExperienceLevel());
                                }
                            }
                        } else {
                            System.out.println("This option is only available to teachers.");
                        }
                        System.out.println("\nPress Enter to return to the main menu...");
                        scanner.nextLine();
                        break;
                    case 7:
                        currentUser = null;
                        System.out.println("Logged out successfully.");
                        break;
                    case 8:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
        
        System.out.println("Thank you for using TuneUp Terminal Mode. Goodbye!");
        scanner.close();
    }
    
    // Main method for terminal mode
    public static void main(String[] args) {
        TuneUp tuneUp = new TuneUp();
        tuneUp.runTerminal();
    }
}