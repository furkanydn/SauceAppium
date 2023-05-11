package pages;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.AppiumServer;
import utils.Linker;
import utils.Linker.Links;

import static java.util.Optional.ofNullable;

public class CatalogTest {

    @BeforeAll
    public static void beforeAll(){
        AppiumServer.start();
    }
    @AfterAll
    public static void afterAll() {
        AppiumServer.stop();
    }

    @Test
    public void GoDrawingPage(){
        Linker.Go(Links.Drawing);
    }
}
