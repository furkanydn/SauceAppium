package touch;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import pages.BasePage;
import utils.Config;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class PointerScroll extends BasePage {
    private final static PointerInput FINGER = new PointerInput(PointerInput.Kind.TOUCH,"finger");
    /**
     * Scrolls horizontally on the given content element from the start width factor to the end width factor.
     *
     * @param contentId The ID of the content element to scroll.
     * @param startWidthFactor the starting horizontal position as a factor of the content element's width, Must be between 0 and 1
     * @param endWidthFactor the ending horizontal position as a factor of the content element's width, Must be between 0 and 1
     * @throws IllegalArgumentException if startWidthFactor or endWidthFactor is not between 0 and 1
     */
    public void HorizontalScroll(String contentId, double startWidthFactor, double endWidthFactor){
        if(startWidthFactor < 0 || startWidthFactor > 1 || endWidthFactor < 0 || endWidthFactor > 1)
            throw new IllegalArgumentException("startWidthFactor or endWidthFactor must be between 0 and 1.");
        int centerY = findElementId(contentId).getRect().y
                + (findElementId(contentId).getSize().height/2);
        double startX = findElementId(contentId).getRect().x
                + (findElementId(contentId).getSize().width * 0.8);
        double endX = findElementId(contentId).getRect().x
                + (findElementId(contentId).getSize().width * 0.2);

        Sequence swipe = new Sequence(FINGER,1);
        swipe.addAction(
                FINGER.createPointerMove(Duration.ofSeconds(0)
                        ,PointerInput.Origin.viewport()
                        ,(int) startX
                        ,centerY));
        swipe.addAction(FINGER.createPointerDown(0));
        swipe.addAction(
                FINGER.createPointerMove(Duration.ofMillis(700)
                        ,PointerInput.Origin.viewport()
                        ,(int) endX,
                        centerY));
        swipe.addAction(FINGER.createPointerUp(0));

        if (Objects.equals(getPlatform(), "Android")) androidDriver.perform(List.of(swipe));
        else iosDriver.perform(List.of(swipe));
    }
    /**
     * Swipe the specified element in the given direction.
     *
     * @param direction the direction of the swipe
     * @param locator   the accessibility id of the element to swipe
     */
    public void swipeAction(SwipeDirection direction, String locator) {
        WebElement element = findElementAccessibilityId(locator);
        Sequence swiper = new Sequence(FINGER, 1);

        int startX = element.getRect().x + (element.getSize().width / 4);
        int startY = element.getRect().y + (element.getSize().height / 2);
        int endX, endY;

        if (direction == SwipeDirection.SWIPE_DOWN || direction == SwipeDirection.SWIPE_UP) {
            startY = direction == SwipeDirection.SWIPE_DOWN ? element.getRect().y + (element.getSize().height / 4) : element.getRect().y + (element.getSize().height * 3 / 4);
            endX = element.getRect().x + (element.getSize().width / 2);
            endY = direction == SwipeDirection.SWIPE_DOWN ? element.getRect().y + (element.getSize().height * 3 / 4) : element.getRect().y + (element.getSize().height / 4);
        } else {
            endX = direction == SwipeDirection.SWIPE_RIGHT ? element.getRect().x + (element.getSize().width * 3 / 4) : element.getRect().x + (element.getSize().width / 4);
            endY = element.getRect().y + (element.getSize().height / 2);
        }

        swiper.addAction(FINGER.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), startX, startY));
        swiper.addAction(FINGER.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swiper.addAction(FINGER.createPointerMove(Duration.ofMillis(750), PointerInput.Origin.viewport(), endX, endY));
        swiper.addAction(FINGER.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        (Config.platformName == Config.Platform.ANDROID ? androidDriver : iosDriver).perform(List.of(swiper));
    }

    public enum SwipeDirection {
        SWIPE_RIGHT,
        SWIPE_LEFT,
        SWIPE_DOWN,
        SWIPE_UP
    }
}
