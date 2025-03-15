package com.tuneup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@code User} class for users.
 */
public class User {
    private String id;
    private String username;
    private String password; // This will store the hashed password
    private String email;
    private String role;
    private ExperienceLevel experienceLevel;

    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * Constructor with parameters
     * 
     * @param id String
     * @param username String
     * @param password String
     * @param email String
     * @param role String
     * @param experienceLevel ExperienceLevel
     */
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

    /**
     * @return {@code id} String
     */
    public String getId() {
        return id;
    }

    /**
     * Set {@code id}
     * 
     * @param id String
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return {@code username} String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set {@code username}
     * 
     * @param username String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return {@code password} String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set {@code password}
     * 
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return {@code email} String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set {@code email}
     * 
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return {@code role} String
     */
    public String getRole() {
        return role;
    }

    /**
     * Set {@code role}
     * 
     * @param role String
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return {@code experienceLevel} ExperienceLevel
     */
    public ExperienceLevel getExperienceLevel() {
        return experienceLevel;
    }

    /**
     * Set {@code experienceLevel}
     * 
     * @param experienceLevel ExperienceLevel
     */
    public void setExperienceLevel(ExperienceLevel experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    /**
     * Method to verify password
     * 
     * @param password String
     * @return {@code true} if password is verified, {@code false} otherwise
     */
    public boolean verifyPassword(String password) {
        return PasswordUtils.verifyPassword(password, this.password);
    }
}