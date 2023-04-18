package com.appium.pages;

import com.appium.utils.TestUtilities;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class Menu extends Base {
    TestUtilities testUtilities = new TestUtilities();

    @AndroidFindBy(accessibility = "open menu")
    @iOSXCUITFindBy(accessibility = "open menu")
    private WebElement openMenuElement;

    public void setOpenMenu(){
        click((AppiumBy) openMenuElement);
    }
}
