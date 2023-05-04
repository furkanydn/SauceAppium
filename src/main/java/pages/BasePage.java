package pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.AppiumServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public abstract class BasePage extends AppiumServer {

    /**
     Returns the value of the specified property key from the configuration file.
     If the key is not found, returns null.
     @return the value of the property, or null if the key is not found
     */
    protected String getProp(){
        Properties props = new Properties();
        InputStream inputStream = null;
        try {
            String fileName = "config.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            if (inputStream!=null) {
                try {
                    props.load(inputStream);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else
                throw new RuntimeException("Property file '" + fileName + "' not found in the classpath");
        } finally {
            try {
                assert inputStream != null;
                inputStream.close();
            } catch (IOException exception){
                exception.printStackTrace();
            }
        }
        return props.getProperty("appium.remote.platform.name");
    }


    /**
     About Android accessibility
     <a href="https://developer.android.com/intl/ru/training/accessibility/accessible-app.html">Accessibility</a>
     About iOS accessibility
     <a href="https://developer.apple.com/library/ios/documentation/UIKit/Reference/UIAccessibilityIdentification_Protocol/index.html">UIAccessibilityIdentification</a>
     @param accessibilityId id is a convenient UI automation accessibility id.
     @return the web element with the given accessibility ID, instance of {@link AppiumBy.ByAndroidUIAutomator}
     */
    public WebElement findElement(String accessibilityId){
        return
                (Objects.equals(getProp(), "Android"))
                        ? androidDriver.findElement(AppiumBy.accessibilityId(accessibilityId))
                        : iosDriver.findElement(AppiumBy.accessibilityId(accessibilityId));
    }


    /**
     Finds the web element by its XPath locator, using the appropriate driver based on the platform property in the config file.
     @param xPath the XPath locator of the web element
     @return the web element found using the given XPath locator
     */
    public WebElement findElementX(String xPath){
        return
                (Objects.equals(getProp(), "Android"))
                        ? androidDriver.findElement(By.xpath(xPath))
                        : iosDriver.findElement(By.xpath(xPath));
    }

    public WebElement findElementId(String id){
        return
                (Objects.equals(getProp(), "Android"))
                        ? androidDriver.findElement(AppiumBy.id(id))
                        : iosDriver.findElement(AppiumBy.id(id));
    }
}
