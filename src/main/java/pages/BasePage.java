package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import touch.PointerScroll;
import utils.AppiumServer;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;

public abstract class BasePage extends AppiumServer {
    private Properties props = null;
    /**
     * Returns the value of the specified property key from the configuration file.
     * If the key is not found, returns null.
     */
    protected String getPlatform() {
        if (props == null){
            props = new Properties();
            try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
                if (inputStream != null) props.load(inputStream);
                else
                    throw new RuntimeException("Property file 'config.properties' not found in the classpath");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
        switch (getPlatform().toLowerCase()) {
            case "ios" -> wait = new WebDriverWait(iosDriver, Duration.ofSeconds(30), Duration.ofMillis(1000));
            case "android" -> wait = new WebDriverWait(androidDriver, Duration.ofSeconds(30), Duration.ofMillis(1000));
            default -> throw new IllegalArgumentException("Invalid platform: " + getPlatform());
        }
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    /**
     * Find and return the WebElement using the given locator.
     *
     * @param locator the locator used to find the WebElement
     * @return the WebElement if found
     * @throws NoSuchElementException if the WebElement is not found
     */
    private WebElement findElement(By locator){
        int maxRetries = 3;
        for (int retryCount = 0; retryCount < maxRetries; retryCount++) {
            try {
                return isElementPresent(locator);
            } catch (NoSuchElementException e) {
                new PointerScroll().swipeAction(PointerScroll.Direction.SWIPE_DOWN);
                findElement(locator);
            }
        }
        throw new NoSuchElementException("Element not found: " + locator);
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
    public WebElement findElementAccessibilityId(String accessibilityId) {
        return findElement(AppiumBy.accessibilityId(accessibilityId));
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
        return findElement(AppiumBy.id(id));
    }
    /**
     * Finds the web element by its XPath locator, using the appropriate driver based on the platform property in the config file.
     *
     * @param text the XPath locator of the web element
     * @return the web element found using the given XPath locator
     */
    public WebElement findElementByText(String text) {
        return getPlatform().equals("Android")
                ? findElementByRelativeXPath("text",text,0)
                : findElementByRelativeXPath("label",text,0);
    }
    /**
     * Finds and returns a WebElement based on the specified search criteria.
     *
     * @param type         the type of search criteria to be used ("content", "text", "name", "label", or "chain")
     * @param contentText  the content text or label value to search for
     * @param arrayValue   the array value used for indexing elements when needed (applicable to "content" and "name" types)
     * @return the found WebElement
     * @throws IllegalArgumentException if the parameters are invalid or not valid for the specified search type
     */
    public WebElement findElementByRelativeXPath(String type, String contentText, int arrayValue) {
        if (!(0 <= arrayValue && arrayValue <= 10) || contentText == null || contentText.isEmpty())
            throw new IllegalArgumentException("Invalid parameters: text or value is not valid.");

        switch (type) {
            case "content" -> {
                return findElement(AppiumBy.xpath("(//*[contains(@content-desc,'%s')])['%d']".formatted(contentText, arrayValue)));
            }
            case "text" -> {
                return findElement(AppiumBy.xpath("//*[contains(@text,'%s')]".formatted(contentText)));
            }
            case "name" -> {
                return findElement(AppiumBy.xpath("(//*[contains(@name,'%s')])['%d']".formatted(contentText, arrayValue)));
            }
            case "label" -> {
                return findElement(AppiumBy.iOSNsPredicateString("label == \"%s\"".formatted(contentText)));
            }
            case "chain" -> {
                return findElement(AppiumBy.iOSClassChain(contentText));
            }
            default -> throw new IllegalArgumentException("Invalid parameter: type is not valid.");
        }
    }
}
