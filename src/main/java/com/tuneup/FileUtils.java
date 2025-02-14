package com.tuneup;

import java.io.File;

public class FileUtils {
    public static void ensureDataFolderExists() {
        File dataFolder = new File("data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }
}
