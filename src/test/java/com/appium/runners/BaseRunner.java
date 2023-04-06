package com.appium.runners;


import com.appium.manager.DriverManager;
import com.appium.manager.GlobalParameters;
import com.appium.manager.ServerManager;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.sql.Driver;

public class BaseRunner {
    private static final ThreadLocal<TestNGCucumberRunner> testCucumberRunner = new ThreadLocal<>();

    public static TestNGCucumberRunner getCucumberRunner() {
        return testCucumberRunner.get();
    }

    public static void setCucumberRunner(TestNGCucumberRunner cucumberRunner){
        testCucumberRunner.set(cucumberRunner);
    }

    public void setupClass(String platformName, String udid, String deviceName,
                           @Optional("Android") String systemPort, @Optional("Android") String driverPort,
                           @Optional("iOS") String wdaPort, @Optional("iOS") String webkitProxyPort)
            throws Exception{
        ThreadContext.put("ROUTINGKEY", platformName + "_" + deviceName);
        GlobalParameters globalParameters = new GlobalParameters();
        globalParameters.setPlatformName(platformName);
        globalParameters.setUdid(udid);
        globalParameters.setDeviceName(deviceName);

        switch (platformName) {
            case "iOS" -> {
                globalParameters.setWdaLocalPort(wdaPort);
                globalParameters.setWebKitProxyPort(webkitProxyPort);
            }
            case "Android" -> {
                globalParameters.setSystemPort(systemPort);
                globalParameters.setDriverPort(driverPort);
            }
            default -> throw new IllegalStateException("Unexpected value: " + platformName);
        }
        new ServerManager().startServiceLocal();
        new DriverManager().initDriverThread();
    }
}
