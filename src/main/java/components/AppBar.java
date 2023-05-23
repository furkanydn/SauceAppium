package components;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import pages.BasePage;
import touch.PointerScroll;
import utils.Config;

import java.util.NoSuchElementException;

/**
 The AppBar class represents the navigation bar that is present in most mobile applications.
 It extends the BasePage class and inherits its methods.
 */
public class AppBar extends BasePage {
    /**
     * Clicks on the "Open Menu" button to reveal the menu options.
     */
    public void openMenu() {
        findElement("open menu").click();
    }
    /**
     Scrolls horizontally to hide the Android menu.
     */
    public void closeAndroidMenu(){
        PointerScroll pointerScroll = new PointerScroll();
        pointerScroll.HorizontalScroll("android:id/content",0.8,0.2);
    }
    /**
     Clicks on the "Sort" button
     */
    public void sortOptionButton(){
        findElement("sort button").click();
    }
    /**
     Clicks on the cart badge icon.
     */
    public void cartBadge(){
        findElement("cart badge").click();
    }
    /**
     * Navigates back in the application.
     * This method identifies the platform name and performs the back navigation accordingly.
     * Supports iOS and Android platforms.
     * Throws a RuntimeException if the platform name is unknown.
     */
    public void navigationBack(){
        String platformName = Config.getProperties("appium.remote.platform.name");
        switch (platformName) {
            case "iOS" -> findElement("navigation back button").click();
            case "Android" -> androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
            default -> throw new RuntimeException("Can We Please Go Back");
        }
    }
    /**
     * Opens the cart option in the tab bar.
     * This method identifies the platform name and opens the cart accordingly.
     * Supports iOS and Android platforms.
     * Throws a RuntimeException if the platform name is unknown.
     */
    public void barOptionCart(){
        String platformName = Config.getProperties("appium.remote.platform.name");
        switch (platformName) {
            case "iOS" -> findElement("tab bar option cart").click();
            case "Android" -> cartBadge();
            default -> throw new RuntimeException("Sorry, Cart cannot be accessed at the moment.");
        }
    }
    /**
     * Selects the sort option by name in ascending order.
     * This method assumes the sort option button is already visible on the screen.
     * After selecting the sort option, it clicks on the "nameAsc" element.
     */
    public void selectSortOptionByNameAscending() {
        sortOptionButton();
        findElement("nameAsc").click();
    }
    /**
     * Checks if the items are sorted by name in ascending order.
     * This method identifies the platform name and checks if the expected sorting is applied.
     * Supports iOS and Android platforms.
     * Returns true if the items are sorted by name in ascending order.
     * Throws a NoSuchElementException if the order cannot be accessed at the moment.
     */
    public boolean isSortedByNameAscending() {
        String platformName = Config.getProperties("appium.remote.platform.name");
        switch (platformName) {
            case "iOS" -> {
                return findElementOrX("**/XCUIElementTypeOther[`label == \"Sauce Labs Backpack $29.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF Sauce Labs Bike Light $9.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF\"`][1]")
                        .getAttribute("label")
                        .matches(".*" + "Sauce Labs Backpack" + ".*");
            }
            case "Android" -> {
                return findElementOrX("store item text")
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
        sortOptionButton();
        findElement("nameDesc").click();
    }
    /**
     * Checks if the items are sorted by name in descending order.
     * This method identifies the platform name and checks if the expected sorting is applied.
     * Supports iOS and Android platforms.
     * Returns true if the items are sorted by name in descending order.
     * Throws a NoSuchElementException if the order cannot be accessed at the moment.
     */
    public boolean isSortedByNameDescending() {
        String platformName = Config.getProperties("appium.remote.platform.name");
        switch (platformName) {
            case "iOS" -> {
                return findElementOrX("**/XCUIElementTypeOther[`label == \"Test.allTheThings() T-Shirt $15.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF Sauce Labs Onesie $7.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF\"`][1]")
                        .getAttribute("label")
                        .matches(".*" + "Test.allTheThings" + ".*");
            }
            case "Android" -> {
                return findElementOrX("store item text")
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
        sortOptionButton();
        findElement("priceAsc").click();
    }
    /**
     * Checks if the items are sorted by price in ascending order.
     * This method checks if the expected sorting is applied.
     * Returns true if the items are sorted by price in ascending order.
     */
    public boolean isSortedByPriceAscending() {
        return findElementOrX("**/XCUIElementTypeOther[`label == \"Sauce Labs Onesie $7.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF Sauce Labs Bike Light $9.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF\"`][1]")
                .getAttribute("label")
                .matches(".*" + "Sauce Labs Onesie" + ".*");
    }
    /**
     * Selects the sort option by price in descending order.
     * This method assumes the sort option button is already visible on the screen.
     * After selecting the sort option, it clicks on the "priceDesc" element.
     */
    public void selectSortOptionByPriceDescending(){
        sortOptionButton();
        findElement("priceDesc").click();
    }
    /**
     * Checks if the items are sorted by price in descending order.
     * This method checks if the expected sorting is applied.
     * Returns true if the items are sorted by price in descending order.
     */
    public boolean isSortedByPriceDescending() {
        return findElementOrX("**/XCUIElementTypeOther[`label == \"Sauce Labs Fleece Jacket $49.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF Sauce Labs Backpack $29.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF\"`][1]")
                .getAttribute("label")
                .matches(".*" + "Sauce Labs Fleece Jacket" + ".*");
    }
}
