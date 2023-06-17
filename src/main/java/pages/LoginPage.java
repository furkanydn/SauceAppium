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
    /**
     * Clicks the login button.
     */
    private void clickLoginButton(){
        findElementAccessibilityId("Login button").click();
    }
    /**
     * Closes the keyboard based on the platform.
     */
    private void closeKeyboard(){
        switch (Config.platformName){
            case ANDROID -> androidDriver.hideKeyboard();
            case IOS -> findElementAccessibilityId("Return").click();
        }
    }
    /**
     * Enters the username and password credentials.
     *
     * @param username The username to enter.
     * @param password The password to enter.
     */
    private void enterCredentials(String username, String password) {
        findElementAccessibilityId("Username input field").sendKeys(username);
        findElementAccessibilityId("Password input field").sendKeys(password);
        closeKeyboard();
    }
    /**
     * Retrieves the generic error message based on the platform.
     *
     * @param value The value to search for in the error message element.
     * @return The text of the generic error message.
     */
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
    /**
     * Performs login with invalid credentials and verifies the error message.
     */
    public void loginInvalidCredentials(){
        enterCredentials(TestData.EMAIL_INVALID, TestData.PASS_INVALID);
        clickLoginButton();
        String message = "Provided credentials do not match any user in this service.";
        Assertions.assertEquals(message, getGenericErrorMessage(message));
    }
    /**
     * Performs login with locked credentials and verifies the error message.
     */
    public void lockValidCredentials(){
        enterCredentials(TestData.EMAIL_LOCKED, TestData.PASS_VALID);
        clickLoginButton();
        String message = "Sorry, this user has been locked out.";
        Assertions.assertEquals(message, getGenericErrorMessage(message));
    }
    /**
     * Performs successful login without any error messages.
     */
    public void successfulLogin(){
        enterCredentials(TestData.EMAIL_VALID, TestData.PASS_VALID);
        clickLoginButton();
    }
}
