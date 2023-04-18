package com.appium.sauceappium.manager;

public class GlobalParameters {
    private static String platformName = String.valueOf(new ThreadLocal<String>());
    private static String udid = String.valueOf(new ThreadLocal<String>());
    private static String deviceName = String.valueOf(new ThreadLocal<String>());

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        GlobalParameters.platformName = platformName;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        GlobalParameters.udid = udid;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        GlobalParameters.deviceName = deviceName;
    }

    //region iOS
    private static String wdaLocalPort = String.valueOf(new ThreadLocal<String>());
    private static String webKitProxyPort = String.valueOf(new ThreadLocal<String>());

    public String getWdaLocalPort() {
        return wdaLocalPort;
    }

    public void setWdaLocalPort(String wdaPort) {
        wdaLocalPort = wdaPort;
    }

    public String getWebKitProxyPort() {
        return webKitProxyPort;
    }

    public void setWebKitProxyPort(String webKitPort) {
        webKitProxyPort = webKitPort;
    }
    //endregion

    //region Android
    private static String systemPort = String.valueOf(new ThreadLocal<String>());
    private static String driverPort = String.valueOf(new ThreadLocal<String>());

    public String getSystemPort() {
        return systemPort;
    }

    public void setSystemPort(String systemPort) {
        GlobalParameters.systemPort = systemPort;
    }

    public String getDriverPort() {
        return driverPort;
    }

    public void setDriverPort(String driverPort) {
        GlobalParameters.driverPort = driverPort;
    }
    //endregion

    public void initGlobalParameterRealDevice(){
        setPlatformName(System.getProperty("platformName","iOS"));
        setUdid(System.getProperty("udid","7021E632-B569-4371-A010-97EDA4564DAD"));
        setDeviceName(System.getProperty("deviceName","iPhone_12"));
        switch (getPlatformName()) {
            case "iOS" -> {
                setWdaLocalPort(System.getProperty("wdaLocalPort", "10001"));
                setWebKitProxyPort(System.getProperty("webkitDebugProxyPort", "11001"));
            }
            case "Android" -> {
                setSystemPort(System.getProperty("systemPort", "10000"));
                setDriverPort(System.getProperty("driverPort", "11000"));
            }
            default -> throw new IllegalArgumentException("Invalid platform");
        }
    }
    public void initGlobalParameterEmulator(){
        setPlatformName(System.getProperty("platformName","Android"));
        setUdid(System.getProperty("udid","emulator-5554"));
        setDeviceName(System.getProperty("deviceName","Pixel"));
        switch (getPlatformName()) {
            case "iOS" -> {
                setWdaLocalPort(System.getProperty("wdaLocalPort", "10001"));
                setWebKitProxyPort(System.getProperty("webkitDebugProxyPort", "11001"));
            }
            case "Android" -> {
                setSystemPort(System.getProperty("systemPort", "10000"));
                setDriverPort(System.getProperty("driverPort", "11000"));
            }
            default -> throw new IllegalArgumentException("Invalid platform");
        }
    }
}