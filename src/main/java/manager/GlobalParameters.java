package manager;

public class GlobalParameters {
    private static String platformName = String.valueOf(new ThreadLocal<String>());
    private static String udid = String.valueOf(new ThreadLocal<String>());
    private static String deviceName = String.valueOf(new ThreadLocal<String>());

    public static String getPlatformName() {
        return platformName;
    }

    public static void setPlatformName(String platformName) {
        GlobalParameters.platformName = platformName;
    }

    public static String getUdid() {
        return udid;
    }

    public static void setUdid(String udid) {
        GlobalParameters.udid = udid;
    }

    public static String getDeviceName() {
        return deviceName;
    }

    public static void setDeviceName(String deviceName) {
        GlobalParameters.deviceName = deviceName;
    }

    //region iOS
    private static String wdaLocalPort = String.valueOf(new ThreadLocal<String>());
    private static String webKitProxyPort = String.valueOf(new ThreadLocal<String>());

    public static String getWdaLocalPort() {
        return wdaLocalPort;
    }

    public static void setWdaLocalPort(String wdaLocalPort) {
        GlobalParameters.wdaLocalPort = wdaLocalPort;
    }

    public static String getWebKitProxyPort() {
        return webKitProxyPort;
    }

    public static void setWebKitProxyPort(String webKitProxyPort) {
        GlobalParameters.webKitProxyPort = webKitProxyPort;
    }
    //endregion

    //region Android
    private static String systemPort = String.valueOf(new ThreadLocal<String>());
    private static String driverPort = String.valueOf(new ThreadLocal<String>());

    public static String getSystemPort() {
        return systemPort;
    }

    public static void setSystemPort(String systemPort) {
        GlobalParameters.systemPort = systemPort;
    }

    public static String getDriverPort() {
        return driverPort;
    }

    public static void setDriverPort(String driverPort) {
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