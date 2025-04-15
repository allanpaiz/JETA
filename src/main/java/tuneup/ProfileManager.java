package tuneup;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * ProfileManager class manages user profiles
 * 
 * @author edwinjwood
 * @author jaychubb
 * @author allanpaiz - debug/javadoc
 */
public class ProfileManager implements DataConstants {
    private List<User> profiles;
    
    /**
     * Constructor initializes profiles
     */
    public ProfileManager() {
        loadProfiles();
    }
    
    /**
     * Testing constructor that initializes profiles
     * @param users List<User>
     * 
     * @author allanpaiz
     */
    public ProfileManager(List<User> users) {
        this.profiles = users;
    }
    
    /**
     * Loads profiles from json file via DataLoader
     */
    private void loadProfiles() {
        profiles = DataLoader.loadUsers();
        if (profiles == null) {
            profiles = new ArrayList<>();
        }
    }
    
    /**
     * Authenticates a user with username and password
     * 
     * @param username Username to authenticate
     * @param password Password to verify
     * @return User object if authentication succeeds, null otherwise
     */
    public User authenticateUser(String username, String password) {
        User user = getProfile(username);
        if (user != null) {
            if (user.verifyPassword(password)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Creates a new user profile
     * 
     * @param username Username for new account
     * @param password Password for new account (will be hashed)
     * @param email Email for new account
     * @param role Role (Teacher/Student)
     * @param experienceLevel Experience level for the user
     * @return New User object if creation succeeds, null if username exists
     */
    public User createProfile(String username, String password, String email, UserType role, ExperienceLevel experienceLevel) {
        // Check if username is already taken
        if (getProfile(username) != null) {
            return null; // Username already exists
        }
        
        String userId = java.util.UUID.randomUUID().toString();
        String hashedPassword = PasswordUtils.hashPassword(password);
        
        User newUser = new User(userId, username, hashedPassword, email, role, experienceLevel);
        if (addProfile(newUser)) {
            return newUser;
        }
        return null;
    }
    
    /**
     * Handle user login process with interactive UI
     * 
     * @param scanner Scanner for user input
     * @return User object if login succeeds, null otherwise
     */
    public User handleLogin(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        User user = authenticateUser(username, password);
        if (user != null) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
        return user;
    }
    
    /**
     * Handle user registration process with interactive UI
     * 
     * @param scanner Scanner for user input
     * @return User object if registration succeeds, null otherwise
     */
    public User handleRegistration(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        /**
         * Choosing role and experience level from enums
         * @author jaychubb
         */
        System.out.print("Choose your role: ");
        System.out.println("\n1. Student");
        System.out.println("2. Teacher");
        
        int choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Please enter a valid number.");
            scanner.nextLine();
        }

        UserType role = UserType.STUDENT;
        switch(choice) {
            case 1: // student
                role = UserType.STUDENT;
                break;
            case 2: // teacher
                role = UserType.TEACHER;
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Student.");
        }
        
        System.out.print("Choose your experience level: ");
        System.out.println("\n1. Beginner");
        System.out.println("2. Intermediate");
        System.out.println("3. Advanced");

        choice = 0;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Please enter a valid number.");
            scanner.nextLine();
        }

        ExperienceLevel experienceLevel = ExperienceLevel.BEGINNER;
        switch(choice) {
            case 1: // beginner
                experienceLevel = ExperienceLevel.BEGINNER;
                break;
            case 2: // intermediate
                experienceLevel = ExperienceLevel.INTERMEDIATE;
                break;
            case 3: // advanced
                experienceLevel = ExperienceLevel.ADVANCED;
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Beginner.");
        }

        User user = createProfile(username, password, email, role, experienceLevel);
        if (user != null) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Username may already exist.");
        }
        return user;
    }
    
    /**
     * Displays profile information for a user
     * 
     * @param user The user whose profile to display
     * @return Formatted string with user profile information
     */
    public String getProfileDisplay(User user) {
        if (user == null) {
            return "User not found.";
        }
        
        StringBuilder profileInfo = new StringBuilder();
        profileInfo.append("\n=== Profile Information ===\n");
        profileInfo.append("Username: ").append(user.getUsername()).append("\n");
        profileInfo.append("Email: ").append(user.getEmail()).append("\n");
        profileInfo.append("Role: ").append(user.getRole()).append("\n");
        profileInfo.append("Experience Level: ").append(user.getExperienceLevel()).append("\n");
        
        return profileInfo.toString();
    }
    
    /**
     * Get a user profile by username
     * 
     * @param username Username to search for
     * @return User object if found, null otherwise
     */
    public User getProfile(String username) {
        if (profiles == null) {
            return null;
        }
        
        for (User user : profiles) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Get a user profile by user ID
     * 
     * @param userId User ID to search for
     * @return User object if found, null otherwise
     */
    public User getProfileById(String userId) {
        if (profiles == null) {
            return null;
        }
        
        for (User user : profiles) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Add a user profile to the list
     * 
     * @param user User object to add
     * @return True if added successfully, false if username exists
     */
    public boolean addProfile(User user) {
        if (getProfile(user.getUsername()) != null) {
            return false; // Username already exists
        }

        profiles.add(user);
        saveProfiles();
        return true;
    }

    /**
     * Test addProfile(), does not saveProfiles()
     * 
     * @param user User
     * @return True if added successfully, false if username exists
     * 
     * @author allanpaiz
     */
    public boolean addProfileTest(User user) {
        if (getProfile(user.getUsername()) != null) {
            return false; // Username already exists
        }

        profiles.add(user);
        // saveProfiles();
        return true;
    }


    /**
     * Save profiles to json file via DataWriter
     */
    private void saveProfiles() {
        DataWriter.saveUsers(profiles);
    }
    
    /**
     * Get all students from the list of profiles
     * 
     * @return List<User> with role "Student"
     */
    public List<User> getAllStudents() {
        return profiles.stream().filter(user -> user.getRole() == UserType.STUDENT).collect(Collectors.toList());
    }

    /**
     * Get all students by experience level
     * 
     * @param experienceLevel Experience level to filter by
     * @return List<User> with role "Student" and matching experience level
     */
    public List<User> getStudentsByExperienceLevel(ExperienceLevel experienceLevel) {
        return profiles.stream().filter(user -> user.getRole() == UserType.STUDENT && 
                   user.getExperienceLevel() == experienceLevel).collect(Collectors.toList());
    }

    /**
     * Displays a list of students with their experience levels
     * 
     * @param currentUser The user requesting the list (must be a Teacher)
     * @param scanner Scanner for user input
     * @return Formatted string with student list or access denied message
     */
    public String displayStudentList(User currentUser) {
        if (currentUser == null || currentUser.getRole() != UserType.TEACHER) {
            return "This option is only available to teachers.";
        }
        
        List<User> students = getAllStudents();
        StringBuilder result = new StringBuilder("\n=== All Students ===\n");
        
        if (students.isEmpty()) {
            result.append("No students found.");
        } else {
            for (User student : students) {
                result.append(student.getUsername())
                    .append(" - ")
                    .append(student.getExperienceLevel())
                    .append("\n");
            }
        }
        
        return result.toString();
    }

    /**
     * Handle listing students with interactive UI
     * 
     * @param currentUser The user requesting the list (must be a Teacher)
     * @param scanner Scanner for user input
     */
    public void handleListStudents(User currentUser, Scanner scanner) {
        String studentList = displayStudentList(currentUser);
        System.out.println(studentList);
        
        System.out.println("\nPress Enter to return to the main menu...");
        scanner.nextLine();
    }

    /**
     * Get the username of a user by ID
     * 
     * @param userId User ID to search for
     * @return Username if found, "Unknown" otherwise
     */
    public String getUsernameById(String userId) {
        User user = getProfileById(userId);
        return (user != null) ? user.getUsername() : "Unknown";
    }
}