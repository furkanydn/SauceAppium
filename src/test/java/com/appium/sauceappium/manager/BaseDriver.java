package com.appium.sauceappium.manager;

import com.appium.sauceappium.utils.TestUtilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
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

    @BeforeAll public void initDriverThread() {
        CapsManage capsManage = new CapsManage();
        TestUtilities testUtilities = new TestUtilities();
        utilities.logger().info("Initializing appium driver...");
        switch (properties.getProperty(PLATFORM_NAME)) {
            case "iOS" -> {
                service = new AppiumServiceBuilder()
                        .withIPAddress(properties.getProperty(APPIUM_IP_ADDRESS))
                        .usingPort(Integer.parseInt(properties.getProperty(APPIUM_PORT)))
                        .build();
                service.start();
                try {
                    testUtilities.logger().info("Getting appium desired capabilities");
                    XCUITestOptions options = new XCUITestOptions()
                            .setDeviceName(capsManage.setDevice())
                            .setApp(capsManage.setApp())
                            .setPlatformVersion(capsManage.setPlatformVersion())
                            .setWdaLaunchTimeout(capsManage.setWdaTime())
                            .eventTimings();
                    iosDriver = new IOSDriver(service.getUrl(), options);
                } catch (Exception e){
                    testUtilities.logger().fatal("Failed to load capabilities " + e);
                }
            }
            case "Android" -> {
                service = new AppiumServiceBuilder()
                        .withIPAddress(properties.getProperty(APPIUM_IP_ADDRESS))
                        .usingPort(Integer.parseInt(properties.getProperty(APPIUM_PORT)))
                        .build();
                service.start();

                UiAutomator2Options options = new UiAutomator2Options()
                        .setPlatformName(properties.getProperty(ANDROID))
                        .setPlatformVersion(properties.getProperty(ANDROID_VERSION))
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
