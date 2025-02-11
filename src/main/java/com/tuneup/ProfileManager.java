package com.tuneup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileManager {
    private static final String PROFILES_FILE = "profiles.txt";
    private Map<String, UserProfile> profiles;

    public ProfileManager() {
        profiles = new HashMap<>();
        loadProfiles();
    }

    public UserProfile getProfile(String username) {
        return profiles.get(username);
    }

    private void loadProfiles() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PROFILES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    UserType userType = "TEACHER".equals(parts[1]) ? UserType.TEACHER : UserType.STUDENT;
                    profiles.put(username, new UserProfile(username, userType));
                }
            }
        } catch (IOException e) {
            // File might not exist yet, that's okay for first run
            System.out.println("Creating new profiles database.");
        }
    }

    public void addProfile(UserProfile profile) {
        profiles.put(profile.getUsername(), profile);
        saveProfiles();
    }

    private void saveProfiles() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PROFILES_FILE))) {
            for (UserProfile profile : profiles.values()) {
                writer.println(profile.getUsername() + "," + profile.getUserType().getName());
            }
        } catch (IOException e) {
            System.out.println("Error saving profiles: " + e.getMessage());
        }
    }

    public List<UserProfile> getAllStudents() {
        List<UserProfile> students = new ArrayList<>();
        for (UserProfile profile : profiles.values()) {
            if (profile.getUserType() == UserType.STUDENT) {
                students.add(profile);
            }
        }
        return students;
    }
}