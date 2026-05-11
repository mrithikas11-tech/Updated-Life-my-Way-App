package com.canigetafiver.lifemyway.api;

import java.util.HashMap;

/**
 * Stores and retrieves users with their expense accounts.
 */
public class UserDataBase {
    private HashMap<String, User> users;
    private HashMap<String, ExpenseAccount> userAccounts;

    public UserDataBase() {
        users = new HashMap<>();
        userAccounts = new HashMap<>();
    }

    public void addUser(String userId, User user, ExpenseAccount account) {
        ensureMaps();
        users.put(userId, user);
        userAccounts.put(userId, account);
    }

    public User findUser(String userId) {
        ensureMaps();
        return users.get(userId);
    }

    public ExpenseAccount findUserAccount(String userId) {
        ensureMaps();
        return userAccounts.get(userId);
    }

    public boolean deleteUser(String userId) {
        ensureMaps();
        User removedUser = users.remove(userId);
        ExpenseAccount removedAccount = userAccounts.remove(userId);
        return removedUser != null || removedAccount != null;
    }

    public HashMap<String, User> getUsers() {
        ensureMaps();
        return users;
    }

    public HashMap<String, ExpenseAccount> getUserAccounts() {
        ensureMaps();
        return userAccounts;
    }

    private void ensureMaps() {
        if (users == null) {
            users = new HashMap<>();
        }
        if (userAccounts == null) {
            userAccounts = new HashMap<>();
        }
    }
}
