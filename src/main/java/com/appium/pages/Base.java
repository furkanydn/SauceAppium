package com.appium.pages;

import com.appium.manager.DriverManager;
import com.appium.utils.TestUtilities;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Base {
    protected static AppiumDriver baseDriver;

    public Base(){
        /**
         * The available {@link AppiumDriver}.
         * */
        baseDriver = new DriverManager().getDriverThreadLocal();
        PageFactory.initElements(new AppiumFieldDecorator(baseDriver),this);
    }

    /**
     * Configure the amount of time that a particular type of operation can execute for before they are aborted
     * @param by ui component type
     */
    public void waitForVisible(AppiumBy by){
        //baseDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtilities.WAITTIME));
        WebDriverWait wait = new WebDriverWait(baseDriver,Duration.ofSeconds(TestUtilities.WAITTIME));
        wait.pollingEvery(Duration.ofSeconds(TestUtilities.WAITTIME));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
