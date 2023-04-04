package com.appium.manager;

import com.appium.utils.TestUtilities;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.util.Properties;

public class CapabilitiesManager {
    TestUtilities testUtilities = new TestUtilities();

    public DesiredCapabilities getCapabilities() throws IOException {
        GlobalParameters parameters = new GlobalParameters();
        Properties properties = new PropertyManager().getProperties();

        try {
            testUtilities.logger().info("Getting appium desired capabilities");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,parameters.getPlatformName());
            capabilities.setCapability(MobileCapabilityType.UDID,parameters.getUdid());
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,parameters.getDeviceName());

            switch (parameters.getPlatformName()) {

            }
            return capabilities;
        } catch (Exception e) {
            e.printStackTrace();
            testUtilities.logger().fatal("Failed to load capabilities of file" + e);
            throw new RuntimeException(e);
        }
    }
}
