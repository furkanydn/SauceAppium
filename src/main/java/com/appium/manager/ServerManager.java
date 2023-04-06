package com.appium.manager;

import com.appium.utils.TestUtilities;
import com.appium.utils.UUtils;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import javax.management.RuntimeOperationsException;
import java.io.File;
import java.util.HashMap;

public class ServerManager {
    private static ThreadLocal<AppiumDriverLocalService> serviceThreadLocal = new ThreadLocal<>();
    TestUtilities testUtilities = new TestUtilities();

    public AppiumDriverLocalService getAppiumService(){
        return serviceThreadLocal.get();
    }

    /**
     * Starts the defined appium server.
     *
     * @throws AppiumServerHasNotBeenStartedLocallyException If an error occurs while spawning the child process.
     * @implNote If you are using macOS use the getAppiumServiceMacOS method or if you are using Windows use the getAppiumServiceWindows method.
     */
    public void startServiceLocal(){
        testUtilities.logger().info("Appium REST Http interface listener started");

        AppiumDriverLocalService localService = getAppiumServiceMacOS();
        try {
            localService.start();
        } catch (AppiumServerHasNotBeenStartedLocallyException e) {
            testUtilities.logger().fatal(new AppiumServerHasNotBeenStartedLocallyException("The local appium server has not been started."));
        }
        localService.clearOutPutStreams();
        serviceThreadLocal.set(localService);
        testUtilities.logger().info("The local appium server has been started.");
    }

    /**
     * It collects the environments required in the macOS operating system.
     * @return AppiumDriverLocalService
     * */
    public AppiumDriverLocalService getAppiumServiceMacOS() {
        GlobalParameters globalParameters = new GlobalParameters();
        HashMap<String,String> environment = new HashMap<String,String>();
        environment.put(
                "PATH", "/Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/bin:/Users"
                + UUtils.getName() + "/Volumes/Helpintosh/Library/Android/sdk/tools:/Users/"
                + UUtils.getName() + "/Volumes/Helpintosh/Library/Android/sdk/platform-tools:/opt/homebrew/bin:/opt/homebrew/sbin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Library/Apple/usr/bin"
                + System.getenv("PATH"));
        environment.put("ANDROID_HOME","/Volumes/Helpintosh/Library/Android/sdk");

        return AppiumDriverLocalService.buildService(
                new AppiumServiceBuilder().usingDriverExecutable(
                        new File("/Users/" + UUtils.getName() + "/.nvm/versions/node/v19.8.1/bin/node"))
                        .withAppiumJS(
                                new File("/Users/" + UUtils.getName() + "/.nvm/versions/node/v19.8.1/lib/node_modules/appium/build/lib/main.js"))
                        .usingAnyFreePort().withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                        .withEnvironment(environment)
                        .withLogFile(
                                new File(globalParameters.getPlatformName() + "_" + globalParameters.getDeviceName() + File.separator + "Service.log")));
    }

    /**
     * It collects the environments required in the Windows operating system.
     * @return AppiumDriverLocalService
     * */
    public AppiumDriverLocalService getAppiumServiceWindows() {
        GlobalParameters parameters = new GlobalParameters();
        /* Since I don't have a Windows device here, I couldn't advance it, it will work if you give the correct file path of the arguments as in macOS. */
        return AppiumDriverLocalService.buildService(
                new AppiumServiceBuilder()
                        .usingAnyFreePort()
                        .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                        .withLogFile(
                                new File(parameters.getPlatformName() + "_" + parameters.getDeviceName() + File.separator + "Service.log")));
    }
}
