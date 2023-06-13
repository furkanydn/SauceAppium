package pages;

import components.AppBar;
import components.MenuItem;
import org.junit.jupiter.api.Assertions;
import utils.Config;
import utils.Linker;

public class LoginPage extends BasePage {
    MenuItem menu = new MenuItem();
    /**
     * Navigates to the login page using a link.
     */
    public void goLoginPageWithLink(){
        Linker.Go(Linker.Links.Login);
    }
    /**
     * Navigates to the login page by performing necessary clicks
     */
    public void goLoginPageWithClicks(){
        menu.LogIn();
    }

    private void clickLoginButton(){
        findElementAccessibilityId("Login button").click();
    }

    private void closeKeyboard(){
        switch (Config.platformName){
            case ANDROID -> new AppBar().navigationBack();
            case IOS -> findElementAccessibilityId("Return").click();
        }
    }

    public void LoginInvalidCredentials(){
        findElementAccessibilityId("Username input field").sendKeys("logintest@mailaddress.com");
        findElementAccessibilityId("Password input field").sendKeys("12345678");
        closeKeyboard();
        clickLoginButton();
        Assertions.assertEquals("Provided credentials do not match any user in this service.",findElementAccessibilityId("generic-error-message").getText());
    }
}
