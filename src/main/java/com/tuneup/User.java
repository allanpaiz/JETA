package com.tuneup;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class User {
    private String id;
    private String username;
    private String hashedPassword;
    private String email;
    private String role;
    private ExperienceLevel experienceLevel;

    public User(String username, String password, String email, String role, ExperienceLevel experienceLevel) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.hashedPassword = hashPassword(password);
        this.email = email;
        this.role = role;
        this.experienceLevel = experienceLevel;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
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

    public boolean verifyPassword(String password) {
        return hashedPassword.equals(hashPassword(password));
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}