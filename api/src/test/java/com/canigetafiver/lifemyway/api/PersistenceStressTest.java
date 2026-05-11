package com.canigetafiver.lifemyway.api;

import java.nio.file.Files;
import java.nio.file.Path;

public class PersistenceStressTest {
    public static void main(String[] args) throws Exception {
        Path dataFile = Files.createTempDirectory("life-my-way-stress-test").resolve("stress-users.json");
        Category category = new Category("Stress", "Stress test expenses");
        ExpenseAccount account = new ExpenseAccount();
        for (int i = 0; i < 500; i++) {
            account.addExpense(new Expense("Expense " + i, i + 0.99, "2026-05-07", category));
        }
        UserDataBase db = new UserDataBase();
        db.addUser("stress", new User("Stress User", "stress@example.com", "555-0500", "USD"), account);
        PersistenceManager persistenceManager = new PersistenceManager();
        persistenceManager.saveUserData(db, dataFile.toString());

        long start = System.nanoTime();
        UserDataBase loaded = persistenceManager.loadUserData(dataFile.toString());
        long elapsedMillis = (System.nanoTime() - start) / 1_000_000;

        assert loaded.findUserAccount("stress").getExpenses().size() == 500;
        assert elapsedMillis < 2_000 : "Load took " + elapsedMillis + " ms";
        System.out.println("PersistenceStressTest passed in " + elapsedMillis + " ms");
    }
}
