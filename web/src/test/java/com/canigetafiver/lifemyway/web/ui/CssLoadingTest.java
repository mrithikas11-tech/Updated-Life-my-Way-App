package com.canigetafiver.lifemyway.web.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Stylesheet (styles.css) classpath loading")
class CssLoadingTest {

    private static final String CSS_PATH = "/com/canigetafiver/lifemyway/web/styles.css";

    @Test
    @DisplayName("styles.css resolves on the classpath")
    void cssResolves() {
        URL css = CssLoadingTest.class.getResource(CSS_PATH);
        assertNotNull(css, "styles.css must be on the classpath at " + CSS_PATH);
    }

    @Test
    @DisplayName("styles.css contains the design tokens and key selectors")
    void cssHasExpectedSelectors() throws IOException {
        URL css = CssLoadingTest.class.getResource(CSS_PATH);
        assertNotNull(css);
        String contents;
        try (var in = css.openStream()) {
            contents = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
        // Spot-check a handful of selectors that controllers and FXML rely on.
        for (String selector : new String[] {
                ".root", ".card", ".title", ".primary-button",
                ".secondary-button", ".ghost-button", ".text-field",
                ".error-label", ".welcome-banner", ".table-view"
        }) {
            assertTrue(
                contents.contains(selector),
                () -> "styles.css missing expected selector: " + selector);
        }
    }
}
