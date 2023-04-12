package com.appium.pages;

import com.appium.manager.DriverManager;
import com.appium.utils.TestUtilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public final class Base {
    TestUtilities testUtilities = new TestUtilities();

    public Base(){
        /**
         * The available {@link AppiumDriver}.
         * */
        AppiumDriver appiumDriver = new DriverManager().getDriverThreadLocal();
        PageFactory.initElements(new AppiumFieldDecorator(appiumDriver),this);
    }


}
