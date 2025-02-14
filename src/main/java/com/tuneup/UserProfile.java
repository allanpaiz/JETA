package com.tuneup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class UserProfile {
    private String username;
    private UserType userType;

    public UserProfile(String username, UserType userType) {
        this.username = username;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public UserType getUserType() {
        return userType;
    }

    public static List<UserProfile> loadProfiles() {
        try (FileReader reader = new FileReader(DataConstants.USERS_FILE)) {
            Type userListType = new TypeToken<List<UserProfile>>() {}.getType();
            return new Gson().fromJson(reader, userListType);
        } catch (IOException e) {
            System.out.println("Error reading users: " + e.getMessage());
            return null;
        }
    }
}