package pages;

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
            case ANDROID -> androidDriver.hideKeyboard();
            case IOS -> findElementAccessibilityId("Return").click();
        }
    }
    private void enterCredentials(String username, String password) {
        findElementAccessibilityId("Username input field").sendKeys(username);
        findElementAccessibilityId("Password input field").sendKeys(password);
        closeKeyboard();
    }

    private String getGenericErrorMessage(String value) {
        switch (Config.platformName){
            case ANDROID -> {
                return findElementByText(value).getText();
            }
            case IOS -> {
                return findElementAccessibilityId("generic-error-message").getText();
            }
            default -> {
                return null;
            }
        }
    }

    public void loginInvalidCredentials(){
        enterCredentials(TestData.EMAIL_INVALID, TestData.PASS_INVALID);
        clickLoginButton();
        String message = "Provided credentials do not match any user in this service.";
        Assertions.assertEquals(message, getGenericErrorMessage(message));
    }

    public void lockValidCredentials(){
        enterCredentials(TestData.EMAIL_LOCKED, TestData.PASS_VALID);
        clickLoginButton();
        String message = "Sorry, this user has been locked out.";
        Assertions.assertEquals(message, getGenericErrorMessage(message));
    }

    public void successfulLogin(){
        enterCredentials(TestData.EMAIL_VALID, TestData.PASS_VALID);
        clickLoginButton();
    }
}
