package com.appium.manager;

import com.appium.utils.TestUtilities;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class VideoManager {
    TestUtilities testUtilities = new TestUtilities();

    /**
     * Start asynchronous screen recording process with default options.
     *
     */
    public void startRecord(){
        try {
            ((CanRecordScreen) new DriverManager().getDriverThreadLocal()).startRecordingScreen();
        } catch (Exception e){
            testUtilities.logger().fatal("Recording could not be started. Try again.");
            e.printStackTrace();
        }
    }

    /**
     * Gather the output from the previously started screen recording to a media file
     * with default options.
     * Created Base-64 encoded content of the recorded media file.
     */
    public void stopRecord(String scenario) throws IOException, SecurityException{
        GlobalParameters globalParameters=new GlobalParameters();
        String media = ((CanRecordScreen) new DriverManager().getDriverThreadLocal()).stopRecordingScreen();
        String path = System.getProperty("user.dir") +
                File.separator + "src" +
                File.separator + "test" +
                File.separator + "resources" +
                File.separator + "videos" +
                globalParameters.getPlatformName() + "-" + globalParameters.getDeviceName() + LocalDate.now();
        File videoDirectory = new File(path);

        synchronized (videoDirectory){
            if (!videoDirectory.exists()) videoDirectory.mkdirs();
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
