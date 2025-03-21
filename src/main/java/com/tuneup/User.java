package com.tuneup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User class
 * 
 * @author edwinjwood
 * @author allanpaiz
 */
public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String role;
    private ExperienceLevel experienceLevel;

    /**
     * Constructor
     * 
     * @param id String - UUID made in ProfileManager
     * @param username String
     * @param password String - hashed password made in ProfileManager via PasswordUtils
     * @param email String
     * @param role String - (Teacher/Student)
     * @param experienceLevel - ExperienceLevel enum
     */
    @JsonCreator
    public User(
        @JsonProperty("id") String id,
        @JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("email") String email,
        @JsonProperty("role") String role,
        @JsonProperty("experienceLevel") ExperienceLevel experienceLevel) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setRole(role);
        setExperienceLevel(experienceLevel);
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

    /**
     * Method to verify the user's password
     * 
     * @param password String - plain text password
     * 
     * @return boolean - true if the password is correct
     */
    public boolean verifyPassword(String password) {
        return PasswordUtils.verifyPassword(password, this.password);
    }
}