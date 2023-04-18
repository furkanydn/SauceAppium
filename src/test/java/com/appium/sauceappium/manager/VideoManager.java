package com.appium.sauceappium.manager;

import com.appium.sauceappium.utils.TestUtilities;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;

public class VideoManager {
    TestUtilities testUtilities = new TestUtilities();

    /**
     * Start asynchronous screen recording process with default options.
     * Screen recording only works on real devices!
     * @param platformName Identifies the operating system of the endpoint node.
     *
     */
    public void startRecord(String platformName) throws InterruptedException{
        switch (platformName){
            case "iOS" -> {
                try {
                    ((CanRecordScreen) new DriverManager().getDriverLocal())
                            .startRecordingScreen(
                                    new IOSStartScreenRecordingOptions()
                                            .withTimeLimit(Duration.ofMinutes(TestUtilities.WAITTIME)));
                } catch (WebDriverException e){
                    testUtilities.logger().fatal("Recording could not be started. Try again.");
                    e.printStackTrace();
                }
            }
            case "Android" -> {
                try {
                    ((CanRecordScreen) new DriverManager().getDriverLocal())
                            .startRecordingScreen(
                                    new AndroidStartScreenRecordingOptions()
                                            .withTimeLimit(Duration.ofMinutes(TestUtilities.WAITTIME)));
                } catch (WebDriverException e){
                    testUtilities.logger().fatal("Recording could not be started. Try again.");
                    e.printStackTrace();
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + platformName);
        }

    }

    /**
     * Gather the output from the previously started screen recording to a media file
     * with default options.
     * Created Base-64 encoded content of the recorded media file.
     */
    public void stopRecord(String scenario) throws IOException, SecurityException{
        GlobalParameters globalParameters=new GlobalParameters();
        String media = ((CanRecordScreen) new DriverManager().getDriverLocal()).stopRecordingScreen();
        String path = System.getProperty("user.dir") +
                File.separator + "src" +
                File.separator + "" +
                File.separator + "resources" +
                File.separator + "videos" +
                globalParameters.getPlatformName() + "-" + globalParameters.getDeviceName() + LocalDate.now();
        File videoDirectory = new File(path);

        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (videoDirectory){
            if (!videoDirectory.exists()) //noinspection ResultOfMethodCallIgnored
                videoDirectory.mkdirs();
        }

        FileOutputStream outputStream = null;
        try {
            var dirName = videoDirectory + File.separator + scenario + ".mp4";
            outputStream = new FileOutputStream(dirName);
            outputStream.write(Base64.decodeBase64(media));
            outputStream.close();
            testUtilities.logger().info("Video filePath: " + dirName);
        } catch (Exception e){
            testUtilities.logger().fatal("Error 0xA00F425D video capture start failed" + e);
        } finally {
            if (outputStream != null) outputStream.close();
        }
    }
}
