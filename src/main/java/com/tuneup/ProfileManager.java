package com.tuneup;

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
        List<UserProfile> users = UserProfile.loadProfiles();
        if (users != null) {
            for (UserProfile user : users) {
                profiles.put(user.getUsername(), user);
            }
        }
    }

    public void addProfile(UserProfile profile) {
        profiles.put(profile.getUsername(), profile);
        saveProfiles();
    }

    private void saveProfiles() {
        // Implementation for saving profiles to a file
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