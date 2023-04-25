package com.appium.sauceappium.definitions;

import com.appium.sauceappium.pages.Login;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDefinition {
    @When("Enter mail as {string}")
    public void enterMailAs(String arg0) {
        new Login().enterUserName(arg0);
    }

    @And("Enter password as {string}")
    public void enterPasswordAs(String arg0) {
    }

    @And("Click the login button")
    public void clickTheLoginButton() {
    }

    @Then("Login should fail with an error {string}")
    public void loginShouldFailWithAnError(String arg0) {
    }

    @Then("The Account locked out notification will appear at the bottom.")
    public void theAccountLockedOutNotificationWillAppearAtTheBottom() {
    }

    @Then("if the given mail and password are correct successful login is possible")
    public void ifTheGivenMailAndPasswordAreCorrectSuccessfulLoginIsPossible() {
    }
}
