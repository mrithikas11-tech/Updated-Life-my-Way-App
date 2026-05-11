package com.canigetafiver.lifemyway.web.auth;
import java.time.Instant;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Coordinator for login/register/logout
 * 
 */
public class AuthenticationService {
    private static final Pattern USERNAME_PATTERN=Pattern.compile("^[A-Za-z0-9_]{3,20}$");
    private static final int MIN_PASSWORD_LENGTH=6;
    private final UserStore userStore;
    private AuthState state;

    public AuthenticationService(UserStore userStore){
        if(userStore==null){
            throw new IllegalArgumentException("userStore cannot be null");
        }
        this.userStore=userStore;
        this.state=new AuthState.LoggedOut();
    }

    /**
     * authenticates given user credentials
     */
    public Result login(String username,String password){
        if(username==null||password==null){
            return Result.fail("Username/password is required.");
        }
        if(username.isBlank()||password.isBlank()){
            return Result.fail("Username/password is required.");
        }
        Optional<UserStore.StoredUser> match=userStore.findByUsername(username);
        if(match.isEmpty()){
            return Result.fail("Invalid username.");
        }
        UserStore.StoredUser user=match.get();
        if(!user.passwordHash().equals(InMemoryUserStore.sha256(password))){
            return Result.fail("Invalid password.");
        }
        AuthSession session=new AuthSession(user.username(),user.displayName(),Instant.now());
        this.state=new AuthState.LoggedIn(session);
        return Result.ok();
    }

    /**
     * registers a new user account
     */
    public Result register(String username,String password, String confirmPassword, String displayName){
        if(username==null||password==null||confirmPassword==null||displayName==null){
            return Result.fail("All fields are required.");
        }
        if(!USERNAME_PATTERN.matcher(username).matches()){
            return Result.fail("Username must contain only letters, numbers, and underscores, and be " + MIN_PASSWORD_LENGTH+"-20 characters long.");
        }
        if(password.length()<MIN_PASSWORD_LENGTH){
            return Result.fail("Password must be at least " + MIN_PASSWORD_LENGTH+" characters long.");
        }
        if(!password.equals(confirmPassword)){
            return Result.fail("Passwords do not match.");
        }
        if(displayName.isBlank()){
            return Result.fail("Display name is required.");
        }
        if (userStore.exists(username)){
            return Result.fail("Username is already taken.");
        }
        userStore.save(new UserStore.StoredUser(username,InMemoryUserStore.sha256(password), displayName));
        return Result.ok();
    }

    public void logout(){
        this.state=new AuthState.LoggedOut();
    }
    public boolean isAuthenticated(){
        return state.isAuthenticated();
    }
    public Optional<AuthSession> currentSession(){
        return state.session();
    }
    
    /**
     * result type to avoid throwing expected validation fails
     */
    public record Result(boolean success, String message){
        public static Result ok(){return new Result(true,"");}
        public static Result fail(String reason){return new Result(false,reason);}
    }

    
}
