package components;

import pages.BasePage;

public class MenuItem extends BasePage {
    /**
     * Clicks on the "Open Menu" button to reveal the menu options.
     */
    public void OpenMenu() {
        findElement("open menu").click();
    }
    /**
     * Clicks on the specified menu item after first checking if the menu is open.
     *
     * @param menuItem the name of the menu item to click (e.g. "catalog")
     */
    public void clickMenuItem(String menuItem) {
        if (findElement("open menu").isDisplayed()) OpenMenu();
        findElement("menu item " + menuItem.toLowerCase()).click();
    }
    /**
     * Clicks on the "Catalog" menu item.
     */
    public void Catalog() {
        clickMenuItem("catalog");
    }
    /**
     * Clicks on the "Webview" menu item.
     */
    public void Webview(){
        clickMenuItem("webview");
    }
    /**
     * Clicks on the "QrCodeScanner" menu item.
     */
    public void QrCodeScanner(){
        clickMenuItem("qr code scanner");
    }
    /**
     * Clicks on the "GeoLocation" menu item.
     */
    public void GeoLocation(){
        clickMenuItem("geo location");
    }
    /**
     * Clicks on the "Drawing" menu item.
     */
    public void Drawing(){
        clickMenuItem("drawing");
    }
    /**
     * Clicks on the "About" menu item.
     */
    public void About(){
        clickMenuItem("about");
    }
    /**
     * Clicks on the "ResetApp" menu item.
     */
    public void ResetApp(){
        clickMenuItem("reset app");
    }
    /**
     * Clicks on the "Biometrics" menu item.
     */
    public void Biometrics(){
        clickMenuItem("biometrics");
    }
    /**
     * Clicks on the "LogIn" menu item.
     */
    public void LogIn(){
        clickMenuItem("log in");
    }
    /**
     * Clicks on the "LogOut" menu item.
     */
    public void LogOut(){
        clickMenuItem("log out");
    }
    /**
     * Clicks on the "ApiCall" menu item.
     */
    public void ApiCall(){
        clickMenuItem("api calls");
    }
    /**
     * Clicks on the "SauceBot" menu item.
     */
    public void SauceBot(){
        clickMenuItem("sauce bot video");
    }
}
