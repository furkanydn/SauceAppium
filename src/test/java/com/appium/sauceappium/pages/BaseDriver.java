package com.appium.sauceappium.pages;

import com.appium.sauceappium.manager.CapsManage;
import com.appium.sauceappium.utils.Config;
import com.appium.sauceappium.utils.Constant;
import com.appium.sauceappium.utils.GlobalConfig;
import com.appium.sauceappium.utils.TestUtilities;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.SessionNotCreatedException;

import java.io.File;

public class BaseDriver implements Config {
    private static AppiumDriverLocalService service;
    protected static AndroidDriver androidDriver;
    protected static IOSDriver iosDriver;
    static TestUtilities utilities = new TestUtilities();

    @BeforeAll
    public static void beforeClass() {
        CapsManage capsManage = new CapsManage();
        TestUtilities testUtilities = new TestUtilities();
        utilities.logger().info(Constant.INITIALIZING_APPIUM_DRIVER);
        switch (GlobalConfig.PLATFORM_NAME) {
            case Constant.IOS -> {
                utilities.logger().info("Platform is "+Constant.IOS);
                service = new AppiumServiceBuilder()
                        .withIPAddress(GlobalConfig.APPIUM_IP_ADDRESS)
                        .usingPort(GlobalConfig.APPIUM_PORT)
                        .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                        .withArgument(GeneralServerFlag.LOG_LEVEL,"error")
                        // Mac M1 Issue https://github.com/nvm-sh/nvm/issues/2350
                        //.usingDriverExecutable(new File(capsManage.setDriverNode()))
                        .build();
                service.start();
                try {
                    testUtilities.logger().info(Constant.GETTING_APPIUM_DESIRED_CAPABILITIES);
                    XCUITestOptions options = new XCUITestOptions()
                            .setPlatformName(iOSConfig.PLATFORM_NAME)
                            .setPlatformVersion(iOSConfig.PLATFORM_VERSION)
                            .setDeviceName(iOSConfig.DEVICE_NAME)
                            .setApp(capsManage.setIOSApp())
                            .setWdaLaunchTimeout(iOSConfig.WDA_LAUNCH_TIMEOUT)
                            .setCommandTimeouts(iOSConfig.COMMAND_TIMEOUTS)
                            .eventTimings();
                    try {
                        iosDriver = new IOSDriver(service.getUrl(), options);
                    } catch (SessionNotCreatedException e) {
                        options.useNewWDA();
                        iosDriver = new IOSDriver(service.getUrl(), options);
                    }
                } catch (Exception e) {
                    testUtilities.logger().fatal(Constant.FAILED_TO_LOAD_CAPABILITIES + e);
                }
            }
            case Constant.ANDROID -> {
                utilities.logger().info("Platform is "+Constant.ANDROID);
                service = new AppiumServiceBuilder()
                        .withIPAddress(GlobalConfig.APPIUM_IP_ADDRESS)
                        .usingPort(GlobalConfig.APPIUM_PORT)
                        .usingDriverExecutable(new File(capsManage.setDriverNode()))
                        .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                        .withArgument(GeneralServerFlag.LOG_LEVEL,"error")
                        .build();
                service.start();

                UiAutomator2Options options = new UiAutomator2Options()
                        .setPlatformName(AndroidConfig.PLATFORM_NAME)
                        .setPlatformVersion(AndroidConfig.PLATFORM_VERSION)
                        .setDeviceName(AndroidConfig.DEVICE_NAME)
                        .setApp(capsManage.setAndroidApp())
                        .eventTimings();
                androidDriver = new AndroidDriver(service.getUrl(), options);
            }
            default -> utilities.logger().info(Constant.DRIVER_INITIALIZATION_FAILED);
        }
    }

    @AfterAll
    public static void afterClass() {
        if (iosDriver != null && androidDriver != null) {
            iosDriver.quit();
            androidDriver.quit();
        }
        if (service != null)
            service.stop();
    }
}