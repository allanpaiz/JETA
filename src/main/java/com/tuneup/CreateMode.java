package com.tuneup;

import javafx.stage.Stage;

public class CreateMode implements Mode {
    private User userProfile;
    private TuneUp facade;
    private Stage stage;
    private TuneUpUI tuneUpUI;

    public CreateMode(User userProfile, TuneUp facade, Stage stage, TuneUpUI tuneUpUI) {
        this.userProfile = userProfile;
        this.facade = facade;
        this.stage = stage;
        this.tuneUpUI = tuneUpUI;
    }

    @Override
    public void handle() {
        // Implement the create functionality here
        System.out.println("Create mode activated for user: " + userProfile.getUsername());
        // You can add more functionality here as needed
    }
}