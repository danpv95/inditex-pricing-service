package com.bcnc.inditex_pricing_service.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VersionTrackerTest {

    private final VersionTracker tracker = new VersionTracker();

    @Test
    void shouldReturnExpectedVersion() {
        assertEquals("0.0.1", tracker.getVersion());
    }

    @Test
    void shouldAlwaysPingTrue() {
        assertTrue(tracker.ping());
    }
}
