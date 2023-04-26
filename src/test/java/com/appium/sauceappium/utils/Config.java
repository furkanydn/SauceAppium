package com.appium.sauceappium.utils;

import java.time.Duration;

public interface Config {
    interface AndroidConfig {
        String PLATFORM_NAME = "Android";
        String PLATFORM_VERSION = "11";
        String DEVICE_NAME = "Pixel";
    }

    interface iOSConfig {
        String PLATFORM_NAME = "iOS";
        String PLATFORM_VERSION = "16.4";
        String DEVICE_NAME = "iPhone 14 Pro Max";
        Duration WDA_LAUNCH_TIMEOUT = Duration.ofSeconds(240);
        Duration COMMAND_TIMEOUTS = Duration.ofSeconds(180);
    }
}

