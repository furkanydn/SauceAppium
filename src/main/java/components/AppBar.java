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
     Clicks on the "Sort" button and selects the given sorting option.
     @param sortComp the sorting option to select.
     */
    public void sortButton(SortComp sortComp){
        findElement("sort button").click();
        //findElement(getSortingName(sortComp)).click();
    }
    /**
     Clicks on the cart badge icon.
     */
    public void cartBadge(){
        findElement("cart badge").click();
    }
    /**
     An enum that represents the available sorting options.
     */
    public enum SortComp {
        /**Sort by name in ascending order*/
        NAME_ASC,
        /**Sort by name in descending order*/
        NAME_DESC,
        /**Sort by price in ascending order*/
        PRICE_ASC,
        /**Sort by price in ascending order*/
        PRICE_DESC
    }
    /**
     Returns the name of the sorting option as a string.
     @param sortComp the sorting option to get the name for.
     @return the name of the sorting option as a string.
     @throws IllegalArgumentException if an invalid sorting option is provided.
     */
    public static String getSortingName(SortComp sortComp) {
        switch (sortComp) {
            case NAME_ASC -> {
                return "nameAsc";
            }
            case NAME_DESC -> {
                return "nameDesc";
            }
            case PRICE_ASC -> {
                return "priceAsc";
            }
            case PRICE_DESC -> {
                return "priceDesc";
            }
            default -> throw new IllegalArgumentException("Invalid sorting name " + sortComp);
        }
    }

    public void navigationBack(){
        switch (Config.getProperties("appium.remote.platform.name")) {
            case "iOS" -> findElement("navigation back button").click();
            case "Android" -> androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
            default -> throw new RuntimeException("Can We Please Go Back");
        }

    }

    public void barOptionCart(){
        switch (Config.getProperties("appium.remote.platform.name")) {
            case "iOS" -> findElement("tab bar option cart").click();
            case "Android" -> cartBadge();
            default -> throw new RuntimeException("Sorry, Cart cannot be accessed at the moment.");
        }
    }
}
