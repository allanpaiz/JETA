package com.tuneup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String id;
    private String username;
    private String password; // This will store the hashed password
    // possibly store unhashed password as well for ease of testing & use?
    private String email;
    private String role;
    private ExperienceLevel experienceLevel;

    // Default constructor
    public User() {
    }

    // Constructor with parameters
    @JsonCreator
    public User(
        @JsonProperty("id") String id,
        @JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("email") String email,
        @JsonProperty("role") String role,
        @JsonProperty("experienceLevel") ExperienceLevel experienceLevel) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.experienceLevel = experienceLevel;
    }

    // Getters and setters

    public String getUserId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    // Method to verify the password
    public boolean verifyPassword(String password) {
        return PasswordUtils.verifyPassword(password, this.password);
    }
}