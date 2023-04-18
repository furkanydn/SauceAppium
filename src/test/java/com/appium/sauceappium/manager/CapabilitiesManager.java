package com.appium.sauceappium.manager;

import com.appium.sauceappium.utils.TestUtilities;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
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
                case "iOS" -> {
                    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, properties.getProperty("iOSAutomationName"));
                    String iOSAppURL = System.getProperty("user.dir") +
                            File.separator + "src" +
                            File.separator + "" +
                            File.separator + "resources" +
                            File.separator + "app" +
                            File.separator + "MyRNDemoApp.app";
                    testUtilities.logger().info("iOSApp URL is" + iOSAppURL);
                    capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, properties.getProperty("iOSBundleId"));
                    capabilities.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, parameters.getWdaLocalPort());
                    capabilities.setCapability("webkitDebugProxyPort", parameters.getWebKitProxyPort());
                    capabilities.setCapability(MobileCapabilityType.APP, iOSAppURL);
                }
                case "Android" -> {
                    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, properties.getProperty("androidAutomationName"));
                    capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, properties.getProperty("androidAppPackage"));
                    /*
                    Deep links are a type of link that send users directly to an app instead.
                    capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,"appActivity");
                    */
                    capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, parameters.getSystemPort());
                    capabilities.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_PORT, parameters.getDriverPort());
                    String androidAppURL = System.getProperty("user.dir") +
                            File.separator + "src" +
                            File.separator + "" +
                            File.separator + "resources" +
                            File.separator + "app" +
                            File.separator + "Android-MyDemoAppRN.1.3.0.build-244.apk";
                    testUtilities.logger().info("androidApp URL is" + androidAppURL);
                    capabilities.setCapability(MobileCapabilityType.APP, androidAppURL);
                }
                default -> throw new IllegalStateException("Unexpected value: " + parameters.getPlatformName());
            }
            return capabilities;
        } catch (Exception e) {
            e.printStackTrace();
            testUtilities.logger().fatal("Failed to load capabilities of file" + e);
            throw new RuntimeException(e);
        }
    }
}
