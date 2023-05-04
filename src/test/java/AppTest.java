import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import utils.AppiumServer;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {

    @AfterEach public void afterEach(){
        ofNullable(AppiumServer.service).ifPresent(AppiumDriverLocalService::stop);
    }
    @Test
    void main() {
        AppiumServer.start();
        AppiumServer.stop();
    }

    @Test void checkAbilityToStartDefaultService(){
        AppiumServer.service = AppiumDriverLocalService.buildDefaultService();
        AppiumServer.service.start();
        assertTrue(AppiumServer.service.isRunning());
    }
}