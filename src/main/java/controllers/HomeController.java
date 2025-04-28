package controllers;

import java.util.logging.Logger;

import tuneup.TuneUp;
import tuneup.User;

/**
 * Controller for the home screen business logic
 */
public class HomeController {
    private static final Logger logger = Logger.getLogger(HomeController.class.getName());
    private TuneUp facade;
    
    /**
     * Constructor initializes with app facade
     */
    public HomeController(TuneUp facade) {
        this.facade = facade;
        logger.info("HomeController initialized");
    }
    
    /**
     * Gets the current user from the facade
     */
    public User getCurrentUser() {
        return facade.getCurrentUser();
    }
    
    /**
     * Gets the application facade
     */
    public TuneUp getFacade() {
        return facade;
    }
    
    /**
     * Process logout request
     */
    public void logout() {
        facade.logout();
        logger.info("User logged out");
    }
    
    /**
     * Update user information
     */
    public boolean updateUserProfile(User updatedUser) {
        try {
            // Handle profile update through the facade
            facade.updateCurrentUser(updatedUser);
            return true;
        } catch (Exception e) {
            logger.warning("Failed to update user profile: " + e.getMessage());
            return false;
        }
    }
}