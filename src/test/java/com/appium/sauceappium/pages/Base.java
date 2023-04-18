package com.appium.sauceappium.pages;

import com.appium.sauceappium.manager.DriverManager;
import com.appium.sauceappium.utils.TestUtilities;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class Base {
    protected static AppiumDriver baseDriver;
    TestUtilities utilities = new TestUtilities();

    public Base(){
        baseDriver = new DriverManager().getDriverLocal();
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

    /**
     * Click this element. If this causes a new page to load, you
     * should discard all references to this element and any further
     * operations performed on this element will throw a
     * StaleElementReferenceException.
     *
     * @throws StaleElementReferenceException If the element no
     *     longer exists as initially defined
     */
    public void click(AppiumBy by) {
        waitForVisible(by);
        try {
            utilities.logger().info("Clicked by " + by);
            //Temporary
            baseDriver.findElement(by).click();
        } catch (StaleElementReferenceException exception) {
            exception.printStackTrace();
        }
    }
    /**
     * Use this method to simulate typing into an element, which may set its value.
     *
     * @param keysToSend character sequence to send to the element
     *
     * @throws IllegalArgumentException if keysToSend is null
     */
    public void sendKeys(AppiumBy by, String keysToSend){
        waitForVisible(by);
        try {
            utilities.logger().info("Element is %s and keys to %s".formatted(by, keysToSend));
            //Temporary
            baseDriver.findElement(by).sendKeys(keysToSend);
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
        }
    }
    /**
     * Get the visible (i.e. not hidden by CSS) text of this element, including sub-elements.
     *
     * @return The visible text of this element.
     */
    public String getKeys(AppiumBy by){
        waitForVisible(by);
        String text = null;
        try {
            //Temporary
            text = baseDriver.findElement(by).getText();
            utilities.logger().info("Element is %s and getting keys to %s".formatted(by, text));
        } catch (NoSuchElementException exception) {
            exception.printStackTrace();
        }
        return text;
    }
}
