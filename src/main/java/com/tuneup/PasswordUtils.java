package com.tuneup;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Class for hashing and verifying passwords
 * 
 * @author edwinjwood
 * @author allanpaiz - javadoc
 */
public class PasswordUtils {
    private static final String SALT = "some-random-salt";

    /**
     * Hashes plaintext password using SHA-256
     * 
     * @param password String - plaintext password
     * @return password String - hashed password
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
     * Verifies plaintext password against hashed password
     * 
     * @param password String - plaintext password
     * @param hashedPassword String - hashed password
     * @return boolean - true if password matches
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
}