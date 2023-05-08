import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import utils.AppiumServer;
import utils.Pather;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.Duration;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.LOG_LEVEL;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.SESSION_OVERRIDE;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    private AppiumDriverLocalService service;
    @AfterEach public void afterEach(){
        ofNullable(service).ifPresent(AppiumDriverLocalService::stop);
    }
    @Disabled
    @Test
    void main() {
        AppiumServer.start();
        AppiumServer.stop();
    }

    @Test @Order(0) void checkAbilityToStartDefaultService(){
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test @Order(1) void checkAbilityToStartServiceOnAFreePort() {
        service = new AppiumServiceBuilder().usingAnyFreePort().build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test @Order(2) void checkAbilityToStartServiceUsingNonLocalhostIP() {
        //https://stackoverflow.com/questions/9481865/getting-the-ip-address-of-the-current-machine-using-java
        String ipAddress;
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"),10002);
            ipAddress = socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
        service = new AppiumServiceBuilder().withIPAddress(ipAddress).build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test @Order(3) void checkAbilityToStartServiceUsingFlags() {
        service = new AppiumServiceBuilder()
                .withArgument(SESSION_OVERRIDE)
                .withArgument(LOG_LEVEL,"warn")
                .build();
        service.start();
        assertTrue(service.isRunning());
    }

    @Test @Order(4) void checkAbilityToStartServiceUsingAndroidCapabilities() {
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

    @Test @Order(5) void checkAbilityToStartServiceUsingIosCapabilities() {
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
}