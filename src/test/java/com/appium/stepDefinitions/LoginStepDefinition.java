package com.appium.stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDefinition {
    @When("Enter username as {string}")
    public void enterUsernameAs(String arg0) {

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
}
