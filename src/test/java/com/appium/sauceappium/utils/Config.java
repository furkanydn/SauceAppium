package com.appium.sauceappium.utils;

import java.util.Arrays;
import java.util.Optional;

enum Config {
    PlatformName("platformName"),
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
