package controllers;

import java.util.List;
import java.util.logging.Logger;

import tuneup.DataLoader;
import tuneup.PasswordUtils;
import tuneup.TuneUp;
import tuneup.User;

/**
 * Controller for handling login authentication
 */
public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());
    private TuneUp facade;
    
    /**
     * Constructor initializes with a reference to the application facade
     */
    public LoginController(TuneUp facade) {
        this.facade = facade;
    }
    
    /**
     * Validate login credentials format
     * 
     * @param username Username to validate
     * @param password Password to validate
     * @return Empty string if valid, error message otherwise
     */
    public String validateCredentials(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return "Username is required";
        }
        
        if (password == null || password.isEmpty()) {
            return "Password is required";
        }
        
        return "";
    }
    
    /**
     * Authenticate user with provided credentials
     * 
     * @param username Username to check
     * @param password Password to verify
     * @return User object if authenticated, null otherwise
     */
    public User authenticateUser(String username, String password) {
        try {
            // Get users from DataLoader
            List<User> users = DataLoader.loadUsers();
            
            if (users == null) {
                logger.warning("Failed to load users from data file");
                return null;
            }
            
            // Find matching username
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    // Verify password
                    if (PasswordUtils.verifyPassword(password, user.getPassword())) {
                        logger.info("User authenticated: " + username);
                        return user;
                    } else {
                        logger.info("Password verification failed for: " + username);
                        return null;
                    }
                }
            }
            
            // No matching username found
            logger.info("No user found with username: " + username);
            return null;
            
        } catch (Exception ex) {
            logger.severe("Error during authentication: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * Gets the application facade
     * @return TuneUp facade
     */
    public TuneUp getFacade() {
        return facade;
    }
}