package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.SessionNotCreatedException;

import java.io.File;
import java.time.Duration;

public class AppiumServer {
    private static AppiumDriverLocalService service;
    protected static AndroidDriver androidDriver;
    protected static IOSDriver iosDriver;
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     *
     * Starts the Appium server and initializes the Appium drivers based on the properties specified in the
     * "config.properties" file. The method reads the desired capabilities for iOS and Android platforms from the
     * configuration file and starts the Appium service accordingly. Once the service is up and running, the method
     * initializes the iOS and Android drivers and assigns them to the appropriate driver variables.
     * @throws SessionNotCreatedException if the session creation fails during the initialization of the iOS driver
     * @throws IllegalArgumentException if the "config.properties" file is not found or cannot be read
     *
     * @see #stop()
     */
    public static void start() {
        LOGGER.info("Initializing appium driver...");
        switch (Config.platformName){
            case IOS -> {
                LOGGER.info("iOS platform selected.");
                service = new AppiumServiceBuilder()
                        .withIPAddress(Config.get(Config.ConfigKey.APPIUM_SERVER_IP.getKey()))
                        .usingPort(Integer.parseInt(Config.getProperties("appium.server.port")))
                        .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                        .build();
                service.start();

                LOGGER.info("Getting appium desired capabilities");

                XCUITestOptions options = new XCUITestOptions()
                        .setPlatformName(Config.getProperties("ios.platform.name"))
                        .setPlatformVersion(Config.getProperties("ios.platform.version"))
                        .setDeviceName(Config.getProperties("ios.device.name"))
                        .setApp(Pather.iosApp().toAbsolutePath().toString())
                        .setWdaLaunchTimeout(Duration.ofSeconds(Long.parseLong(Config.getProperties("ios.wda.launch.timeout"))))
                        .setWdaStartupRetries(4)
                        .setWdaStartupRetryInterval(Duration.ofMinutes(15))
                        .setCommandTimeouts(Duration.ofSeconds(Long.parseLong(Config.getProperties("ios.command.timeout"))))
                        .clearSystemFiles()
                        .eventTimings();
                try {
                    iosDriver = new IOSDriver(service.getUrl(),options);
                } catch (SessionNotCreatedException e){
                    options.useNewWDA();
                    iosDriver = new IOSDriver(service.getUrl(),options);
                }
            }
            case ANDROID -> {
                LOGGER.info("Android platform selected.");
                service = new AppiumServiceBuilder()
                        .withIPAddress(Config.getProperties("appium.server.ip"))
                        .usingPort(Integer.parseInt(Config.getProperties("appium.server.port")))
                        .usingDriverExecutable(new File(Pather.nodePath()))
                        .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                        .build();
                service.start();

                UiAutomator2Options options = new UiAutomator2Options()
                        .setPlatformName(Config.getProperties("android.platform.name"))
                        .setPlatformVersion(Config.getProperties("android.platform.version"))
                        .setDeviceName(Config.getProperties("android.device.name"))
                        .setApp(Pather.androidApk().toAbsolutePath().toString())
                        .clearSystemFiles()
                        .eventTimings();
                androidDriver = new AndroidDriver(service.getUrl(), options);
            }
            default -> LOGGER.info("Driver initialization failed.");
        }
    }

    /**
     * Stops this service is it is currently running. This method will attempt to block until the
     * server has been fully shutdown.
     *
     * @see #start()
     */
    public static void stop() {
        if (iosDriver != null && androidDriver != null) {
            iosDriver.quit();
            androidDriver.quit();
        }
        if (service != null)
            service.stop();
    }
}
