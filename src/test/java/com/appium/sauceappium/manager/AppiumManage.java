package com.appium.sauceappium.manager;

import java.io.IOException;
import java.util.Properties;

import static com.appium.sauceappium.utils.Config.APPIUM_IP_ADDRESS;
import static com.appium.sauceappium.utils.Config.APPIUM_PORT;

public class AppiumManage {
    Properties properties;

    {
        try {
            properties = new PropertyManager().getProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String appiumIpAddress() {
        return properties.getProperty(APPIUM_IP_ADDRESS);
    }

    public int appiumPort() {
        return Integer.parseInt(properties.getProperty(APPIUM_PORT));
    }
}
