package com.tuneup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * DataWriter class that handles saving data to JSON files.
 * It uses Jackson ObjectMapper to serialize Java objects into JSON data.
 * Implements DataConstants for file path constants.
 * 
 * @see com.fasterxml.jackson.databind.ObjectMapper
 * @see com.fasterxml.jackson.databind.ObjectWriter
 * @see com.fasterxml.jackson.databind.SerializationFeature
 * 
 * @author edwinjwood
 */
public class DataWriter implements DataConstants {
    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private static final ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

    /**
     * Saves user profiles to the JSON file specified in USERS_FILE.
     * 
     * @param users List of User objects to save.
     */
    public static void saveUsers(List<User> users) {
        try (FileWriter fileWriter = new FileWriter(USERS_FILE)) {
            writer.writeValue(fileWriter, users);
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
    
    /**
     * Saves songs to the JSON file specified in SONGS_FILE.
     * 
     * @param songs List of Song objects to save.
     */
    public static void saveSongs(List<Song> songs) {
        try (FileWriter fileWriter = new FileWriter(SONGS_FILE)) {
            writer.writeValue(fileWriter, songs);
        } catch (IOException e) {
            System.out.println("Error saving songs: " + e.getMessage());
        }
    }

    /**
     * Saves lessons to the JSON file specified in LESSONS_FILE.
     * 
     * @param lessons List of Lesson objects to save.
     */
    public static void saveLessons(List<Lesson> lessons) {
        try (FileWriter fileWriter = new FileWriter(LESSONS_FILE)) {
            writer.writeValue(fileWriter, lessons);
        } catch (IOException e) {
            System.out.println("Error saving lessons: " + e.getMessage());
        }
    }
}