package pages;

import components.AppBar;
import touch.PointerScroll;
import utils.Config;
import utils.Linker;
import utils.Linker.Links;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static utils.Config.Platform;
import static utils.Config.platformName;

public class CatalogPage extends BasePage {
    AppBar Bar = new AppBar();
    PointerScroll Pointer = new PointerScroll();
    /**
     This method navigates to the drawing page in the application using a deep link.
     */
    public void goDrawingPageWithDeepLink(){
        Linker.Go(Links.Drawing);
    }
    /**
     Returns the header text of the current page.
     @return String the header text of the current page.
     */
    public String getHeader() {
        return (platformName == Platform.ANDROID)
                ? findElementByRelativeXPath("text","Products",0).getText()
                : findElementAccessibilityId("container header").getAttribute("label");
    }

    /**
     * Selects the sort option by name in ascending order.
     * This method assumes the sort option button is already visible on the screen.
     * After selecting the sort option, it clicks on the "nameAsc" element.
     */
    public void selectSortOptionByNameAscending() {
        Bar.sortOptionButton();
        findElementAccessibilityId("nameAsc").click();
    }
    /**
     * Checks if the items are sorted by name in ascending order.
     * This method identifies the platform name and checks if the expected sorting is applied.
     * Supports iOS and Android platforms.
     * Returns true if the items are sorted by name in ascending order.
     * Throws a NoSuchElementException if the order cannot be accessed at the moment.
     */
    public boolean isSortedByNameAscending() {
        switch (Config.platformName) {
            case IOS -> {
                return findElementByRelativeXPath("name", "store item", 1)
                        .getAttribute("label")
                        .matches(".*" + "Sauce Labs Backpack" + ".*");
            }
            case ANDROID -> {
                return findElementByRelativeXPath("content", "store item text", 1)
                        .getText()
                        .matches(".*" + "Sauce Labs Backpack" + ".*");
            }
            default -> throw new NoSuchElementException("Sorry, Order cannot be accessed at the moment.");
        }
    }
    /**
     * Selects the sort option by name in descending order.
     * This method assumes the sort option button is already visible on the screen.
     * After selecting the sort option, it clicks on the "nameDesc" element.
     */
    public void selectSortOptionByNameDescending(){
        Bar.sortOptionButton();
        findElementAccessibilityId("nameDesc").click();
    }
    /**
     * Checks if the items are sorted by name in descending order.
     * This method identifies the platform name and checks if the expected sorting is applied.
     * Supports iOS and Android platforms.
     * Returns true if the items are sorted by name in descending order.
     * Throws a NoSuchElementException if the order cannot be accessed at the moment.
     */
    public boolean isSortedByNameDescending() {
        switch (Config.platformName) {
            case IOS -> {
                return findElementByRelativeXPath("name", "store item", 1)
                        .getAttribute("label")
                        .matches(".*" + "Test.allTheThings" + ".*");
            }
            case ANDROID -> {
                return findElementByRelativeXPath("content", "store item text", 1)
                        .getText()
                        .matches(".*" + "Test.allTheThings" + ".*");
            }
            default -> throw new NoSuchElementException("Sorry, Order cannot be accessed at the moment.");
        }
    }
    /**
     * Selects the sort option by price in ascending order.
     * This method assumes the sort option button is already visible on the screen.
     * After selecting the sort option, it clicks on the "priceAsc" element.
     */
    public void selectSortOptionByPriceAscending(){
        Bar.sortOptionButton();
        findElementAccessibilityId("priceAsc").click();
    }
    /**
     * Checks if the items are sorted by price in ascending order.
     * This method checks if the expected sorting is applied.
     * Returns true if the items are sorted by price in ascending order.
     */
    public boolean isSortedByPriceAscending() {
        switch (Config.platformName) {
            case IOS -> {
                return findElementByRelativeXPath("name", "store item", 1)
                        .getAttribute("label")
                        .matches(".*" + "Sauce Labs Onesie" + ".*");
            }
            case ANDROID -> {
                return findElementByRelativeXPath("content", "store item text", 1)
                        .getText()
                        .matches(".*" + "Sauce Labs Onesie" + ".*");
            }
            default -> throw new NoSuchElementException("Sorry, Order cannot be accessed at the moment.");
        }
    }
    /**
     * Selects the sort option by price in descending order.
     * This method assumes the sort option button is already visible on the screen.
     * After selecting the sort option, it clicks on the "priceDesc" element.
     */
    public void selectSortOptionByPriceDescending(){
        Bar.sortOptionButton();
        findElementAccessibilityId("priceDesc").click();
    }
    /**
     * Checks if the items are sorted by price in descending order.
     * This method checks if the expected sorting is applied.
     * Returns true if the items are sorted by price in descending order.
     */
    public boolean isSortedByPriceDescending() {
        switch (Config.platformName) {
            case IOS -> {
                return findElementByRelativeXPath("name", "store item", 1)
                        .getAttribute("label")
                        .matches(".*" + "Sauce Labs Fleece Jacket" + ".*");
            }
            case ANDROID -> {
                return findElementByRelativeXPath("content", "store item text", 1)
                        .getText()
                        .matches(".*" + "Sauce Labs Fleece Jacket" + ".*");
            }
            default -> throw new NoSuchElementException("Sorry, Order cannot be accessed at the moment.");
        }
    }

    /**
     Adds the "Backpack" and "Bike Light" products to the cart by clicking on their respective "Add To Cart" buttons on the "Products" page.*/
    public void backpackAndBikeLightToCart(){
        Linker.Go(Links.Products);
        for (String productName : Arrays.asList("Backpack", "Bike Light")) {
            findElementAccessibilityId("Sauce Labs " + productName).click();
            findElementAccessibilityId("Add To Cart button").click();
            Bar.navigationBack();
        }
        Bar.barOptionCart();
    }

    /**
     Checks whether the Backpack and Bike Light products are added to the cart.
     @return true if both products are added to the cart, false otherwise.
     */
    public boolean isBackpackAndBikeLightAddedToCart(){
        Linker.Go("cart/id=1&amount=1&color=black,id=2&amount=1&color=black");
        boolean isBackpackInCart = findElementByText("Sauce Labs Backpack").isDisplayed();
        boolean isBikeLightInCart = findElementByText("Sauce Labs Bike Light").isDisplayed();
        return isBackpackInCart && isBikeLightInCart;
    }
    /**
     * Performs the selection and customization of multiple products with options.
     * This method adds products to the cart with specified options, increments the counter until a limit is reached,
     * and navigates back to the previous screen after each product selection.
     * Finally, it performs a swipe action and opens the cart options.
     */
    public void multipleProductsWithOptions() {
        performProductWithOptions("Sauce Labs Bike Light", "black circle");
        performProductWithOptions("Sauce Labs Fleece Jacket", "gray circle");
        Pointer.swipeAction(PointerScroll.Direction.SWIPE_UP, "products screen");
        performProductWithOptions("Sauce Labs Onesie", "red circle");
        Bar.barOptionCart();
    }
    /**
     * Performs the selection and customization of a product with options.
     *
     * @param productName The name or accessibility ID of the product element.
     * @param colorOption The name or accessibility ID of the color option element.
     */
    private void performProductWithOptions(String productName, String colorOption) {
        findElementAccessibilityId(productName).click();
        findElementAccessibilityId(colorOption).click();
        incrementCounterUntil();
        findElementAccessibilityId("Add To Cart button").click();
        Bar.navigationBack();
    }
    /**
     * Increments the counter amount until it reaches the specified limit.
     */
    private void incrementCounterUntil() {
        while (Integer.parseInt(findElementAccessibilityId("counter amount").getText()) < 3) {
            findElementAccessibilityId("counter plus button").click();
        }
    }
    /**
     * Verifies if the expected product name is displayed and returns the text.
     *
     * @param expectedProductName The expected product name.
     * @return The displayed product name.
     */
    public String assertProductName(String expectedProductName) {
        return findElementByRelativeXPath(Config.platformName == Platform.IOS ? "label" : "text", expectedProductName, 0).getText();
    }
    /**
     * Verifies if the expected product color is displayed.
     *
     * @param expectedProductColor The expected product color.
     * @return True if the product color is displayed, false otherwise.
     */
    public boolean assertProductColor(String expectedProductColor) {
        return findElementAccessibilityId(expectedProductColor + " circle").isDisplayed();
    }
    /**
     * Verifies if the expected product price is displayed and returns the text.
     *
     * @param expectedProductPrice The expected product price.
     * @return The displayed product price.
     */
    public String assertProductPrice(double expectedProductPrice) {
        return findElementByRelativeXPath(Config.platformName == Platform.IOS ? "label" : "text", "$%s".formatted(expectedProductPrice), 0).getText();

    }
    /**
     * Verifies if the expected product quantity is displayed and returns the value.
     *
     * @param expectedProductQuantity The expected product quantity.
     * @return The displayed product quantity.
     */
    public int assertProductQuantity(int expectedProductQuantity) {
        return Integer.parseInt(findElementByRelativeXPath("name", "counter amount", expectedProductQuantity).getText());
    }
    /**
     * Returns the text of the total price displayed in the cart.
     *
     * @return The total price in the cart.
     */
    public String assertCartTotalPrice() {
        return findElementAccessibilityId("total price").getText();
    }

}
