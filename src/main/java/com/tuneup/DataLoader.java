package com.tuneup;

import java.util.List;

public class DataLoader {
    private ProfileManager profileManager;

    public DataLoader(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    public void loadUsers() {
        List<UserProfile> users = DataReader.readUsers();
        if (users != null) {
            for (UserProfile user : users) {
                profileManager.addProfile(user);
            }
        }
    }

    public void loadLessons() {
        List<Lesson> lessons = DataReader.readLessons();
        if (lessons != null) {
            for (Lesson lesson : lessons) {
                // Add logic to associate lessons with users
            }
        }
    }
}