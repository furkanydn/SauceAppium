package com.appium.sauceappium.manager;

import com.appium.sauceappium.utils.TestUtilities;

public class CapsManage {
    public String setIOSApp() {
        return TestUtilities.iosApp().toAbsolutePath().toString();
    }

    public String setAndroidApp(){
        return TestUtilities.androidApk().toAbsolutePath().toString();
    }
}

