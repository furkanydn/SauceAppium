package com.appium.sauceappium.manager;

import com.appium.sauceappium.utils.TestUtilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private static final Properties properties = new Properties();
    TestUtilities testUtilities = new TestUtilities();

    public Properties getProperties() throws IOException {
        InputStream inputStream = null;
        String propertyName = "config.properties";

        if (!properties.isEmpty()) {
            return properties;
        }
        try {
            testUtilities.logger().info("Config properties loading");
            inputStream = getClass().getClassLoader().getResourceAsStream(propertyName);
            properties.load(inputStream);
        } catch (IOException IOe) {
            IOe.printStackTrace();
            testUtilities.logger().fatal("Unable to load the configuration from the properties" + IOe);
            throw IOe;
        } finally {
            if (inputStream!=null) inputStream.close();
        }
        return properties;
    }
}
