package components;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import pages.BasePage;
import touch.PointerScroll;
import utils.Config;

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

    public void navigationBack(){
        String platformName = Config.getProperties("appium.remote.platform.name");
        switch (platformName) {
            case "iOS" -> findElement("navigation back button").click();
            case "Android" -> androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
            default -> throw new RuntimeException("Can We Please Go Back");
        }
    }

    public void barOptionCart(){
        String platformName = Config.getProperties("appium.remote.platform.name");
        switch (platformName) {
            case "iOS" -> findElement("tab bar option cart").click();
            case "Android" -> cartBadge();
            default -> throw new RuntimeException("Sorry, Cart cannot be accessed at the moment.");
        }
    }
    public void selectSortOptionByNameAscending() {
        sortOptionButton();
        findElement("nameAsc").click();
    }
    public boolean isSortedByNameAscending() {
        return findElementOrX("**/XCUIElementTypeOther[`label == \"Sauce Labs Backpack $29.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF Sauce Labs Bike Light $9.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF\"`][1]")
                .getAttribute("label")
                .matches(
                        ".*" + "Sauce Labs Backpack" + ".*");
    }

    public void selectSortOptionByNameDescending(){
        sortOptionButton();
        findElement("nameDesc").click();
    }
    public boolean isSortedByNameDescending() {
        return findElementOrX("**/XCUIElementTypeOther[`label == \"Test.allTheThings() T-Shirt $15.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF Sauce Labs Onesie $7.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF\"`][1]")
                .getAttribute("label")
                .matches(
                        ".*" + "Test.allTheThings" + ".*");
    }

    public void selectSortOptionByPriceAscending(){
        sortOptionButton();
        findElement("priceAsc").click();
    }
    public boolean isSortedByPriceAscending() {
        return findElementOrX("**/XCUIElementTypeOther[`label == \"Sauce Labs Onesie $7.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF Sauce Labs Bike Light $9.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF\"`][1]")
                .getAttribute("label")
                .matches(
                        ".*" + "Sauce Labs Onesie" + ".*");
    }

    public void selectSortOptionByPriceDescending(){
        sortOptionButton();
        findElement("priceDesc").click();
    }
    public boolean isSortedByPriceDescending() {
        return findElementOrX("**/XCUIElementTypeOther[`label == \"Sauce Labs Fleece Jacket $49.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF Sauce Labs Backpack $29.99 \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF \uDB81\uDCCF\"`][1]")
                .getAttribute("label")
                .matches(
                        ".*" + "Sauce Labs Fleece Jacket" + ".*");
    }
}
