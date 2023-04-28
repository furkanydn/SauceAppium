package components;

import pages.BasePage;

import java.io.IOException;

public class MenuItem extends BasePage {
    public void Catalog() throws IOException {
        findElement("menu item catalog").click();
    }
}
