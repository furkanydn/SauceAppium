package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Configuration class for platform-specific settings.
 */
public class Config {
    private static final Logger LOGGER = LogManager.getLogger(Config.class.getName());
    private static Map<String, String> configMap = new HashMap<>();
    /**
     * Enumeration for supported platforms.
     */
    public enum Platform {
        ANDROID,
        IOS
    }
    /**
     * Holds the current platform value.
     */
    public static Platform platformName = getPlatformName();
    /**
     * Retrieves the platform name based on the configured value.
     *
     * @return The platform name as {@link Platform} enum value.
     * @throws IllegalArgumentException If the platform value is missing or not supported.
     */
    static Platform getPlatformName(){
        String platform = getProperties("appium.remote.platform.name");
        if (platform == null || platform.isEmpty()) throw new IllegalArgumentException("Platform value is missing.");
        return "Android".equalsIgnoreCase(platform) ? Platform.ANDROID : Platform.IOS;
    }

    /**
     * Gets the value of a property from the "config.properties" file.
     *
     * @param value the key of the property to retrieve
     * @return the value of the property
     */
    public static String getProperties(String value){
        Properties props = new Properties();
        try {FileInputStream input = new FileInputStream("src/main/resources/config.properties");
            LOGGER.info("config.properties read completed.");
            props.load(input);
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return props.getProperty(value);
    }
}
