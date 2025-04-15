package controllers;

import tuneup.ExperienceLevel;
import tuneup.TuneUp;
import tuneup.User;
import tuneup.UserType;

/**
 * Controller responsible for user registration
 */
public class SignUpController {
    private TuneUp facade;
    
    /**
     * Constructor initializes with a reference to the application facade
     */
    public SignUpController(TuneUp facade) {
        this.facade = facade;
    }
    
    /**
     * Registers a new user with the system
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
     * Validates registration data
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
}