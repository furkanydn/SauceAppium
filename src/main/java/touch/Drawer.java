package touch;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.MouseButton;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Arrays;


public class Drawer {
    private Point getPointOnCircle (int step, int totalSteps, Point origin, double radius){
        double theta = 2 * Math.PI * ((double)step / totalSteps);
        int x = (int)Math.floor(Math.cos(theta) * radius);
        int y = (int)Math.floor(Math.sin(theta) * radius);
        return new Point(origin.x + x, origin.y + y);
    }

    public void drawCircle (AppiumDriver driver, Point origin, double radius, int steps) {
        Point firstPoint = getPointOnCircle(0, steps, origin, radius);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence circle = new Sequence(finger, 0);
        circle.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), firstPoint.x, firstPoint.y));
        circle.addAction(finger.createPointerDown(MouseButton.LEFT.asArg()));

        for (int i = 1; i < steps + 1; i++) {
            Point point = getPointOnCircle(i, steps, origin, radius);
            circle.addAction(finger.createPointerMove(Duration.ofMillis(20), PointerInput.Origin.viewport(), point.x, point.y));
        }

        circle.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(circle));
    }
}
