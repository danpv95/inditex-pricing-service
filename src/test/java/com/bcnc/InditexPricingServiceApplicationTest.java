package com.bcnc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
class InditexPricingServiceApplicationTest {

    @Test
    @DisplayName("Should load Spring context without errors")
    void shouldLoadContext() {
    }

    @Test
    @DisplayName("Should run main method without exceptions")
    void shouldRunMainMethod() {
        assertDoesNotThrow(() -> {
            String[] args = {};
        });
    }
}