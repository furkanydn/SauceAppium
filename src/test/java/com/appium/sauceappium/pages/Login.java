package com.appium.sauceappium.pages;

public class Login extends Base {
    Menu menu = new Menu();
    public Login enterUserName(String username) {
        menu.setOpenMenu();
        return this;
    }
}
