package com.tuneup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class UserProfile {
    private String id;
    private String username;
    private UserType userType;
    private String email;
    private String phoneNumber;

    public UserProfile(String username, UserType userType, String email, String phoneNumber) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.userType = userType;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public static List<UserProfile> loadProfiles() {
        ObjectMapper mapper = new ObjectMapper();
        try (FileReader reader = new FileReader(DataConstants.USERS_FILE)) {
            return mapper.readValue(reader, new TypeReference<List<UserProfile>>() {});
        } catch (IOException e) {
            System.out.println("Error reading users: " + e.getMessage());
            return null;
        }
    }
}