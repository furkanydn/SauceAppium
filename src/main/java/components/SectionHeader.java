package components;

import org.openqa.selenium.WebElement;
import pages.BasePage;

public class SectionHeader extends BasePage {
    /**
     * Returns the text of the section header in the current view.
     *
     * @return a String representing the section header text
     */
    public  WebElement SectionHeader(String value){
        return findElement(value + "header");
    }
}
