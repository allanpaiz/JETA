package com.tuneup;

import java.util.UUID;

public class User {
    private String userID;
    private String username;
    private String password;
    private String email;
    private String role;
    private ExperienceLevel experienceLevel;

    public User(String username, String password, String email, String role, ExperienceLevel experienceLevel) {
        this.userID = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.experienceLevel = experienceLevel;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }

    public void register() {
        // Implementation for user registration
    }

    public void login() {
        // Implementation for user login
    }

    public void logout() {
        // Implementation for user logout
    }

    public void updateProfile() {
        // Implementation for updating user profile
    }
}