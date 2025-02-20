package com.tuneup;

import java.io.File;

public class FileUtils {
    public static void ensureDataFolderExists() {
        File jsonFolder = new File("json");
        if (!jsonFolder.exists()) {
            jsonFolder.mkdirs();
        }
    }
}