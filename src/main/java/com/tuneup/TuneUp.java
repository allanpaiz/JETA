package com.tuneup;

import javafx.stage.Stage;
import java.util.List;

public class TuneUp {
    private ProfileManager profileManager;
    private TuneUpUI tuneUpUI;

    public TuneUp() {
        profileManager = new ProfileManager();
        tuneUpUI = new TuneUpUI();
    }

    public User login(String username, String password) {
        User user = profileManager.getProfile(username);
        if (user != null) {
            System.out.println("User found: " + username); // Debug statement
            if (user.verifyPassword(password)) {
                System.out.println("Password verified for user: " + username); // Debug statement
                return user;
            } else {
                System.out.println("Invalid password for user: " + username); // Debug statement
            }
        } else {
            System.out.println("User not found: " + username); // Debug statement
        }
        return null;
    }

    public User register(String username, String password, String email, String role, ExperienceLevel experienceLevel) {
        User user = new User(java.util.UUID.randomUUID().toString(), username, PasswordUtils.hashPassword(password), email, role, experienceLevel);
        profileManager.addProfile(user);
        return user;
    }

    public List<User> getAllStudents() {
        return profileManager.getAllStudents();
    }

    public List<User> getStudentsByExperienceLevel(ExperienceLevel experienceLevel) {
        return profileManager.getStudentsByExperienceLevel(experienceLevel);
    }

    public void activateMainMenu(User userProfile, Stage stage) {
        tuneUpUI.showMainMenu(stage);
    }

    public void activateCreateMode(User userProfile, Stage stage, TuneUpUI tuneUpUI) {
        Mode createMode = new CreateMode(userProfile, this, stage, tuneUpUI);
        createMode.handle();
    }

    public void activateSongLibrary(User userProfile, Stage stage, TuneUpUI tuneUpUI) {
        Mode songLibraryMode = new SongLibraryMode(userProfile, this, stage, tuneUpUI);
        songLibraryMode.handle();
    }
}