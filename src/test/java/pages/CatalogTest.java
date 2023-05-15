package pages;

import org.junit.jupiter.api.*;
import utils.AppiumServer;

public class CatalogTest {
    CatalogPage Page = new CatalogPage();
    @BeforeAll
    public static void beforeAll(){
        AppiumServer.start();
    }
    @AfterAll
    public static void afterAll() {
        AppiumServer.stop();
    }

    @Disabled
    @Test
    public void GoDrawingPage(){
        Page.goDrawingPageWithDeepLink();
        Assertions.assertEquals("Drawing", Page.getHeader());
    }
    /**
     * Here, the scenario starts with the assumption that the user is logged in to the app
     * and then the user needs to be on the catalog page. Then,
     * the user selects the product "Backpack" and clicks on the "Add to Cart" button.
     * Similarly, the user selects the product "Bike Light" and clicks on the "Add to Cart" button.
     * Finally, the user goes to the cart to check that the items have been added correctly
     * and the scenario ends successfully.
     */
    @Test
    public void addBackpackAndBikeLightToCart(){
        Assertions.assertEquals("Products", Page.getHeader());
        Page.BackpackAndBikeLightToCart();
    }

    @Test
    public void isBackpackAndBikeLightAddedToCart(){
        Page.isBackpackAndBikeLightAddedToCart();
    }
}
