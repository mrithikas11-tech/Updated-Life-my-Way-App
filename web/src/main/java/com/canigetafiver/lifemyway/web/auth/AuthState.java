package com.canigetafiver.lifemyway.web.auth;
import java.util.Optional;

/**
 * state pattern interface for auth service
 * 
 */
public sealed interface AuthState permits AuthState.LoggedOut, AuthState.LoggedIn{
    Optional<AuthSession> session();
    boolean isAuthenticated();
    /** Initial state (no user is currently logged in) */
    record LoggedOut() implements AuthState{
        @Override public Optional<AuthSession> session(){return Optional.empty();}
        @Override public boolean isAuthenticated(){return false;}
        }

    /** Active session (authenticated user is logged in) */
    record LoggedIn(AuthSession activeSession) implements AuthState{
        public LoggedIn {
            if(activeSession==null){
                throw new IllegalArgumentException("activeSession cannot be null");
            }
        }
        @Override public Optional<AuthSession> session(){return Optional.of(activeSession);}
        @Override public boolean isAuthenticated(){return true;}
    }
}
