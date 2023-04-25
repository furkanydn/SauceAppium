package com.appium.sauceappium.definitions;

import com.appium.sauceappium.manager.BaseDriver;
import com.appium.sauceappium.manager.GlobalParameters;
import com.appium.sauceappium.manager.PropertyManager;
import com.appium.sauceappium.manager.VideoManager;
import com.appium.sauceappium.utils.Constant;
import com.appium.sauceappium.utils.TestUtilities;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions;
import io.cucumber.java.Before;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import static com.appium.sauceappium.utils.Config.*;

/**
 * Hooks run before and after every Cucumber scenario
 * */
public class Hooks extends BaseDriver {
    TestUtilities testUtilities = new TestUtilities();
    GlobalParameters parameters = new GlobalParameters();
    static Properties properties;
    {
        try {
            properties = new PropertyManager().getProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Before
    public void initializeHook(){
        try {
            new VideoManager().startRecord(parameters.getPlatformName());
        } catch (Exception exception){
            testUtilities.logger().fatal("Launch initialize VideoManager hook failed" + exception);
        }
    }

    /**
     * Attach data to the report(s) and stop recording
     *
     * <p>
     * To ensure reporting tools can understand what the data is a
     * {@code mediaType} must be provided. For example: {@code text/plain},
     * {@code image/png}, {@code text/html;charset=utf-8}.<p>
     */
    @Test
    public void basicScreenRecordingWorks() throws InterruptedException {
        switch (properties.getProperty(PLATFORM_NAME)) {
            case Constant.IOS -> {
                try {
                    iosDriver.startRecordingScreen(
                            new IOSStartScreenRecordingOptions()
                                    .withTimeLimit(Duration.ofSeconds(10)));
                    Thread.sleep(5000);
                    String result = iosDriver.stopRecordingScreen();
                    //add assert not empty!
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            case Constant.ANDROID -> {
                androidDriver.startRecordingScreen(
                        new AndroidStartScreenRecordingOptions()
                                .withTimeLimit(Duration.ofSeconds(5)));

            }
        }
    }
}
