package com.appium.sauceappium.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class TestUtilities {
    public static final long WAITTIME = 10;

    public Logger logger(){
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }

    public String dateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static Path resourcePathToLocalPath(String rPath){
        URL url = ClassLoader.getSystemResource(rPath);
        Objects.requireNonNull(url, String.format("Cannot find the '%s' resource", rPath));
        return Paths.get(url.getPath());

    }

    public static Path androidApk(){
        return resourcePathToLocalPath("app/Android-MyDemoAppRN.1.3.0.build-244.apk");
    }

    public static Path iosApp(){
        return resourcePathToLocalPath("app/MyRNDemoApp.app");
    }
}
