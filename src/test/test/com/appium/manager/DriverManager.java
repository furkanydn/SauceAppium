package com.appium.manager;

import com.appium.utils.TestUtilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import java.io.IOException;

public class DriverManager {
    private static ThreadLocal<AppiumDriver> driverThread = new ThreadLocal<>();
    private static AppiumDriverLocalService service;
    protected static AndroidDriver androidDriver;
    protected static IOSDriver iosDriver;
    TestUtilities utilities = new TestUtilities();

    public AppiumDriver getDriverLocal() {
        return driverThread.get();
    }

    public void setDriverThreadLocal(AppiumDriver appiumDriver) {
        driverThread.set(appiumDriver);
    }

    public void initDriverThread() throws Exception {
        AppiumDriver driver = null;
        GlobalParameters globalParameters = new GlobalParameters();

        try {
            utilities.logger().info("Initializing appium driver...");
            switch (globalParameters.getPlatformName()) {
                case "iOS" -> driver = new IOSDriver(new ServerManager().getAppiumService().getUrl(), new CapabilitiesManager().getCapabilities());
                case "Android" -> driver = new AndroidDriver(new ServerManager().getAppiumService().getUrl(), new CapabilitiesManager().getCapabilities());
            }
            if (driver == null) throw new Exception("Driver is null");
            utilities.logger().info("Driver is initialized");
            setDriverThreadLocal(driver);
        } catch (IOException e) {
            utilities.logger().info("Driver initialization failed.");
            throw e;
        }
    }
}
