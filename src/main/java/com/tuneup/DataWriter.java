package com.tuneup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataWriter implements DataConstants {
    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private static final ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

    public static void saveUsers(List<User> users) {
        try (FileWriter fileWriter = new FileWriter(USERS_FILE)) {
            writer.writeValue(fileWriter, users);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public static void saveLessons(List<Song> lessons) {
        try (FileWriter fileWriter = new FileWriter(LESSONS_FILE)) {
            writer.writeValue(fileWriter, lessons);
        } catch (IOException e) {
            System.out.println("Error saving lessons: " + e.getMessage());
        }
    }
}