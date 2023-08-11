package pages;

import org.junit.jupiter.api.*;
import utils.AppiumServer;

public class DrawingTest {
    @BeforeAll
    public static void beforeAll() {
        AppiumServer.start();
    }

    @AfterAll
    public static void afterAll() {
        AppiumServer.stop();
    }

    @Test
    void testDrawASurprisedFace(){
        new DrawingPage().goDrawingPageWithClicks();
        Assertions.assertTrue(new DrawingPage().isSignaturePadDisplayed());
        new DrawingPage().drawFace();
    }

    @Test
    @Disabled("isSignaturePadDisplayed method is not working on ios side needs to be edited")
    void testDrawSquare(){
        new DrawingPage().goDrawingPageWithDeepLink();
        Assertions.assertTrue(new DrawingPage().isSignaturePadDisplayed());
        new DrawingPage().drawSquare();
    }
}
