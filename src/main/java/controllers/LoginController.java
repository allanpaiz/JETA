package controllers;

import tuneup.TuneUp;
import tuneup.User;

/**
 * Controller responsible for user authentication
 */
public class LoginController {
    private TuneUp facade;
    
    /**
     * Constructor initializes with a reference to the application facade
     */
    public LoginController(TuneUp facade) {
        this.facade = facade;
    }
    
    /**
     * Authenticates a user with provided credentials
     */
    public User authenticateUser(String username, String password) {
        if (username == null || username.trim().isEmpty() || 
            password == null || password.isEmpty()) {
            return null;
        }
        
        return facade.login(username, password);
    }
    
    /**
     * Validates input credentials
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
}