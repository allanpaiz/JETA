package com.tuneup;

import java.util.List;
import java.util.Scanner;

/**
 * TuneUp facade provides a simplified interface to the subsystems
 */
public class TuneUp {
    private ProfileManager profileManager;
    
    /**
     * Constructor initializes the subsystems
     */
    public TuneUp() {
        profileManager = new ProfileManager();
        // Initialize other subsystems as needed
    }
    
    /**
     * Authenticates a user with username and password
     * 
     * @param username Username to authenticate
     * @param password Password to verify
     * @return User object if authentication succeeds, null otherwise
     */
    public User login(String username, String password) {
        return profileManager.authenticateUser(username, password);
    }
    
    /**
     * Handle login through interactive terminal
     * 
     * @param scanner Scanner for user input
     * @return User object if login succeeds, null otherwise
     */
    public User login(Scanner scanner) {
        return profileManager.handleLogin(scanner);
    }
    
    /**
     * Creates a new user profile
     * 
     * @param username Username for new account
     * @param password Password for new account
     * @param email Email for new account
     * @param role Role (Teacher/Student)
     * @param experienceLevel Experience level for the user
     * @return New User object if creation succeeds, null otherwise
     */
    public User register(String username, String password, String email, UserType role, ExperienceLevel experienceLevel) {
        return profileManager.createProfile(username, password, email, role, experienceLevel);
    }
    
    /**
     * Handle registration through interactive terminal
     * 
     * @param scanner Scanner for user input
     * @return User object if registration succeeds, null otherwise
     */
    public User register(Scanner scanner) {
        return profileManager.handleRegistration(scanner);
    }
    
    /**
     * Gets a list of all students
     * 
     * @return List of all users with Student role
     */
    public List<User> getAllStudents() {
        return profileManager.getAllStudents();
    }
    
    /**
     * Gets students filtered by experience level
     * 
     * @param experienceLevel Experience level to filter by
     * @return List of students with the specified experience level
     */
    public List<User> getStudentsByExperienceLevel(ExperienceLevel experienceLevel) {
        return profileManager.getStudentsByExperienceLevel(experienceLevel);
    }
    
    /**
     * Gets formatted profile information for a user
     * 
     * @param user User whose profile to display
     * @return Formatted string with profile information
     */
    public String getProfileDisplay(User user) {
        return profileManager.getProfileDisplay(user);
    }
    
    /**
     * Display list of students (teacher only)
     * 
     * @param currentUser User requesting the list
     * @param scanner Scanner for user input
     */
    public void displayStudentList(User currentUser, Scanner scanner) {
        profileManager.handleListStudents(currentUser, scanner);
    }
    
    /**
     * Main method - entry point of the application
     */
    public static void main(String[] args) {
        // Create the facade
        TuneUp tuneUp = new TuneUp();
        
        // Create the UI and start it
        TerminalUI terminalUI = new TerminalUI(tuneUp);
        terminalUI.run();
    }
}