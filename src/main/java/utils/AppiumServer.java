package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.SessionNotCreatedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class AppiumServer {
    private static AppiumDriverLocalService service;
    protected static AndroidDriver androidDriver;
    protected static IOSDriver iosDriver;
    private static final Logger LOGGER = LogManager.getLogger();
    public static void start() {
        Properties props = new Properties();
        try {
            FileInputStream input = new FileInputStream("config.properties");
            LOGGER.info("config.properties read completed.");
            props.load(input);
        } catch (IOException exception){
            exception.printStackTrace();
        }
        LOGGER.info("Initializing appium driver...");
        switch (props.getProperty("appium.remote.platform.name")){
            case "iOS" -> {
                LOGGER.info("iOS platform selected.");
                service = new AppiumServiceBuilder()
                        .withIPAddress(props.getProperty("appium.server.ip"))
                        .usingPort(Integer.parseInt(props.getProperty("appium.server.port")))
                        // Mac M1 Issue https://github.com/nvm-sh/nvm/issues/2350
                        //.usingDriverExecutable(new File(capsManage.setDriverNode()))
                        .build();
                service.start();

                LOGGER.info("Getting appium desired capabilities");

                XCUITestOptions options = new XCUITestOptions()
                        .setPlatformName(props.getProperty("ios.platform.name"))
                        .setPlatformVersion(props.getProperty("ios.platform.version"))
                        .setDeviceName(props.getProperty("ios.device.name"))
                        .setApp(Pather.iosApp().toAbsolutePath().toString())
                        .setWdaLaunchTimeout(Duration.ofSeconds(Long.parseLong(props.getProperty("ios.wda.launch.timeout"))))
                        .setCommandTimeouts(Duration.ofSeconds(Long.parseLong(props.getProperty("ios.command.timeout"))))
                        .eventTimings();
                try {
                    iosDriver = new IOSDriver(service.getUrl(),options);
                } catch (SessionNotCreatedException e){
                    options.useNewWDA();
                    iosDriver = new IOSDriver(service.getUrl(),options);
                }
            }
            case "Android" -> {
                LOGGER.info("Android platform selected.");
                service = new AppiumServiceBuilder()
                        .withIPAddress(props.getProperty("appium.server.ip"))
                        .usingPort(Integer.parseInt(props.getProperty("appium.server.port")))
                        .usingDriverExecutable(new File(Pather.nodePath().toAbsolutePath().toString()))
                        .build();
                service.start();

                UiAutomator2Options options = new UiAutomator2Options()
                        .setPlatformName(props.getProperty("android.platform.name"))
                        .setPlatformVersion(props.getProperty("android.platform.version"))
                        .setDeviceName(props.getProperty("android.device.name"))
                        .setApp(Pather.androidApk().toAbsolutePath().toString())
                        .eventTimings();
                androidDriver = new AndroidDriver(service.getUrl(), options);
            }
            default -> LOGGER.info("Driver initialization failed.");
        }
    }

    public static void stop() {
        if (iosDriver != null && androidDriver != null) {
            iosDriver.quit();
            androidDriver.quit();
        }
        if (service != null)
            service.stop();
    }
}
