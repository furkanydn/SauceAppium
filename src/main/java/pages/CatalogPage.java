package pages;

import components.AppBar;
import utils.Config;
import utils.Linker;
import utils.Linker.Links;

import java.util.Arrays;
import java.util.Objects;

public class CatalogPage extends BasePage {
    AppBar Bar = new AppBar();
    /**
     This method navigates to the drawing page in the application using a deep link.
     */
    public void goDrawingPageWithDeepLink(){
        Linker.Go(Links.Drawing);
    }
    /**
     Returns the header text of the current page.
     @return String the header text of the current page.
     */
    public String getHeader() {
        try {
            Thread.sleep(3000);
            String headerText;
            if ((Objects.equals(Config.getProperties("appium.remote.platform.name"), "Android")))
                headerText = findElementX("Products").getText();
            else
                headerText = findElement("container header").getAttribute("label");
            return headerText;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     Adds the "Backpack" and "Bike Light" products to the cart by clicking on their respective "Add To Cart" buttons on the "Products" page.*/
    public void backpackAndBikeLightToCart(){
        Linker.Go(Links.Products);
        for (String productName : Arrays.asList("Backpack", "Bike Light")) {
            findElement("Sauce Labs " + productName).click();
            findElement("Add To Cart button").click();
            Bar.navigationBack();
        }
        Bar.barOptionCart();
    }

    /**
     Checks whether the Backpack and Bike Light products are added to the cart.
     @return true if both products are added to the cart, false otherwise.
     */
    public boolean isBackpackAndBikeLightAddedToCart(){
        Linker.Go("cart/id=1&amount=1&color=black,id=2&amount=1&color=black");
        boolean isBackpackInCart = findElementX("Sauce Labs Backpack").isDisplayed();
        boolean isBikeLightInCart = findElementX("Sauce Labs Bike Light").isDisplayed();
        return isBackpackInCart && isBikeLightInCart;
    }
}
