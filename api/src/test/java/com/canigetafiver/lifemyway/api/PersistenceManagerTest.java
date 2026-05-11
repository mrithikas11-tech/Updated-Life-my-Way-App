package com.canigetafiver.lifemyway.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PersistenceManagerTest {
    public static void main(String[] args) throws IOException {
        Path tempDir = Files.createTempDirectory("life-my-way-persistence-test");
        savesAndLoadsEmptyDatabase(tempDir);
        savesAndLoadsDatabaseWithUsersAndAccounts(tempDir);
        recoversFromTempFileWhenMainFileIsMissing(tempDir);
        missingFilesLoadAsEmptyDatabase(tempDir);
        System.out.println("PersistenceManagerTest passed");
    }

    private static void savesAndLoadsEmptyDatabase(Path tempDir) {
        Path dataFile = tempDir.resolve("empty-users.json");
        PersistenceManager persistenceManager = new PersistenceManager();
        persistenceManager.saveUserData(new UserDataBase(), dataFile.toString());
        UserDataBase loaded = persistenceManager.loadUserData(dataFile.toString());
        assert loaded != null;
        assert loaded.getUsers().isEmpty();
        assert loaded.getUserAccounts().isEmpty();
    }

    private static void savesAndLoadsDatabaseWithUsersAndAccounts(Path tempDir) {
        Path dataFile = tempDir.resolve("users.json");
        PersistenceManager persistenceManager = new PersistenceManager();
        persistenceManager.saveUserData(MockDataFactory.createUserDataBase(), dataFile.toString());
        UserDataBase loaded = persistenceManager.loadUserData(dataFile.toString());
        assert loaded.getUsers().size() == 2;
        assert "Steven Tinoco Calvillo".equals(loaded.findUser("steven").getName());
        assert "USD".equals(loaded.findUser("steven").getPreferredCurrency());
        assert loaded.findUserAccount("steven").getExpenses().size() == 5;
    }

    private static void recoversFromTempFileWhenMainFileIsMissing(Path tempDir) throws IOException {
        Path dataFile = tempDir.resolve("recoverable-users.json");
        Path tempFile = tempDir.resolve("recoverable-users.json.tmp");
        PersistenceManager persistenceManager = new PersistenceManager();
        persistenceManager.saveUserData(MockDataFactory.createUserDataBase(), dataFile.toString());
        Files.copy(dataFile, tempFile);
        Files.delete(dataFile);
        UserDataBase loaded = persistenceManager.loadUserData(dataFile.toString());
        assert "Demo Teammate".equals(loaded.findUser("demo").getName());
        assert Files.exists(dataFile) || Files.exists(tempFile);
    }

    private static void missingFilesLoadAsEmptyDatabase(Path tempDir) {
        Path dataFile = tempDir.resolve("missing-users.json");
        UserDataBase loaded = new PersistenceManager().loadUserData(dataFile.toString());
        assert loaded.getUsers().isEmpty();
        assert loaded.getUserAccounts().isEmpty();
    }
}
