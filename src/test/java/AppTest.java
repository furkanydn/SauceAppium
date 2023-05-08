import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Capabilities;
import utils.AppiumServer;
import utils.Pather;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.List;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.*;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private AppiumDriverLocalService service;

    @AfterEach
    public void afterEach() {
        ofNullable(service).ifPresent(AppiumDriverLocalService::stop);
    }
    @Disabled
    @Test
    void checkMain() {
        AppiumServer.start();
        AppiumServer.stop();
    }

    // This test is Service
    @Test
    void checkAbilityToStartDefaultService() {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test
    void checkAbilityToStartServiceOnAFreePort() {
        service = new AppiumServiceBuilder().usingAnyFreePort().build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test
    void checkAbilityToStartServiceUsingNonLocalhostIP() {
        //https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java
        String ipAddress;
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ipAddress = socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
        service = new AppiumServiceBuilder().withIPAddress(ipAddress).build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test
    void checkAbilityToStartServiceUsingFlags() {
        service = new AppiumServiceBuilder()
                .withArgument(SESSION_OVERRIDE)
                .withArgument(LOG_LEVEL, "warn")
                .build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test
    void checkAbilityToStartServiceUsingAndroidCapabilities() {
        UiAutomator2Options options = new UiAutomator2Options()
                .fullReset()
                .setNewCommandTimeout(Duration.ofSeconds(60))
                .setPlatformName("Android")
                .setPlatformVersion("13")
                .setAppPackage("com.saucelabs.mydemoapp.rn")
                .setAppActivity(".MainActivity")
                .setApp(Pather.androidApk().toAbsolutePath().toString());

        service = new AppiumServiceBuilder().withCapabilities(options).build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test
    void checkAbilityToStartServiceUsingIosCapabilities() {
        XCUITestOptions options = new XCUITestOptions()
                .fullReset()
                .setNewCommandTimeout(Duration.ofSeconds(60))
                .setPlatformName("iOS")
                .setPlatformVersion("15.6")
                .setApp(Pather.iosApp().toAbsolutePath().toString());

        service = new AppiumServiceBuilder().withCapabilities(options).build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test
    void checkAbilityToShutDownService() {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
        service.stop();
        assertFalse(service.isRunning());
    }

    @Test
    void checkAbilityToStartAndShutDownFewServices() {
        List<AppiumDriverLocalService> services = asList(
                new AppiumServiceBuilder().usingAnyFreePort().build(),
                new AppiumServiceBuilder().usingAnyFreePort().build(),
                new AppiumServiceBuilder().usingAnyFreePort().build(),
                new AppiumServiceBuilder().usingAnyFreePort().build());
        services.parallelStream().forEach(AppiumDriverLocalService::start);
        assertTrue(services.stream().allMatch(AppiumDriverLocalService::isRunning));
        try {
            SECONDS.sleep(1);
        } catch (InterruptedException ignored) {
        }
        services.parallelStream().forEach(AppiumDriverLocalService::stop);
        assertTrue(services.stream().noneMatch(AppiumDriverLocalService::isRunning));
    }

    @Test
    void checkAbilityToStartServiceWithPortUsingFlag() {
        String port = "10001";
        String expectedUrl = String.format("http://0.0.0.0:%s/", port);

        service = new AppiumServiceBuilder()
                .withArgument(() -> "--port", port)
                .build();
        String actualUrl = service.getUrl().toString();
        assertEquals(expectedUrl, actualUrl);
        service.start();
    }

    @Test
    void checkAbilityToStartServiceWithIpUsingFlag() {
        String testIP = "127.0.0.1";
        String expectedUrl = String.format("http://%s:4723/", testIP);

        service = new AppiumServiceBuilder()
                .withArgument(() -> "--address", testIP)
                .build();
        String actualUrl = service.getUrl().toString();
        assertEquals(expectedUrl, actualUrl);
        service.start();
    }

    @Test
    void checkAbilityToValidateBasePathForEmptyBasePath() {
        assertThrows(IllegalArgumentException.class, () -> new AppiumServiceBuilder().withArgument(BASEPATH, ""));
    }

    @Test
    void checkAbilityToValidateBasePathForNullBasePath() {
        assertThrows(NullPointerException.class, () -> new AppiumServiceBuilder().withArgument(BASEPATH, null));
    }

    @Test
    void startingAndroidAppWithCapabilities() {
        AndroidDriver driver = new AndroidDriver(new UiAutomator2Options()
                .setDeviceName("Android Emulator")
                .autoGrantPermissions()
                .setApp(Pather.androidApk().toAbsolutePath().toString()));
        try {
            Capabilities caps = driver.getCapabilities();
            assertTrue(MobilePlatform.ANDROID.equalsIgnoreCase(
                    String.valueOf(caps.getCapability(MobileCapabilityType.PLATFORM_NAME)))
            );
            assertEquals(AutomationName.ANDROID_UIAUTOMATOR2, caps.getCapability(MobileCapabilityType.AUTOMATION_NAME));
            assertNotNull(caps.getCapability(MobileCapabilityType.DEVICE_NAME));
            assertEquals(Pather.androidApk().toAbsolutePath().toString(), caps.getCapability(MobileCapabilityType.APP));
        } finally {
            driver.quit();
        }
    }

    @Test
    void startingAndroidAppWithCapabilitiesAndServiceTest() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.STRICT_CAPS);

        AndroidDriver driver = new AndroidDriver(builder, new UiAutomator2Options()
                .setDeviceName("Android Emulator")
                .autoGrantPermissions()
                .setApp(Pather.androidApk().toAbsolutePath().toString()));
        try {
            Capabilities caps = driver.getCapabilities();
            assertTrue(MobilePlatform.ANDROID.equalsIgnoreCase(
                    String.valueOf(caps.getCapability(MobileCapabilityType.PLATFORM_NAME)))
            );
            assertNotNull(caps.getCapability(MobileCapabilityType.DEVICE_NAME));
        } finally {
            driver.quit();
        }
    }

    @Test
    void startingAndroidAppWithCapabilitiesAndFlagsOnServerSideTest() {
        UiAutomator2Options serverOptions = new UiAutomator2Options()
                .setDeviceName("Android Emulator")
                .fullReset()
                .autoGrantPermissions()
                .setNewCommandTimeout(Duration.ofSeconds(60))
                .setApp(Pather.androidApk().toAbsolutePath().toString());

        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.STRICT_CAPS)
                .withCapabilities(serverOptions);

        UiAutomator2Options clientOptions = new UiAutomator2Options()
                .setAppPackage("com.saucelabs.mydemoapp.rn")
                .setAppActivity(".MainActivity");

        AndroidDriver driver = new AndroidDriver(builder, clientOptions);
        try {
            Capabilities caps = driver.getCapabilities();
            assertTrue(MobilePlatform.ANDROID.equalsIgnoreCase(
                    String.valueOf(caps.getCapability(MobileCapabilityType.PLATFORM_NAME)))
            );
            assertNotNull(caps.getCapability(MobileCapabilityType.DEVICE_NAME));
        } finally {
            driver.quit();
        }
    }
}