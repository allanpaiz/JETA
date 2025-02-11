package com.tuneup;

public class UserProfile {
    private String username;
    private UserType userType;

    public UserProfile(String username, UserType userType) {
        this.username = username;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public UserType getUserType() {
        return userType;
    }
}