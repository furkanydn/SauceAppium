package com.appium.manager;

import com.appium.utils.TestUtilities;
import io.appium.java_client.AppiumDriver;

public class DriverManager {
    private static ThreadLocal<AppiumDriver> driverThreadLocal = new ThreadLocal<>();
    TestUtilities utilities = new TestUtilities();

    public AppiumDriver getDriverThreadLocal() {
        return driverThreadLocal.get();
    }

    public void setDriverThreadLocal(AppiumDriver appiumDriver) {
        driverThreadLocal.set(appiumDriver);
    }

    public void initDriverThread() throws Exception {
        AppiumDriver driver = null;
        GlobalParameters globalParameters = new GlobalParameters();
    }
}
