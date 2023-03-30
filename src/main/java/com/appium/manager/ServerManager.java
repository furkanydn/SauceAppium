package com.appium.manager;

import com.appium.utils.TestUtilities;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class ServerManager {
    private static ThreadLocal<AppiumDriverLocalService> serviceThreadLocal = new ThreadLocal<>();
    TestUtilities testUtilities = new TestUtilities();

    public void StartServiceLocal(){
        testUtilities.logger().info("Appium REST Http interface listener started");
    }
}
