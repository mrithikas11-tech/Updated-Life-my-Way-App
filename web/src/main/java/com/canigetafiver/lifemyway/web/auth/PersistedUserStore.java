package com.canigetafiver.lifemyway.web.auth;

import com.canigetafiver.lifemyway.api.ExpenseAccount;
import com.canigetafiver.lifemyway.api.MockDataFactory;
import com.canigetafiver.lifemyway.api.User;
import com.canigetafiver.lifemyway.api.UserDataBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * UserStore backed by Steven's UserDataBase + MockDataFactory seed data.
 * Replaces the demo-only InMemoryUserStore for the real application.
 *
 * Passwords are still hashed via the SHA-256 helper from InMemoryUserStore. Since
 * Steven's User class doesn't store passwords, we keep a parallel password map in
 * memory and seed it with default passwords for each pre-seeded account
 * (username "demo" -> "demo123", username "steven" -> "steven123").
 */
public class PersistedUserStore implements UserStore {

    private final UserDataBase database;
    private final Map<String, String> passwords = new HashMap<>();

    public PersistedUserStore() {
        this.database = MockDataFactory.createUserDataBase();
        // Seed default passwords for the demo accounts.
        passwords.put("demo", InMemoryUserStore.sha256("demo123"));
        passwords.put("steven", InMemoryUserStore.sha256("steven123"));
    }

    /** Expose the underlying database for controllers that need account access. */
    public UserDataBase database() {
        return database;
    }

    @Override
    public Optional<StoredUser> findByUsername(String username) {
        if (username == null) return Optional.empty();
        User u = database.findUser(username);
        String hash = passwords.get(username);
        if (u == null || hash == null) return Optional.empty();
        return Optional.of(new StoredUser(username, hash, u.getName()));
    }

    @Override
    public void save(StoredUser user) {
        if (user == null) throw new IllegalArgumentException("user cannot be null");
        passwords.put(user.username(), user.passwordHash());
        if (database.findUser(user.username()) == null) {
            // New registration -> create a User + empty ExpenseAccount in Steven's database.
            User u = new User(user.displayName(), "", "", "USD");
            database.addUser(user.username(), u, new ExpenseAccount());
        }
    }

    @Override
    public boolean exists(String username) {
        return username != null && passwords.containsKey(username);
    }
}
