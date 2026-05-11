package com.canigetafiver.lifemyway.web.auth;
import java.util.Objects;
import java.util.Optional;

/**
 * storage contract for user accounts
 * AuthService only talks to this interface
 */
public interface UserStore {
    Optional<StoredUser> findByUsername(String username);
    void save(StoredUser user);
    boolean exists(String username);

    /**
     * internal user rep for auth module
     * passwordHash is in SHA-256 hex,never plaintext
     */
    record StoredUser(String username, String passwordHash, String displayName){
        public StoredUser{
            Objects.requireNonNull(username, "username required");
            Objects.requireNonNull(passwordHash, "passwordHash required"); 
            Objects.requireNonNull(displayName, "displayName required");     
        }
    }
}
