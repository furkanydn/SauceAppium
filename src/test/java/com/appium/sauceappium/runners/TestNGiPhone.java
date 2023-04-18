package com.appium.sauceappium.runners;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        plugin = {
                "pretty","html:target_TestNG/iPhone/report.html",
                "summary",
                "me.jvt.cucumber.report.PrettyReports:target_TestNG/iPhone/" },
        features = {"src/test/resources/features"},
        glue = {"com.appium.stepDefinitions"},
        monochrome = true,
        tags = "@login-feature")
public class TestNGiPhone extends BaseRunner{
}
