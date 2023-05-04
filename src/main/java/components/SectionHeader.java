package components;

import pages.BasePage;

public class SectionHeader extends BasePage {
    public String GetSectionHeader(){
        return findElement("container header").getText();
    }
}
