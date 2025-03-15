package com.tuneup;

import java.util.List;
import java.util.Scanner;

/**
 * {@code TuneUp} is the main facade for the app.
 */
public class TuneUp {
    private ProfileManager profileManager;

    /**
     * Default constructor
     * <p>
     * Initializes {@code profileManager}
     */
    public TuneUp() {
        profileManager = new ProfileManager();
    }

    /**
     * Login method
     * 
     * @param username String
     * @param password String
     * @return {@code User} object if login is successful, {@code null} otherwise
     */
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

    /**
     * User registration method
     * 
     * @param username String
     * @param password String
     * @param email String
     * @param role String (Teacher/Student)
     * @param experienceLevel ExperienceLevel (Beginner/Intermediate/Advanced)
     * @return {@code User} object
     */
    public User register(String username, String password, String email, String role, ExperienceLevel experienceLevel) {
        User user = new User(java.util.UUID.randomUUID().toString(), username, PasswordUtils.hashPassword(password), email, role, experienceLevel);
        profileManager.addProfile(user);
        return user;
    }

    /**
     * Get all students
     * 
     * @return List of {@code User} objects
     */
    public List<User> getAllStudents() {
        return profileManager.getAllStudents();
    }
    
    /**
     * View profile method
     * 
     * @param currentUser {@code User} object
     * @param scanner {@code Scanner} object
     */
    private void viewProfile(User currentUser, Scanner scanner) {
        System.out.println("\n=== Profile Information ===");
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Email: " + currentUser.getEmail());
        System.out.println("Role: " + currentUser.getRole());
        System.out.println("Experience Level: " + currentUser.getExperienceLevel());
        System.out.println("\nPress Enter to return to the main menu...");
        scanner.nextLine();
    }

    /**
     * Method for running the app in terminal mode
     */
    public void runTerminal() {
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;
        boolean running = true;

        System.out.println("\nWelcome to TuneUp Terminal Mode");

        // Main loop
        while (running) {

            // If not logged in, show login/register options
            if (currentUser == null) {
                System.out.println("\n1. Login");
                System.out.println("2. Register");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                
                switch (choice) {
                    case 1:     // Login
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
                    case 2:     // Register
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
                    case 3:     // Exit
                        running = false;
                        break;
                    default:   // Invalid choice
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                // If logged in, show main menu options
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
                    case 1:     // Play Mode
                        Mode playMode = new PlayMode(currentUser, this);
                        playMode.handleTerminal(scanner);
                        break;
                    case 2:     // Song Library
                        Mode songLibraryMode = new SongLibraryMode(currentUser, this);
                        songLibraryMode.handleTerminal(scanner);
                        break;
                    case 3:     // Lesson Mode
                        Mode lessonMode = new LessonMode(currentUser, this);
                        lessonMode.handleTerminal(scanner);
                        break;
                    case 4:     // Create Mode
                        Mode createMode = new CreateMode(currentUser, this);
                        createMode.handleTerminal(scanner);
                        break;
                    case 5:     // View Profile
                        viewProfile(currentUser, scanner);
                        break;
                    case 6:     // List Students
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
                    case 7:     // Logout
                        currentUser = null;
                        System.out.println("Logged out successfully.");
                        break;
                    case 8:     // Exit
                        running = false;
                        break;
                    default:    // Invalid choice
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
        
        System.out.println("Thank you for using TuneUp Terminal Mode. Goodbye!");
        scanner.close();
    }
    
    /**
     * Main method
     * <p>
     * Runs the app in terminal mode
     * 
     * @param args String[]
     */
    public static void main(String[] args) {
        TuneUp tuneUp = new TuneUp();
        tuneUp.runTerminal();
    }
}