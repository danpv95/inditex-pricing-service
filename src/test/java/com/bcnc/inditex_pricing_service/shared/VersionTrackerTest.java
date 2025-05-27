package com.bcnc.inditex_pricing_service.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VersionTrackerTest {

    private final VersionTracker tracker = new VersionTracker();

    @Test
    void shouldReturnVersion() {
        assertEquals("0.0.1", tracker.getVersion());
    }

    @Test
    void shouldRespondToPing() {
        assertTrue(tracker.ping());
    }
}
