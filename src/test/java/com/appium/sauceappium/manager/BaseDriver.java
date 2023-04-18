package com.appium.sauceappium.manager;

import com.appium.sauceappium.utils.TestUtilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.cucumber.java.BeforeAll;

import java.io.IOException;

public class BaseDriver {
    private static AppiumDriverLocalService service;
    protected static AndroidDriver androidDriver;
    protected static IOSDriver iosDriver;
    TestUtilities utilities = new TestUtilities();

    @BeforeAll
    public AppiumDriver getDriverLocal() {
        GlobalParameters p = new GlobalParameters();
        if (p.getPlatformName().equals("iOS")) {
            return iosDriver;
        } else if (p.getPlatformName().equals("Android")) {
            return androidDriver;
        }
        return androidDriver;
    }

    public void setDriverThreadLocal(AppiumDriver appiumDriver) {
        //driverThread.set(appiumDriver);
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
