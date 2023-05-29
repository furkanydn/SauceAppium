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
        findElementAccessibilityId("open menu").click();
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
        findElementAccessibilityId("sort button").click();
    }
    /**
     Clicks on the cart badge icon.
     */
    public void cartBadge(){
        findElementAccessibilityId("cart badge").click();
    }
    /**
     * Navigates back in the application.
     * This method identifies the platform name and performs the back navigation accordingly.
     * Supports iOS and Android platforms.
     * Throws a RuntimeException if the platform name is unknown.
     */
    public void navigationBack(){
        switch (Config.platformName) {
            case IOS -> findElementAccessibilityId("navigation back button").click();
            case ANDROID -> androidDriver.pressKey(new KeyEvent(AndroidKey.BACK));
            default -> throw new RuntimeException("Can we please go back");
        }
    }
    /**
     * Opens the cart option in the tab bar.
     * This method identifies the platform name and opens the cart accordingly.
     * Supports iOS and Android platforms.
     * Throws a RuntimeException if the platform name is unknown.
     */
    public void barOptionCart(){
        switch (Config.platformName) {
            case IOS -> findElementAccessibilityId("tab bar option cart").click();
            case ANDROID -> cartBadge();
            default -> throw new RuntimeException("Sorry, Cart cannot be accessed at the moment.");
        }
    }
}
