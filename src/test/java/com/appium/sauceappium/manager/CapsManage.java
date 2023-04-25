package com.appium.sauceappium.manager;

import com.appium.sauceappium.utils.TestUtilities;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import static com.appium.sauceappium.utils.Config.*;

public class CapsManage {

    Properties properties;
    {
        try {
            properties = new PropertyManager().getProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String setDevice() {
        return properties.getProperty(IOS_DEVICE) != null ? properties.getProperty(IOS_DEVICE) : "iPhone 12";
    }

    public String setApp() {
        return TestUtilities.iosApp().toAbsolutePath().toString();
    }

    public String setPlatformVersion() {
        return properties.getProperty(IOS_VERSION) != null ? properties.getProperty(IOS_VERSION) : "15.3";
    }

    public Duration setWdaTime() {
        return Duration.ofSeconds(Long.parseLong(properties.getProperty(WDA_TIME_OUT)));
    }
}

