package com.appium.sauceappium.pages;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;

//@Execution(ExecutionMode.CONCURRENT) Running tests in parallel
public class MenuTest extends BaseDriver {

    @Test
    public void setOpenMenu(){
        androidDriver.findElement(AppiumBy.accessibilityId("open menu")).click();
    }
    @Test public void menuItemCatalog(){
        androidDriver.findElement(AppiumBy.accessibilityId("menu item catalog")).click();
    }
    @Test public void menuItemWebview(){
        androidDriver.findElement(AppiumBy.accessibilityId("menu item webview")).click();
    }

    @Test public void menuItemQrCodeScanner(){
        androidDriver.findElement(AppiumBy.accessibilityId("menu item qr code scanner")).click();
    }
    @Test public void menuItemGeoLocation(){
        androidDriver.findElement(AppiumBy.accessibilityId("menu item geo location")).click();
    }
    @Test public void menuItemDrawing(){
        androidDriver.findElement(AppiumBy.accessibilityId("menu item drawing")).click();
    }
    @Test public void menuItemAbout(){
        androidDriver.findElement(AppiumBy.accessibilityId("menu item about")).click();
    }
    @Test public void menuItemResetApp(){
        androidDriver.findElement(AppiumBy.accessibilityId("menu item reset app")).click();
    }
    @Test public void menuItemBiometrics(){
        androidDriver.findElement(AppiumBy.accessibilityId("menu item biometrics")).click();
    }
    @Test public void menuItemLogin(){
        androidDriver.findElement(AppiumBy.accessibilityId("menu item log in")).click();
    }
    @Test public void menuItemLogout(){
        androidDriver.findElement(AppiumBy.accessibilityId("menu item log out")).click();
    }
    @Test public void menuItemApiCalls(){
        androidDriver.findElement(AppiumBy.accessibilityId("menu item api calls")).click();
    }
    @Test public void menuItemSauceBot(){
        androidDriver.findElement(AppiumBy.accessibilityId("menu item sauce bot video")).click();
    }
}
