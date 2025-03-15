package com.tuneup;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * {@code PasswordUtils} class for hashing and verifying passwords.
 */
public class PasswordUtils {

    private static final String SALT = "some-random-salt"; // You can generate a more secure salt

    /**
     * Hash a password using SHA-256 algorithm
     * 
     * @param password String
     * @return Hashed password
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(SALT.getBytes());
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Verify a password against a hashed password
     * 
     * @param password String
     * @param hashedPassword String
     * @return {@code true} if password matches hashed password, {@code false} otherwise
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
}