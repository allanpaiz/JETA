package tuneup;

/**
 * TuneUp is the main facade for the application
 * Provides a simplified interface to the system's functionality
 */
public class TuneUp {
    private ProfileManager profileManager;
    private User currentUser;
    
    /**
     * Constructor initializes all managers
     */
    public TuneUp() {
        this.profileManager = new ProfileManager();
        // Temporarily removed: lessonManager and practiceManager
    }
    
    /**
     * Authenticate user with credentials
     * 
     * @param username Username to authenticate
     * @param password Password to verify
     * @return User object if authentication succeeds, null otherwise
     */
    public User login(String username, String password) {
        currentUser = profileManager.authenticateUser(username, password);
        return currentUser;
    }
    
    /**
     * Register a new user
     * 
     * @param username Username for new account
     * @param password Password for new account
     * @param email Email for new account
     * @param userType User's role (STUDENT/TEACHER)
     * @param experienceLevel User's experience level
     * @return New User object if successful, null otherwise
     */
    public User register(String username, String password, String email, 
                        UserType userType, ExperienceLevel experienceLevel) {
        User newUser = profileManager.createProfile(username, password, email, userType, experienceLevel);
        if (newUser != null) {
            currentUser = newUser;
        }
        return newUser;
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
 * Get a username by user ID
 * 
 * @param userId The ID of the user to look up
 * @return The username, or "Unknown User" if not found
 */
public String getUsernameById(String userId) {
    return profileManager.getUsernameById(userId);
}
    
    /**
     * Log out the current user
     */
    public void logout() {
        currentUser = null;
    }
}