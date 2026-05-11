package com.canigetafiver.lifemyway.web.auth;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserStore implements UserStore{
    private final Map <String,StoredUser>usersByUsername=new HashMap<>();

    public InMemoryUserStore(){
        //demo seed acc for app testing
        save(new StoredUser("demo", sha256("demo123"), "Demo User"));
    }

    @Override
    public Optional<StoredUser>findByUsername(String username){
        if(username==null){
            return Optional.empty();
        }
        return Optional.ofNullable(usersByUsername.get(username));
    }
    @Override
    public void save(StoredUser user){
        if(user==null){
            throw new IllegalArgumentException("user cannot be null");
        }
        usersByUsername.put(user.username(),user);
    }

    @Override
    public boolean exists(String username){
        return username!=null && usersByUsername.containsKey(username);
    }

    /**
     * sha256 hex digest helper
     */
    public static String sha256(String input){
        try{
            MessageDigest md=MessageDigest.getInstance("SHA-256");
            byte[] digest=md.digest(input.getBytes());
            StringBuilder hex=new StringBuilder(digest.length*2);
            for(byte b:digest){
                hex.append(String.format("%02x",b));
            }
            return hex.toString();
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("SHA-256 not found",e);
        }
    }
}
