package com.canigetafiver.lifemyway.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Facade that hides JSON file persistence and crash recovery details.
 */
public class PersistenceManager {
    private final Gson gson;

    public PersistenceManager() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void saveUserData(UserDataBase db, String path) {
        File mainFile = new File(path);
        File tempFile = new File(path + ".tmp");

        File parent = mainFile.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            return;
        }

        try (FileWriter writer = new FileWriter(tempFile)) {
            gson.toJson(db, writer);
        } catch (IOException exception) {
            return;
        }

        if (mainFile.exists() && !mainFile.delete()) {
            return;
        }

        if (!tempFile.renameTo(mainFile)) {
            // Preserve the completed temp file so a later load can recover it.
            return;
        }
    }

    public UserDataBase loadUserData(String path) {
        File mainFile = new File(path);
        File tempFile = new File(path + ".tmp");
        File fileToLoad = mainFile;

        if (!mainFile.exists()) {
            if (!tempFile.exists()) {
                return new UserDataBase();
            }
            if (!tempFile.renameTo(mainFile)) {
                fileToLoad = tempFile;
            }
        }

        try (FileReader reader = new FileReader(fileToLoad)) {
            UserDataBase loadedDataBase = gson.fromJson(reader, UserDataBase.class);
            if (loadedDataBase == null) {
                return new UserDataBase();
            }
            return loadedDataBase;
        } catch (IOException | RuntimeException exception) {
            return new UserDataBase();
        }
    }
}
