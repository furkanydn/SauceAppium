package pages;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.AppiumServer;

public class LoginTest {
    LoginPage page = new LoginPage();
    @BeforeAll
    public static void beforeAll() {
        AppiumServer.start();
    }

    @AfterAll
    public static void afterAll() {
        AppiumServer.stop();
    }

    @Test
    void testEmptyFieldsLogin(){
        page.goLoginPageWithLink();
    }

    @Test
    void testFailedLoginInvalidCredentials(){
        page.goLoginPageWithClicks();
    }

    @Test
    void testLockedValidCredentials(){

    }

    @Test
    void testSuccessfulLogin(){

    }
}
