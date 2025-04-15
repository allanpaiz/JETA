package tuneup;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utility class for hashing and verifying passwords.
 * 
 * This class provides methods to securely hash passwords using SHA-256 and verify
 * plaintext passwords against their hashed counterparts.
 * 
 * @author edwinjwood
 * @author allanpaiz - javadoc
 * @author Terdoo - javadoc
 */
public class PasswordUtils {
    private static final String SALT = "some-random-salt"; // Salt used for hashing passwords

    /**
     * Hashes a plaintext password using SHA-256.
     * 
     * This method combines the provided password with a predefined salt and hashes
     * the result using the SHA-256 algorithm. The hashed password is then encoded
     * in Base64 format for storage or comparison.
     * 
     * @param password The plaintext password to hash.
     * @return The hashed password as a Base64-encoded string.
     * @throws RuntimeException if the SHA-256 algorithm is not available.
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
     * Verifies a plaintext password against a hashed password.
     * 
     * This method hashes the provided plaintext password using the same salt and
     * algorithm as the original hash, then compares the result to the provided
     * hashed password.
     * 
     * @param password The plaintext password to verify.
     * @param hashedPassword The hashed password to compare against.
     * @return {@code true} if the plaintext password matches the hashed password,
     *         {@code false} otherwise.
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
}