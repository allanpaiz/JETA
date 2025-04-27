package controllers;

import tuneup.ExperienceLevel;
import tuneup.TuneUp;
import tuneup.User;
import tuneup.UserType;

/**
 * Controller responsible for user registration.
 * Handles the logic for validating and registering new users in the system.
 */
public class SignUpController {
    private TuneUp facade;
    
    /**
     * Constructor initializes the controller with a reference to the application facade.
     * 
     * @param facade The application facade providing access to core functionality.
     */
    public SignUpController(TuneUp facade) {
        this.facade = facade;
    }
    
    /**
     * Registers a new user with the system.
     * Validates the input data, converts role and experience level strings to enums,
     * and delegates the registration process to the application facade.
     * 
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @param confirmPassword The confirmation of the password.
     * @param email The email address of the new user.
     * @param roleStr The role of the user as a string (e.g., "Teacher" or "Student").
     * @param experienceLevelStr The experience level of the user as a string (e.g., "Beginner").
     * @return The registered User object if successful, or null if registration fails.
     */
    public User registerUser(String username, String password, String confirmPassword,
                           String email, String roleStr, String experienceLevelStr) {
        try {
            // First validate all input
            String validationError = validateRegistrationData(
                username, password, confirmPassword, email, roleStr, experienceLevelStr);
            
            if (!validationError.isEmpty()) {
                return null;
            }
            
            // Convert role string to UserType enum
            UserType userType;
            if (roleStr.equalsIgnoreCase("Teacher")) {
                userType = UserType.TEACHER;
            } else {
                userType = UserType.STUDENT;
            }
            
            // Convert experience level string to ExperienceLevel enum
            ExperienceLevel experienceLevel;
            if (experienceLevelStr == null || experienceLevelStr.isEmpty()) {
                // Default to ADVANCED for teachers
                experienceLevel = ExperienceLevel.ADVANCED;
            } else if (experienceLevelStr.equalsIgnoreCase("Beginner")) {
                experienceLevel = ExperienceLevel.BEGINNER;
            } else if (experienceLevelStr.equalsIgnoreCase("Intermediate")) {
                experienceLevel = ExperienceLevel.INTERMEDIATE;
            } else {
                experienceLevel = ExperienceLevel.ADVANCED;
            }
            
            // Register the user via facade
            return facade.register(username, password, email, userType, experienceLevel);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Validates the registration data provided by the user.
     * Ensures that all required fields are filled and that passwords match.
     * 
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @param confirmPassword The confirmation of the password.
     * @param email The email address of the new user.
     * @param roleStr The role of the user as a string (e.g., "Teacher" or "Student").
     * @param experienceLevelStr The experience level of the user as a string (optional).
     * @return A validation error message if validation fails, or an empty string if validation succeeds.
     */
    public String validateRegistrationData(String username, String password, 
                                         String confirmPassword, String email,
                                         String roleStr, String experienceLevelStr) {
        // Basic validation
        if (username == null || username.trim().isEmpty()) {
            return "Username is required";
        }
        
        if (password == null || password.isEmpty()) {
            return "Password is required";
        }
        
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            return "Please confirm your password";
        }
        
        if (!password.equals(confirmPassword)) {
            return "Passwords do not match";
        }
        
        if (email == null || email.trim().isEmpty()) {
            return "Email is required";
        }
        
        if (roleStr == null || roleStr.trim().isEmpty()) {
            return "Please select a role";
        }
        
        return "";
    }
    
    /**
     * Gets the application facade.
     * Provides access to the core functionality of the application.
     * 
     * @return The TuneUp facade.
     */
    public TuneUp getFacade() {
        return facade;
    }
}