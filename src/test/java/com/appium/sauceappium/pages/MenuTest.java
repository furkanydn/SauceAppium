package com.appium.sauceappium.pages;

import com.appium.sauceappium.utils.TestUtilities;
import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;

//@Execution(ExecutionMode.CONCURRENT) Running tests in parallel
public class MenuTest extends BaseDriver {
    TestUtilities testUtilities = new TestUtilities();

    @Test
    public void setOpenMenu(){
        iosDriver.findElement(AppiumBy.accessibilityId("open menu")).click();
    }


}

