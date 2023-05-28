package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Logger LOGGER = LogManager.getLogger(Config.class.getName());

    public enum Platform {
        ANDROID,
        IOS
    }
    public static Platform platformName = getPlatformName();

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
