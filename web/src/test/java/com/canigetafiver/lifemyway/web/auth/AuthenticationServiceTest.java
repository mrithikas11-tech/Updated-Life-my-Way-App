package com.canigetafiver.lifemyway.web.auth;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("AuthenticationService")
class AuthenticationServiceTest {
    private InMemoryUserStore store;
    private AuthenticationService auth;

    @BeforeEach
    void setUp(){
        store=new InMemoryUserStore();
        auth= new AuthenticationService(store);
    }
    
    @Test
    @DisplayName("constructor rejects null user store")
    void RejectsNullStore(){
        assertThrows(IllegalArgumentException.class,()-> new AuthenticationService(null));
    }

    @Test
    @DisplayName("starts in logged out state")
    void startsLoggedOut(){
        assertFalse(auth.isAuthenticated());
        assertTrue(auth.currentSession().isEmpty());
    }

    @Nested
    @DisplayName("login")
    class Login{
        @Test
        @DisplayName("succeeds with seeded demo creds")
        void demoCredsWork(){
            AuthenticationService.Result result=auth.login("demo","demo123");
            assertTrue(result.success(),()->"got: "+ result.message());
            assertTrue(auth.isAuthenticated());
            assertEquals("demo", auth.currentSession().orElseThrow().username());
            assertEquals("Demo User", auth.currentSession().orElseThrow().displayName());
        }
        
        @Test
        @DisplayName("fails on wrong pass")
        void wrongPass(){
            AuthenticationService.Result result=auth.login("demo","WRONG");
            assertFalse(result.success());
            assertFalse(auth.isAuthenticated());
            assertTrue(result.message().toLowerCase().contains("password"));
        }

        @Test
        @DisplayName("fails on blank/null fields")
        void blankFields(){
            assertFalse(auth.login("", "").success());
            assertFalse(auth.login(null, "!").success());
            assertFalse(auth.login("user", "").success());
        }
    }

    @Nested
    @DisplayName("register")
    class Register{
        @Test
        @DisplayName("creates new user that can login")
        void createsNewUser(){
            AuthenticationService.Result rando= auth.register("kelly","supersecret123", "supersecret123", "kelly");
            assertTrue(rando.success(), ()-> "register failed: " +rando.message());
            assertTrue(store.exists("kelly"));
            AuthenticationService.Result login =auth.login("kelly","supersecret123");
            assertTrue(login.success());
        }

        @Test
        @DisplayName("fails on short pass")
        void shortPass(){
            AuthenticationService.Result pass= auth.register("alice", "elc", "elc", "Alice");
            assertFalse(pass.success());
            assertTrue(pass.message().toLowerCase().contains("password"));
        }

        @Test
        @DisplayName("fails on mismatched pass")
        void mismatchpass(){
            AuthenticationService.Result pass= auth.register("bob", "tacocat1", "tacocat2", "Bob");
            assertFalse(pass.success());
            assertTrue(pass.message().toLowerCase().contains("match"));
        }

        @Test 
        @DisplayName("fails on duplicate user")
        void duplicate(){
            assertTrue(auth.register("raven", "abrams2", "abrams2", "Raven").success());
            AuthenticationService.Result dup= auth.register("raven", "abrams2", "abrams2", "Raven");
            assertFalse(dup.success());
            assertTrue(dup.message().toLowerCase().contains("taken"));
        }

        @Test
        @DisplayName("fails on invalid user format")
        void invalidUser(){
            assertFalse(auth.register("h i", "oopsie2","oopsie2","Toby").success());
            assertFalse(auth.register("hi", "oopsie2","oopsie2","Toby").success());
            assertFalse(auth.register("hi!", "oopsie2","oopsie2","Toby").success());
            assertFalse(auth.register("toby", "oopsie2","oopsie2","    ").success());
        }
    }

    @Nested
    @DisplayName("logout")
    class Logout{
        @Test
        @DisplayName("transitions back to logged out state") 
        void logoutClearsSession(){
            assertTrue(auth.login("demo","demo123").success());
            assertTrue(auth.isAuthenticated());
            auth.logout();
            assertFalse(auth.isAuthenticated());
            assertTrue(auth.currentSession().isEmpty());
        }

    }


    
}
