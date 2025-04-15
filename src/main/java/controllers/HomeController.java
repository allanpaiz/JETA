package controllers;

import tuneup.TuneUp;
import tuneup.User;

/**
 * Controller responsible for managing the home screen and navigation to different modes
 */
public class HomeController {
    private TuneUp facade;
    
    /**
     * Constructor initializes with a reference to the application facade
     * 
     * @param facade Application facade
     */
    public HomeController(TuneUp facade) {
        this.facade = facade;
    }
    
    /**
     * Get the currently logged in user
     * 
     * @return Current user or null if not logged in
     */
    public User getCurrentUser() {
        return facade.getCurrentUser();
    }
    
    /**
     * Log out the current user
     */
    public void logout() {
        facade.logout();
    }
    
    /**
     * Switch to Play Mode (practice mode)
     * 
     * @return Success status
     */
    public boolean enterPlayMode() {
        // This would contain logic to enter play mode
        // For now, just return success
        return true;
    }
    
    /**
     * Switch to Create Mode
     * 
     * @return Success status
     */
    public boolean enterCreateMode() {
        // This would contain logic to enter create mode
        // For now, just return success
        return true;
    }
    
    /**
     * Switch to Song Library Mode
     * 
     * @return Success status
     */
    public boolean enterSongLibraryMode() {
        // This would contain logic to enter song library mode
        // For now, just return success
        return true;
    }
    
    /**
     * Gets the application facade
     * @return TuneUp facade
     */
    public TuneUp getFacade() {
        return facade;
    }
}