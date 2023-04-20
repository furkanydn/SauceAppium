package com.appium.sauceappium.manager;

import com.appium.sauceappium.utils.TestUtilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import static com.appium.sauceappium.utils.Config.*;

public class BaseDriver {
    private static AppiumDriverLocalService service;
    protected static AndroidDriver androidDriver;
    protected static IOSDriver iosDriver;
    TestUtilities utilities = new TestUtilities();
    Properties properties;
    {
        try {
            properties = new PropertyManager().getProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AppiumDriver getDriverLocal() {
        return properties.
                getProperty(
                        PLATFORM_NAME)
                .equals(
                        IOS)
                ? iosDriver
                : androidDriver;
    }

    @BeforeAll public void initDriverThread() throws Exception {
        utilities.logger().info("Initializing appium driver...");
        switch (properties.getProperty(PLATFORM_NAME)) {
            case "iOS" -> {
                String DEVICE =
                        properties.getProperty(IOS_DEVICE) != null
                                ? properties.getProperty(IOS_DEVICE)
                                : "iPhone 12";
                String PLATFORM_VERSION =
                        properties.getProperty(IOS_VERSION) != null
                                ? properties.getProperty(IOS_VERSION)
                                : "15.3";
                Duration WDA_TIMEOUT = Duration.ofSeconds(Long.parseLong(properties.getProperty(WDA_TIME_OUT)));

                service = new AppiumServiceBuilder()
                        .withIPAddress(properties.getProperty(APPIUM_IP_ADDRESS))
                        .usingPort(Integer.parseInt(properties.getProperty(APPIUM_PORT)))
                        .build();
                service.start();

                XCUITestOptions options = new XCUITestOptions()
                        .setDeviceName(DEVICE)
                        .setApp(TestUtilities.iosApp().toAbsolutePath().toString())
                        .setWdaLaunchTimeout(WDA_TIMEOUT)
                        .setPlatformVersion(PLATFORM_VERSION)
                        .eventTimings();
                iosDriver = new IOSDriver(service.getUrl(), options);
            }
            case "Android" -> {
                service = new AppiumServiceBuilder()
                        .withIPAddress(properties.getProperty(APPIUM_IP_ADDRESS))
                        .usingPort(Integer.parseInt(properties.getProperty(APPIUM_PORT)))
                        .build();
                service.start();

                UiAutomator2Options options = new UiAutomator2Options()
                        .setDeviceName(properties.getProperty(ANDROID_DEVICE))
                        .setApp(TestUtilities.androidApk().toAbsolutePath().toString())
                        .eventTimings();
                androidDriver = new AndroidDriver(service.getUrl(), options);
            }
            default -> utilities.logger().info("Driver initialization failed.");
        }
    }

    @AfterAll public static void afterClass(){
        if (iosDriver != null && androidDriver !=null){
            iosDriver.quit();
            androidDriver.quit();
        } if (service != null)
            service.stop();

    }
}
