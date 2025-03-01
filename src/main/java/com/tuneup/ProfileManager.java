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
        return profiles.values().stream()
                .filter(profile -> profile.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    private void loadProfiles() {
        // TODO: 
        // List<User> users = DataLoader.loadUsers();
        // if (users != null) {
        //     for (User user : users) {
        //         profiles.put(user.getId(), user);
        //     }
        // }
    }

    public void addProfile(User profile) {
        profiles.put(profile.getId(), profile);
        saveProfiles();
    }


    public void saveProfiles() {
        // TODO:
        // List<User> userList = new ArrayList<>(profiles.values());
        // DataWriter.saveUsers(userList);
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