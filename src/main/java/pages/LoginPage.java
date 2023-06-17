package pages;

import components.AppBar;
import components.MenuItem;
import org.junit.jupiter.api.Assertions;
import utils.Config;
import utils.Linker;
import utils.TestData;

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
    private void enterCredentials(String username, String password) {
        findElementAccessibilityId("Username input field").sendKeys(username);
        findElementAccessibilityId("Password input field").sendKeys(password);
        closeKeyboard();
    }

    private String getGenericErrorMessage() {
        return findElementAccessibilityId("generic-error-message").getText();
    }

    public void loginInvalidCredentials(){
        enterCredentials(TestData.EMAIL_INVALID, TestData.PASS_INVALID);
        clickLoginButton();
        Assertions.assertEquals("Provided credentials do not match any user in this service.", getGenericErrorMessage());
    }

    public void lockValidCredentials(){
        enterCredentials(TestData.EMAIL_LOCKED, TestData.PASS_VALID);
        clickLoginButton();
        Assertions.assertEquals("Sorry, this user has been locked out.", getGenericErrorMessage());
    }

    public void successfulLogin(){
        enterCredentials(TestData.EMAIL_VALID, TestData.PASS_VALID);
        clickLoginButton();
    }
}
