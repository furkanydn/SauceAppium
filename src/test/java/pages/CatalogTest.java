package pages;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.AppiumServer;

public class CatalogTest {
    CatalogPage page = new CatalogPage();
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
        page.goDrawingPageWithDeepLink();
    }
}
