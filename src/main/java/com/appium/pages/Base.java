package com.appium.pages;

import com.appium.manager.DriverManager;
import com.appium.utils.TestUtilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class Base {
    /**
     * The available {@link AppiumDriver}.
     * */
    private AppiumDriver appiumDriver;
    TestUtilities testUtilities = new TestUtilities();

    public Base(){
        this.appiumDriver = new DriverManager().getDriverThreadLocal();
        PageFactory.initElements(new AppiumFieldDecorator(this.appiumDriver),this);
    }

    public void waitForVisible(WebElement wE){
        WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(TestUtilities.WAITTIME));
        wait.until(ExpectedConditions.visibilityOf(wE));
    }
    public void waitForVisibleBy(By by){
        WebDriverWait wait = new WebDriverWait(appiumDriver, Duration.ofSeconds(TestUtilities.WAITTIME));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void clear(WebElement webElement){
        waitForVisible(webElement);
        webElement.clear();
    }

    public void click(WebElement webElement){
        waitForVisible(webElement);
        webElement.click();
    }

    public void click(WebElement webElement,String message){
        waitForVisible(webElement);
        testUtilities.logger().info(message + "clicked");
        webElement.click();
    }

    public void sendKeys(WebElement webElement,String text, String message){
        waitForVisible(webElement);
        testUtilities.logger().info(message + "clicked");
        webElement.sendKeys(text);
    }

    public String getText(WebElement element, String message){
        String text;
        try {
            text = element.getText();
            testUtilities.logger().info("Message: " + message + " - Text: " + text);
            return text;
        } catch (IllegalStateException stateException){
            throw new IllegalStateException("Unexpected value " + stateException);
        }
    }
}
