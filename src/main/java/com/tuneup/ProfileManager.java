package com.tuneup;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProfileManager implements DataConstants {
    private List<User> profiles;
    
    public ProfileManager() {
        loadProfiles();
    }
    
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
    public User createProfile(String username, String password, String email, String role, ExperienceLevel experienceLevel) {
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
        System.out.print("Enter role (Teacher/Student): ");
        String role = scanner.nextLine();
        
        ExperienceLevel experienceLevel = ExperienceLevel.BEGINNER;
        if (role.equalsIgnoreCase("Student")) {
            System.out.print("Enter experience level (Beginner/Intermediate/Advanced): ");
            String expLevel = scanner.nextLine();
            try {
                experienceLevel = ExperienceLevel.valueOf(expLevel.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid experience level. Defaulting to BEGINNER.");
            }
        } else {
            experienceLevel = ExperienceLevel.ADVANCED;
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
    
    public User getProfileById(String userId) {
        if (profiles == null) {
            return null;
        }
        
        for (User user : profiles) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
    
    public boolean addProfile(User user) {
        if (getProfile(user.getUsername()) != null) {
            return false; // Username already exists
        }
        
        profiles.add(user);
        saveProfiles();
        return true;
    }
    
    private void saveProfiles() {
        DataWriter.saveUsers(profiles);
    }
    
    public List<User> getAllStudents() {
        return profiles.stream()
            .filter(user -> user.getRole().equalsIgnoreCase("Student"))
            .collect(Collectors.toList());
    }
    
    public List<User> getStudentsByExperienceLevel(ExperienceLevel experienceLevel) {
        return profiles.stream()
            .filter(user -> user.getRole().equalsIgnoreCase("Student") && 
                   user.getExperienceLevel() == experienceLevel)
            .collect(Collectors.toList());
    }

    /**
     * Displays a list of students with their experience levels
     * 
     * @param currentUser The user requesting the list (must be a Teacher)
     * @param scanner Scanner for user input
     * @return Formatted string with student list or access denied message
     */
    public String displayStudentList(User currentUser) {
        if (currentUser == null || !currentUser.getRole().equalsIgnoreCase("Teacher")) {
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
}