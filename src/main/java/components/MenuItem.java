package components;

import pages.BasePage;

import java.io.IOException;

public class MenuItem extends BasePage {

    public void OpenMenu() throws IOException {
        findElement("open menu").click();
    }

    public void Catalog() throws IOException {
        if (findElement("open menu").isDisplayed()){
            OpenMenu();
            findElement("menu item catalog").click();
        }
    }
}
