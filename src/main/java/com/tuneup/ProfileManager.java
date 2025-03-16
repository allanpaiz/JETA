package com.tuneup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileManager {
    private Map<String, User> profiles;

    public ProfileManager() {
        profiles = new HashMap<>();
        loadProfiles();
    }

    public User getProfile(String username) {
        return profiles.get(username.toLowerCase());
    }

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

    public void addProfile(User profile) {
        profiles.put(profile.getUsername().toLowerCase(), profile);
        saveProfiles();
    }

    public void saveProfiles() {
        List<User> userList = new ArrayList<>(profiles.values());
        DataWriter.saveUsers(userList);
    }

    public List<User> getAllStudents() {
        List<User> students = new ArrayList<>();
        for (User profile : profiles.values()) {
            if ("Student".equalsIgnoreCase(profile.getRole())) {
                students.add(profile);
            }
        }
        return students;
    }

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