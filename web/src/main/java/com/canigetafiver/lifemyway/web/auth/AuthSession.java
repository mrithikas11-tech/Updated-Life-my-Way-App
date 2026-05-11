package com.canigetafiver.lifemyway.web.auth;
import java.time.Instant;
import java.util.Objects;

/**
 * immutable snapshot of authenticated user session
 */
public record AuthSession(String username, String displayName,Instant loginTime) {
    public AuthSession {
        Objects.requireNonNull(username, "username required");
        Objects.requireNonNull(displayName, "displayName required");
        Objects.requireNonNull(loginTime, "loginTime required");
        if(username.isBlank()){
            throw new IllegalArgumentException("username cannot be blank");
        }
    }
}
