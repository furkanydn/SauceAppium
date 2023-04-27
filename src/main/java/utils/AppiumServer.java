package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppiumServer {
    private static final Logger LOGGER = LogManager.getLogger();
    public static void start() {
        Properties props = new Properties();
        try {
            FileInputStream input = new FileInputStream("config.properties");
            LOGGER.info("config.properties read completed.");
            props.load(input);
        } catch (IOException exception){
            exception.printStackTrace();
        }
        LOGGER.info(Const.INITIALIZING_APPIUM_DRIVER);
        switch (props.getProperty("appium.remote.platform.name")){
            case Const.Android -> {
                LOGGER.info("Platform Android selected.");
            }
        }
    }
}
