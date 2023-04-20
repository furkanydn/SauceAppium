package com.appium.sauceappium.runners;


import com.appium.sauceappium.manager.BaseDriver;
import com.appium.sauceappium.manager.GlobalParameters;
import com.appium.sauceappium.manager.ServerManager;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class BaseRunner {
    private static final ThreadLocal<TestNGCucumberRunner> testCucumberRunner = new ThreadLocal<>();

    public static TestNGCucumberRunner getCucumberRunner() {
        return testCucumberRunner.get();
    }

    public static void setCucumberRunner(TestNGCucumberRunner cucumberRunner){
        testCucumberRunner.set(cucumberRunner);
    }

    /**
     * It initializes the Appium service and driver by calling the necessary methods inside.
     * @param platformName e.g. iOS
     * @param udid e.g. 1ae203187fc012g
     * @param deviceName e.g. iPhone12
     * @param systemPort e.g. 10000
     * @param driverPort e.g. 11000
     * @param wdaPort e.g. 10001
     * @param webkitProxyPort e.g. 11001
     * */
    @BeforeClass(alwaysRun = true)
    public void setupClass(String platformName, String udid, String deviceName,
                           @Optional("Android") String systemPort, @Optional("Android") String driverPort,
                           @Optional("iOS") String wdaPort, @Optional("iOS") String webkitProxyPort)
            throws Exception {
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
        new BaseDriver().initDriverThread();
        setCucumberRunner(new TestNGCucumberRunner(this.getClass()));
    }

    /**
     * Cucumber puts scenarios in them.
     * @param pickleWrapper Scenario this pickle was wrapped from.
     * @param featureWrapper featureWrapper
     * */
    @Test(groups = "cucumber",description = "Runs Cucumber scenarios",dataProvider = "scenarios")
    public void scenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        getCucumberRunner().runScenario(pickleWrapper.getPickle());
    }

    @DataProvider
    public static Object[][] scenarios() {
        return getCucumberRunner().provideScenarios();
    }

    /**
     * Stops this service is it is currently running. This method will attempt to block until the
     * server has been fully shutdown.
     */
    @AfterClass public void tearDown(){
        BaseDriver.afterClass();
        getCucumberRunner().finish();
    }
}