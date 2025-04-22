package controllers;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import tuneup.DataLoader;
import tuneup.DataWriter;
import tuneup.PasswordUtils;
import tuneup.TuneUp;
import tuneup.User;

/**
 * Controller for handling profile operations
 */
public class ProfileController {
    private static final Logger logger = Logger.getLogger(ProfileController.class.getName());
    private TuneUp facade;
    
    /**
     * Constructor initializes with a reference to the application facade
     */
    public ProfileController(TuneUp facade) {
        this.facade = facade;
    }
    
    /**
     * Update user profile
     * 
     * @param userId ID of the user to update
     * @param updatedUser User object with updated information
     * @return true if update was successful, false otherwise
     */
    public boolean updateUserProfile(String userId, User updatedUser) {
        try {
            // Load all users
            List<User> users = DataLoader.loadUsers();
            if (users == null) {
                logger.warning("Failed to load users from data file");
                return false;
            }
            
            // Find user to update
            boolean userFound = false;
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId().equals(userId)) {
                    // Replace with updated user
                    users.set(i, updatedUser);
                    userFound = true;
                    break;
                }
            }
            
            if (!userFound) {
                logger.warning("User not found for update: " + userId);
                return false;
            }
            
            // Save updated users list
            try {
                DataWriter.saveUsers(users);
                logger.info("User profile updated: " + userId);
                return true;
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Failed to save updated user profile", ex);
                return false;
            }
            
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error updating user profile", ex);
            return false;
        }
    }
    
    /**
     * Change user password
     * 
     * @param userId ID of the user to update
     * @param currentPassword Current password for validation
     * @param newPassword New password to set
     * @return true if password change was successful, false otherwise
     */
    public boolean changePassword(String userId, String currentPassword, String newPassword) {
        try {
            // Load all users
            List<User> users = DataLoader.loadUsers();
            if (users == null) {
                logger.warning("Failed to load users from data file");
                return false;
            }
            
            // Find user to update
            boolean userFound = false;
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getId().equals(userId)) {
                    // Verify current password
                    if (!PasswordUtils.verifyPassword(currentPassword, user.getPassword())) {
                        logger.warning("Current password verification failed for user: " + userId);
                        return false;
                    }
                    
                    // Create updated user with new password
                    User updatedUser = new User(
                        user.getId(),
                        user.getUsername(),
                        PasswordUtils.hashPassword(newPassword),
                        user.getEmail(),
                        user.getRole(),
                        user.getExperienceLevel()
                    );
                    
                    // Replace with updated user
                    users.set(i, updatedUser);
                    userFound = true;
                    break;
                }
            }
            
            if (!userFound) {
                logger.warning("User not found for password change: " + userId);
                return false;
            }
            
            // Save updated users list
            try {
                DataWriter.saveUsers(users);
                logger.info("Password changed for user: " + userId);
                return true;
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Failed to save updated password", ex);
                return false;
            }
            
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error changing password", ex);
            return false;
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