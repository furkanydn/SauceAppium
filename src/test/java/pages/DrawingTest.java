package pages;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
        new DrawingPage().drawFace();
    }

    @Test
    void testDrawSquare(){
        new DrawingPage().goDrawingPageWithDeepLink();
        new DrawingPage().drawSquare();
    }
}
