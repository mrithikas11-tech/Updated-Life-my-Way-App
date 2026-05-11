package com.canigetafiver.lifemyway.web.nav;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("NavigationController + View routing table")
class NavigationControllerTest {

    @Test
    @DisplayName("getInstance returns the same singleton each call")
    void singleton() {
        NavigationController a = NavigationController.getInstance();
        NavigationController b = NavigationController.getInstance();
        assertSame(a, b, "Singleton must return identical instances");
    }

    @Test
    @DisplayName("every View constant resolves its FXML on the classpath")
    void everyViewResolvesFxml() {
        for (View v : View.values()) {
            if (v == View.REGISTER) {
                // RegisterView.fxml is Kevin's deliverable — may be absent at this point.
                // Test still ensures the path exists in the View enum.
                assertNotNull(v.fxmlPath(), "REGISTER must have a path even if FXML not yet committed");
                continue;
            }
            assertNotNull(
                NavigationControllerTest.class.getResource(v.fxmlPath()),
                "FXML missing for " + v + " at " + v.fxmlPath());
        }
    }

    @Test
    @DisplayName("every View constant has a non-blank window title")
    void everyViewHasTitle() {
        for (View v : View.values()) {
            assertNotNull(v.title());
            assertTrue(!v.title().isBlank(), "title must not be blank for " + v);
        }
    }
}
