package com.appium.sauceappium.manager;

import com.appium.sauceappium.utils.TestUtilities;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
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

    protected String setDevice() {
        return properties.getProperty(IOS_DEVICE) != null ? properties.getProperty(IOS_DEVICE) : "iPhone 12";
    }

    protected String setApp() {
        return TestUtilities.iosApp().toAbsolutePath().toString();
    }

    protected String setPlatformVersion() {
        return properties.getProperty(IOS_VERSION) != null ? properties.getProperty(IOS_VERSION) : "15.3";
    }

    protected Duration setWdaTime() {
        return Duration.ofSeconds(Long.parseLong(properties.getProperty(WDA_TIME_OUT)));
    }
}
