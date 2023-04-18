package com.appium.sauceappium.manager;

import com.appium.sauceappium.utils.Config;
import com.appium.sauceappium.utils.TestUtilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.cucumber.java.AfterAll;

import java.io.IOException;
import java.util.Properties;

public class BaseDriver {
    private static AppiumDriverLocalService service;
    protected static AndroidDriver androidDriver;
    protected static IOSDriver iosDriver;
    TestUtilities utilities = new TestUtilities();
    GlobalParameters globalParameters = new GlobalParameters();
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
                        Config.PlatformName.toString())
                .equals(
                        Config.iOS.toString())
                ? iosDriver
                : androidDriver;
    }

    public void setDriverThreadLocal(AppiumDriver appiumDriver) {
        //driverThread.set(appiumDriver);
    }

    public void initDriverThread() throws Exception {
        service = new AppiumServiceBuilder()
                .withIPAddress(properties.getProperty("appiumIpAddress"))
                .usingPort(Integer.parseInt(properties.getProperty("appiumPort")))
                .build();
        service.start();

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

    @AfterAll public static void afterClass(){
        if (iosDriver != null && androidDriver !=null){
            iosDriver.quit();
            androidDriver.quit();
        } if (service != null)
            service.stop();

    }
}
