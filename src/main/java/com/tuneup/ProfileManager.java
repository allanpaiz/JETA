package com.tuneup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code ProfileManager} manages {@code User} profiles.
 */
public class ProfileManager {
    private Map<String, User> profiles;

    /**
     * Default constructor
     * <p>
     * Initializes {@code profiles} hash map and calls {@code loadProfiles()}
     */
    public ProfileManager() {
        profiles = new HashMap<>();
        loadProfiles();
    }

    /**
     * Load user profiles from users.json
     */
    private void loadProfiles() {
        List<User> users = DataLoader.loadUsers();

        if (users != null) {
            for (User user : users) {
                profiles.put(user.getUsername().toLowerCase(), user);
                System.out.println("Loaded user: " + user.getUsername()); // Debug statement
            }
        } else {
            System.out.println("No users loaded from users.json"); // Debug statement
        }
    }

    /**
     * Get user profile by username
     * 
     * @param username String
     * @return {@code User} object if found, {@code null} otherwise
     */
    public User getProfile(String username) {
        return profiles.get(username.toLowerCase());
    }

    /**
     * Add a new user profile
     * 
     * @param profile {@code User} object
     */
    public void addProfile(User profile) {
        profiles.put(profile.getUsername().toLowerCase(), profile);
        saveProfiles();
    }

    /**
     * Save user profiles to users.json
     */
    public void saveProfiles() {
        List<User> userList = new ArrayList<>(profiles.values());
        DataWriter.saveUsers(userList);
    }

    /**
     * Get all students
     * 
     * @return List of {@code User} objects
     */
    public List<User> getAllStudents() {
        List<User> students = new ArrayList<>();
        for (User profile : profiles.values()) {
            if ("Student".equalsIgnoreCase(profile.getRole())) {
                students.add(profile);
            }
        }
        return students;
    }

    /**
     * Get students by experience level
     * 
     * @param experienceLevel {@code ExperienceLevel}
     * @return List of {@code User} objects
     */
    public List<User> getStudentsByExperienceLevel(ExperienceLevel experienceLevel) {
        List<User> students = new ArrayList<>();
        for (User profile : profiles.values()) {
            if ("Student".equalsIgnoreCase(profile.getRole()) && profile.getExperienceLevel() == experienceLevel) {
                students.add(profile);
            }
        }
        return students;
    }
}