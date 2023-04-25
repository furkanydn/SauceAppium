package com.appium.sauceappium.manager;

import com.appium.sauceappium.utils.Constant;
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
import org.openqa.selenium.SessionNotCreatedException;

import java.io.IOException;
import java.util.Properties;

import static com.appium.sauceappium.utils.Config.*;

public class BaseDriver {
    private static AppiumDriverLocalService service;
    protected static AndroidDriver androidDriver;
    protected static IOSDriver iosDriver;
    protected static AppiumDriver driver;
    static TestUtilities utilities = new TestUtilities();
    static Properties properties;
    {
        try {
            properties = new PropertyManager().getProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * It is created for the initialization of the mobile driver to be used from the properties.
     * */
    public AppiumDriver getDriverLocal() {
        return properties.
                getProperty(
                        PLATFORM_NAME)
                .equals(
                        IOS)
                ? iosDriver
                : androidDriver;
    }

    @BeforeAll public static void beforeClass() {
        CapsManage capsManage = new CapsManage();
        AppiumManage appiumManage = new AppiumManage();
        TestUtilities testUtilities = new TestUtilities();
        utilities.logger().info(Constant.INITIALIZING_APPIUM_DRIVER);
        switch (properties.getProperty(PLATFORM_NAME)) {
            case Constant.IOS -> {
                service = new AppiumServiceBuilder()
                        .withIPAddress(appiumManage.appiumIpAddress())
                        .usingPort(appiumManage.appiumPort())
                        .build();
                service.start();
                try {
                    testUtilities.logger().info(Constant.GETTING_APPIUM_DESIRED_CAPABILITIES);
                    XCUITestOptions options = new XCUITestOptions()
                            .setDeviceName(capsManage.setDevice())
                            .setApp(capsManage.setApp())
                            .setPlatformVersion(capsManage.setPlatformVersion())
                            .setWdaLaunchTimeout(capsManage.setWdaTime())
                            .setCommandTimeouts(capsManage.setWdaTime())
                            .eventTimings();
                    try {
                        driver = new IOSDriver(service.getUrl(), options);
                    }catch (SessionNotCreatedException e){
                        options.useNewWDA();
                        iosDriver = new IOSDriver(service.getUrl(), options);
                    }
                } catch (Exception e){
                    testUtilities.logger().fatal(Constant.FAILED_TO_LOAD_CAPABILITIES + e);
                }
            }
            case Constant.ANDROID -> {
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
                driver = new AndroidDriver(service.getUrl(), options);
            }
            default -> utilities.logger().info(Constant.DRIVER_INITIALIZATION_FAILED);
        }
    }

    @AfterAll public static void afterClass(){
        if (driver != null){
            driver.quit();
        } if (service != null)
            service.stop();

    }
}
