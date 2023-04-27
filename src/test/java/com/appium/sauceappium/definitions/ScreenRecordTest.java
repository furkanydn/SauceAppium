package com.appium.sauceappium.definitions;

import com.appium.sauceappium.pages.BaseDriver;
import com.appium.sauceappium.utils.Constant;
import com.appium.sauceappium.utils.TestUtilities;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.appium.sauceappium.utils.GlobalConfig.PLATFORM_NAME;

/**
 * Hooks run before and after every Cucumber scenario
 * */
public class ScreenRecordTest extends BaseDriver {
    TestUtilities testUtilities = new TestUtilities();

    /**
     * Start asynchronous screen recording process.
     */
    @Test
    public void basicScreenRecordingWorks() {
        switch (PLATFORM_NAME) {
            case Constant.IOS -> {
                try {
                    iosDriver.startRecordingScreen(
                            new IOSStartScreenRecordingOptions()
                                    .withTimeLimit(Duration.ofSeconds(10)));
                    Thread.sleep(5000);
                    String result = iosDriver.stopRecordingScreen();
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
