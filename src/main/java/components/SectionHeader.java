package components;

import pages.BasePage;

public class SectionHeader extends BasePage {
    /**
     Returns the text of the section header in the current view.
     @return a String representing the section header text
     */
    public String GetSectionHeader(){
        return findElement("container header").getText();
    }
}
