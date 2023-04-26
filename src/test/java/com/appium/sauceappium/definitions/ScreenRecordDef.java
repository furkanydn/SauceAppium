package com.appium.sauceappium.definitions;

import com.appium.sauceappium.pages.BaseDriver;
import com.appium.sauceappium.manager.PropertyManager;
import com.appium.sauceappium.utils.Constant;
import com.appium.sauceappium.utils.GlobalConfig;
import com.appium.sauceappium.utils.TestUtilities;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import static com.appium.sauceappium.utils.GlobalConfig.PLATFORM_NAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Hooks run before and after every Cucumber scenario
 * */
public class ScreenRecordDef extends BaseDriver {
    TestUtilities testUtilities = new TestUtilities();
    static Properties properties;
    {
        try {
            properties = new PropertyManager().getProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Start asynchronous screen recording process.
     */
    @Test
    public void basicScreenRecordingWorks() throws InterruptedException {
        switch (PLATFORM_NAME) {
            case Constant.IOS -> {
                try {
                    iosDriver.startRecordingScreen(
                            new IOSStartScreenRecordingOptions()
                                    .withTimeLimit(Duration.ofSeconds(10)));
                    Thread.sleep(5000);
                    String result = iosDriver.stopRecordingScreen();
                    assertThat(result, is(not(emptyString())));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            case Constant.ANDROID -> {
                try {
                    androidDriver.startRecordingScreen(
                            new AndroidStartScreenRecordingOptions()
                                    .withTimeLimit(Duration.ofSeconds(10)));
                } catch (Exception e) {
                    testUtilities.logger().fatal(Constant.SCREEN_RECORDING_ONLY_WORKS_ON_REAL_DEVICES);
                    e.printStackTrace();
                }
            }
        }
    }
}
