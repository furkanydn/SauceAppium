package pages;

import org.junit.jupiter.api.*;
import utils.AppiumServer;

public class CatalogTest {
    CatalogPage catalogPage = new CatalogPage();

    @BeforeAll
    public static void beforeAll() {
        AppiumServer.start();
    }

    @AfterAll
    public static void afterAll() {
        AppiumServer.stop();
    }

    @Test
    public void requiredOrderProductsSorted() {
        catalogPage.selectSortOptionByNameAscending();
        Assertions.assertTrue(catalogPage.isSortedByNameAscending());

        catalogPage.selectSortOptionByNameDescending();
        Assertions.assertTrue(catalogPage.isSortedByNameDescending());

        catalogPage.selectSortOptionByPriceAscending();
        Assertions.assertTrue(catalogPage.isSortedByPriceAscending());

        catalogPage.selectSortOptionByPriceDescending();
        Assertions.assertTrue(catalogPage.isSortedByPriceDescending());
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
    public void addBackpackAndBikeLightToCart() {
        Assertions.assertEquals("Products", catalogPage.getHeader());
        catalogPage.backpackAndBikeLightToCart();
    }

    @Test
    public void isBackpackAndBikeLightAddedToCart() {
        Assertions.assertTrue(catalogPage.isBackpackAndBikeLightAddedToCart());
        Assertions.assertEquals("My Cart", catalogPage.getHeader());
    }

    @Test
    public void addMultipleProductsWithOptions() {
        catalogPage.multipleProductsWithOptions();
        //Linker.Go("cart/id=2&amount=3&color=black,id=4&amount=3&color=gray,id=5&amount=3&color=red");
        Assertions.assertEquals("My Cart", catalogPage.getHeader());

        String[] productNames = {"Sauce Labs Bike Light", "Sauce Labs Fleece Jacket", "Sauce Labs Onesie"};
        String[] productColors = {"black", "gray", "red"};
        double[] productPrices = {9.99, 49.99, 7.99};
        int[] productQuantities = {3, 3, 3};
        double totalPrice = 203.91;

        for (int i = 0; i < productNames.length; i++) {
            final int index = i;
            Assertions.assertAll(
                    () -> Assertions.assertEquals(productNames[index], catalogPage.assertProductName(productNames[index])),
                    () -> Assertions.assertTrue(catalogPage.assertProductColor(productColors[index])),
                    () -> Assertions.assertEquals("$%s".formatted(productPrices[index]), catalogPage.assertProductPrice(productPrices[index])),
                    () -> Assertions.assertEquals(productQuantities[index], catalogPage.assertProductQuantity(productQuantities[index]))
            );
        }
        Assertions.assertEquals("$%s".formatted(totalPrice),catalogPage.assertCartTotalPrice());
    }
}
