package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AppiumServer;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Properties;

public abstract class BasePage extends AppiumServer {
    /**
     * Returns the value of the specified property key from the configuration file.
     * If the key is not found, returns null.
     *
     * @return the value of the property, or null if the key is not found
     */
    protected String getProp() {
        Properties props = new Properties();
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream != null) props.load(inputStream);
            else
                throw new RuntimeException("Property file 'config.properties' not found in the classpath");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props.getProperty("appium.remote.platform.name");
    }

    /**
     * Checks if the given WebElement is present by waiting for its invisibility based on the current platform.
     *
     * @throws NoSuchElementException if the platform condition is not available at the moment
     */
    private WebElement isElementPresent(By locator) {
        WebDriverWait wait;
        switch (getProp()) {
            case "iOS" -> wait = new WebDriverWait(iosDriver, Duration.ofSeconds(30), Duration.ofMillis(1000));
            case "Android" -> wait = new WebDriverWait(androidDriver, Duration.ofSeconds(30), Duration.ofMillis(1000));
            default -> throw new IllegalArgumentException("Invalid platform: " + getProp());
        }
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * About Android accessibility
     * <a href="https://developer.android.com/intl/ru/training/accessibility/accessible-app.html">Accessibility</a>
     * About iOS accessibility
     * <a href="https://developer.apple.com/library/ios/documentation/UIKit/Reference/UIAccessibilityIdentification_Protocol/index.html">UIAccessibilityIdentification</a>
     * Finds and returns a WebElement using the provided accessibilityId.
     * @param accessibilityId id is a convenient UI automation accessibility id.
     * @return the web element with the given accessibility ID, instance of {@link AppiumBy.ByAndroidUIAutomator}
     * @throws IllegalStateException if the value returned by getProp() is unexpected
     */
    public WebElement findElement(String accessibilityId) {
        return isElementPresent(AppiumBy.accessibilityId(accessibilityId));
    }

    /**
     * Finds the web element by its XPath locator, using the appropriate driver based on the platform property in the config file.
     *
     * @param value the XPath locator of the web element
     * @return the web element found using the given XPath locator
     */
    public WebElement findElementX(String value) {
        String locator = (Objects.equals(getProp(), "Android"))
                ? "//*[contains(@text,\"%s\")]".formatted(value)
                : "label == \"%s\"".formatted(value);

        return isElementPresent((Objects.equals(getProp(), "Android"))
                ? AppiumBy.xpath(locator)
                : AppiumBy.iOSNsPredicateString(locator));
    }

    /**
     * Finds a web element with the specified ID.
     * If the current platform is Android, the method searches for the element using the AppiumBy.id() method with the given ID.
     * If the current platform is iOS, the method searches for the element using the AppiumBy.id() method with the given ID.
     *
     * @param id the ID of the web element to be found.
     * @return the web element with the specified ID.
     */
    public WebElement findElementId(String id) {
        return (Objects.equals(getProp(), "Android"))
                        ? androidDriver.findElement(AppiumBy.id(id))
                        : iosDriver.findElement(AppiumBy.id(id));
    }
    /**
     * Finds and returns a WebElement using the provided locator value. The element is searched by ID first,
     * and if not found, then by XPath.
     *
     * @param locator the locator value of the element to be found
     * @return WebElement corresponding to the locator value
     * @throws NoSuchElementException if the element cannot be found
     */
    public WebElement findElementOrX(String locator) throws NoSuchElementException {
        try {
            return findElementId(locator);
        } catch (Exception exception) {
            try {
                if ((Objects.equals(getProp(), "Android"))) {
                    return androidDriver.findElement(AppiumBy.xpath("//*[contains(@text,\"%s\")]".formatted(locator)));
                } else {
                    return iosDriver.findElement(AppiumBy.iOSNsPredicateString("label == ".formatted(locator)));
                }
            } catch (Exception exception1){
                try {
                    return iosDriver.findElement(AppiumBy.iOSClassChain(locator));
                } catch (Exception exception2){
                    return androidDriver.findElement(AppiumBy.xpath("(//*[@content-desc=\"%s\"])[1]".formatted(locator)));
                }
            }
        }
    }
}
