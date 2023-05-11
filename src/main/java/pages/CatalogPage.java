package pages;

import utils.Linker;
import utils.Linker.Links;

public class CatalogPage extends BasePage {
    /**
     This method navigates to the drawing page in the application using a deep link.
     */
    public void goDrawingPageWithDeepLink(){
        Linker.Go(Links.Drawing);
    }
}
