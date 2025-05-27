package com.bcnc.inditex_pricing_service.shared;

public class VersionTracker {
    private static final String VERSION = "0.0.1";
    public String getVersion() {
        return VERSION;
    }
    public boolean ping() {
        return true;
    }
}
