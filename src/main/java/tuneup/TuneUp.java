package tuneup;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Main facade for the TuneUp application
 */
public class TuneUp {
    private static final Logger logger = Logger.getLogger(TuneUp.class.getName());
    private User currentUser;
    
    /**
     * Initialize the TuneUp application
     */
    public TuneUp() {
        // Ensure data folder exists
        FileUtils.ensureDataFolderExists();
        
        // Load users from JSON
        List<User> users = DataLoader.loadUsers();
        if (users != null) {
            logger.info("Loaded " + users.size() + " users from storage");
        } else {
            logger.warning("Failed to load users data");
        }
    }
    
    /**
     * Log in a user with the provided credentials
     * 
     * @param username Username
     * @param password Plain text password
     * @return User object if login successful, null otherwise
     */
    public User login(String username, String password) {
        List<User> users = DataLoader.loadUsers();
        
        if (users == null) {
            logger.warning("Failed to load users for login");
            return null;
        }
        
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (PasswordUtils.verifyPassword(password, user.getPassword())) {
                    currentUser = user;
                    logger.info("Login successful for: " + username);
                    return user;
                } else {
                    logger.info("Login failed: Incorrect password for: " + username);
                    return null;
                }
            }
        }
        
        logger.info("Login failed: User not found: " + username);
        return null;
    }
     /**
     * Gets a username by user ID
     * 
     * @param userId The ID of the user to look up
     * @return The username corresponding to the ID, or "Unknown" if not found
     */
    public String getUsernameById(String userId) {
        if (userId == null || userId.isEmpty()) {
            return "Unknown";
        }
        
        List<User> users = DataLoader.loadUsers();
        if (users == null) {
            return "Unknown";
        }
        
        for (User user : users) {
            if (userId.equals(user.getId())) {
                return user.getUsername();
            }
        }
        
        // If no user with matching ID is found
        return "Unknown";
    }
    /**
     * Log out the current user
     */
    public void logout() {
        currentUser = null;
        logger.info("User logged out");
    }
    
    /**
     * Get the currently logged in user
     * 
     * @return Current user or null if not logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Register a new user
     * 
     * @param username Username
     * @param password Plain text password (will be hashed)
     * @param email Email address
     * @param role User role
     * @param expLevel Experience level 
     * @return The created user, or null if registration failed
     */
    public User register(String username, String password, String email, 
                         UserType role, ExperienceLevel expLevel) {
        // Get existing users
        List<User> users = DataLoader.loadUsers();
        
        if (users == null) {
            logger.warning("Failed to load users for registration");
            return null;
        }
        
        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                logger.info("Registration failed: Username already exists: " + username);
                return null;
            }
        }
        
        // Create new user with hashed password
        String hashedPassword = PasswordUtils.hashPassword(password);
        String userId = UUID.randomUUID().toString();
        
        User newUser = new User(
            userId, 
            username, 
            hashedPassword, 
            email, 
            role, 
            expLevel
        );
        
        // Add to users list
        users.add(newUser);
        
        // Save updated users list using DataWriter
        DataWriter.saveUsers(users);
        
        logger.info("User registered successfully: " + username);
        return newUser;
    }
}