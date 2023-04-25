package com.appium.sauceappium.pages;

import com.appium.sauceappium.manager.BaseDriver;
import com.appium.sauceappium.utils.TestUtilities;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

public class MenuTest extends BaseDriver {
    TestUtilities testUtilities = new TestUtilities();

    @Test
    public void setOpenMenu(){
        //clickAccessibilityId(MenuTestConst.openMenuElement);
        driver.findElement(AppiumBy.accessibilityId(MenuTestConst.openMenuElement)).click();
        //getDriverLocal().findElement(AppiumBy.accessibilityId(MenuTestConst.openMenuElement)).click();
    }
}

class MenuTestConst {
    static String openMenuElement="open menu";
}