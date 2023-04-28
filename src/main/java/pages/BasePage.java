package pages;

import io.appium.java_client.AppiumBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.AppiumServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public abstract class BasePage extends AppiumServer {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Returns the value of the specified property key from the configuration file.
     * If the key is not found, returns null.
     *
     * @param key the key of the property
     * @return the value of the property, or null if the key is not found
     */
    protected String getProp(String key) throws IOException {
        Properties props = new Properties();
        InputStream inputStream = null;
        try {
            String fileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            if (inputStream!=null) {
                props.load(inputStream);
            } else
                throw new RuntimeException("Property file '" + fileName + "' not found in the classpath");
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception){
                exception.printStackTrace();
            }
        }
        return props.getProperty(key);
    }


    /**
     * About Android accessibility
     * <a href="https://developer.android.com/intl/ru/training/accessibility/accessible-app.html">Accessibility</a>
     * About iOS accessibility
     * <a href="https://developer.apple.com/library/ios/documentation/UIKit/Reference/UIAccessibilityIdentification_Protocol/index.html">UIAccessibilityIdentification</a>
     * @param accessibilityId id is a convenient UI automation accessibility id.
     * @return an instance of {@link AppiumBy.ByAndroidUIAutomator}
     */
    public WebElement findElement(String accessibilityId) throws IOException {
        LOGGER.info(accessibilityId +" clicked.");
        return
                (Objects.equals(getProp("appium.remote.platform.name"), "Android"))
                        ? androidDriver.findElement(AppiumBy.accessibilityId(accessibilityId))
                        : iosDriver.findElement(AppiumBy.accessibilityId(accessibilityId));
    }


    // Add description
    public WebElement findElementX(String xPath) throws IOException {
        return
                (Objects.equals(getProp("appium.remote.platform.name"), "Android"))
                        ? androidDriver.findElement(By.xpath(xPath))
                        : iosDriver.findElement(By.xpath(xPath));
    }
}
