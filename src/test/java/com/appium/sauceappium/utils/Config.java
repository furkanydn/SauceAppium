package com.appium.sauceappium.utils;

import java.util.Arrays;
import java.util.Optional;

public enum Config {
    PlatformName("platformName"),
    iOS("iOS"),
    Android("Android"),
    AppiumIpAddress("appiumIpAddress"),
    AppiumPort("appiumPort");

    private final String configName;
    Config(String appiumPort) {
        this.configName = appiumPort;
    }

    public String getConfigName(){
        return configName;
    }

    static Optional<Config> byConfigIgnoreCase(String name) {
        return Arrays.stream(values()).filter(i -> i.configName.equalsIgnoreCase(name)).findAny();
    }
}
