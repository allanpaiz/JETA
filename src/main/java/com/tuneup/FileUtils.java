package com.tuneup;

import java.io.File;

/**
 * Utility class
 * 
 * @author edwinjwood
 * @author allanpaiz - javadoc
 */
public class FileUtils {

    /**
     * Ensures that the json folder exists, otherwise creates it
     */
    public static void ensureDataFolderExists() {
        File jsonFolder = new File("json");
        if (!jsonFolder.exists()) {
            jsonFolder.mkdirs();
        }
    }
}