package com.appium.sauceappium.stepDefinitions;

import com.appium.sauceappium.manager.DriverManager;
import com.appium.sauceappium.manager.GlobalParameters;
import com.appium.sauceappium.manager.VideoManager;
import com.appium.sauceappium.utils.TestUtilities;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;

import java.io.IOException;

/**
 * Hooks run before and after every Cucumber scenario
 * */
public class Hooks {
    TestUtilities testUtilities = new TestUtilities();
    GlobalParameters parameters = new GlobalParameters();

    @Before
    public void initializeHook(){
//        try {
//            new VideoManager().startRecord(parameters.getPlatformName());
//        } catch (Exception exception){
//            testUtilities.logger().fatal("Launch initialize VideoManager hook failed" + exception);
//        }
    }

    /**
     * Attach data to the report(s) and stop recording
     *
     * <pre>
     * {@code
     * // Attach a screenshot. See your UI automation tool's docs for details about how to take a screenshot.
     * scenario.attach(pngBytes, "image/png", "Bytes");
     * }
     * </pre>
     * <p>
     * To ensure reporting tools can understand what the data is a
     * {@code mediaType} must be provided. For example: {@code text/plain},
     * {@code image/png}, {@code text/html;charset=utf-8}.
     * <p>
     *
     * @param scenario  The current scenario
     */
    @After
    public void quitHook(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            byte[] screenshot = new DriverManager().getDriverLocal().getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png", scenario.getName());
        }
        new VideoManager().stopRecord(scenario.getName());
    }
}
