package components;

import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import pages.BasePage;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class AppBar extends BasePage {
    /**
     * Clicks on the "Open Menu" button to reveal the menu options.
     */
    public void openMenu() {
        findElement("open menu").click();
    }

    public void closeAndroidMenu(){
        horizontalScroll("android:id/content");
    }

    public void sortButton(SortComp sortComp){
        findElement("sort button").click();
        //findElement(getSortingName(sortComp)).click();
    }

    public void cartBadge(){
        findElement("cart badge").click();
    }

    public enum SortComp {
        NAME_ASC, NAME_DESC, PRICE_ASC, PRICE_DESC
    }

    public static String getSortingName(SortComp sortComp) {
        switch (sortComp) {
            case NAME_ASC -> {
                return "nameAsc";
            }
            case NAME_DESC -> {
                return "nameDesc";
            }
            case PRICE_ASC -> {
                return "priceAsc";
            }
            case PRICE_DESC -> {
                return "priceDesc";
            }
            default -> throw new IllegalArgumentException("Invalid sorting name " + sortComp);
        }
    }

    public void horizontalScroll(String contentId){
        int centerY = findElementId(contentId).getRect().y + (findElementId(contentId).getSize().height/2);
        double startX = findElementId(contentId).getRect().x + (findElementId(contentId).getSize().width * 0.8);
        double endX = findElementId(contentId).getRect().x + (findElementId(contentId).getSize().width * 0.2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH,"finger");
        Sequence swipe = new Sequence(finger,1);
        swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), (int) startX,centerY));
        swipe.addAction(finger.createPointerDown(0));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), (int) endX,centerY));
        swipe.addAction(finger.createPointerUp(0));

        if (Objects.equals(getProp(), "Android")) androidDriver.perform(List.of(swipe));
        else iosDriver.perform(List.of(swipe));
    }
}
