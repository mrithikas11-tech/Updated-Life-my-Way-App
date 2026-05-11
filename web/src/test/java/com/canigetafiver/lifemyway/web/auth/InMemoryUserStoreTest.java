package com.canigetafiver.lifemyway.web.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.canigetafiver.lifemyway.web.auth.UserStore.StoredUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("InMemoryUserStore — persistence-free user storage")
class InMemoryUserStoreTest {

    @Test
    @DisplayName("seeds the demo user on construction")
    void seedsDemoUser() {
        InMemoryUserStore store = new InMemoryUserStore();
        assertTrue(store.exists("demo"), "demo account should be seeded");
        StoredUser demo = store.findByUsername("demo").orElseThrow();
        assertEquals("Demo User", demo.displayName());
    }

    @Test
    @DisplayName("save and findByUsername round-trip")
    void saveAndFind() {
        InMemoryUserStore store = new InMemoryUserStore();
        store.save(new StoredUser("alice", InMemoryUserStore.sha256("pw1234"), "Alice"));
        assertTrue(store.exists("alice"));
        assertEquals("Alice", store.findByUsername("alice").orElseThrow().displayName());
    }

    @Test
    @DisplayName("findByUsername returns empty for unknown user")
    void findUnknown() {
        InMemoryUserStore store = new InMemoryUserStore();
        assertTrue(store.findByUsername("nobody").isEmpty());
        assertFalse(store.exists("nobody"));
    }

    @Test
    @DisplayName("save with null user throws")
    void saveNullThrows() {
        InMemoryUserStore store = new InMemoryUserStore();
        assertThrows(IllegalArgumentException.class, () -> store.save(null));
    }

    @Test
    @DisplayName("sha256 is deterministic and not the plaintext")
    void sha256Properties() {
        String hashA = InMemoryUserStore.sha256("hello");
        String hashB = InMemoryUserStore.sha256("hello");
        assertEquals(hashA, hashB, "same input should produce same hash");
        assertNotEquals("hello", hashA, "hash must not equal plaintext");
        assertEquals(64, hashA.length(), "SHA-256 hex digest should be 64 chars");
    }
}
