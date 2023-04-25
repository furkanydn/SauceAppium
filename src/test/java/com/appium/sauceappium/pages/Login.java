package com.appium.sauceappium.pages;

import com.appium.sauceappium.manager.BaseDriver;

public class Login extends BaseDriver {
    MenuTest menuTest = new MenuTest();
    public Login enterUserName(String username) {
        //menuTest.setOpenMenu();
        return this;
    }
}
