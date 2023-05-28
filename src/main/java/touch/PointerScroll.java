package touch;

import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import pages.BasePage;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class PointerScroll extends BasePage {
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

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH,"finger");
        Sequence swipe = new Sequence(finger,1);
        swipe.addAction(
                finger.createPointerMove(Duration.ofSeconds(0)
                        ,PointerInput.Origin.viewport()
                        ,(int) startX
                        ,centerY));
        swipe.addAction(finger.createPointerDown(0));
        swipe.addAction(
                finger.createPointerMove(Duration.ofMillis(700)
                        ,PointerInput.Origin.viewport()
                        ,(int) endX,
                        centerY));
        swipe.addAction(finger.createPointerUp(0));

        if (Objects.equals(getPlatform(), "Android")) androidDriver.perform(List.of(swipe));
        else iosDriver.perform(List.of(swipe));
    }
    /**
     * Scrolls vertically on the given content element from the start height factor to the end height factor.
     *
     * @param contentId The ID of the content element to scroll.
     * @param startHeightFactor The factor to calculate the start y-coordinate of the scroll gesture. Must be between 0 and 1.
     * @param endHeightFactor The factor to calculate the end y-coordinate of the scroll gesture. Must be between 0 and 1.
     * @throws IllegalArgumentException If the startHeightFactor or endHeightFactor is not between 0 and 1.
     */
    public void VerticalScroll(String contentId, double startHeightFactor, double endHeightFactor){
        if(startHeightFactor < 0 || startHeightFactor > 1 || endHeightFactor < 0 || endHeightFactor > 1)
            throw new IllegalArgumentException("startHeightFactor or endHeightFactor must be between 0 and 1.");
        int centerX = findElementId(contentId).getRect().x
                + (findElementId(contentId).getSize().width / 2);
        double startY = findElementId(contentId).getRect().y
                + (findElementId(contentId).getSize().height / startHeightFactor);
        double endY = findElementId(contentId).getRect().y
                + (findElementId(contentId).getSize().height / endHeightFactor);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH,"finger");
        Sequence swipe = new Sequence(finger,1);
        swipe.addAction(
                finger.createPointerMove(Duration.ofSeconds(0)
                        ,PointerInput.Origin.viewport(),
                        centerX,
                        (int) startY));
        swipe.addAction(finger.createPointerDown(0));
        swipe.addAction(
                finger.createPointerMove(Duration.ofMillis(700)
                        ,PointerInput.Origin.viewport()
                        ,centerX
                        ,(int) endY));
        swipe.addAction(finger.createPointerUp(0));

        if (Objects.equals(getPlatform(), "Android")) androidDriver.perform(List.of(swipe));
        else iosDriver.perform(List.of(swipe));
    }
}
