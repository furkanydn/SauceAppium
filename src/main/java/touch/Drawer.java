package touch;

import com.beust.ah.A;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.touch.ActionOptions;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.MouseButton;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;


public class Drawer {
    /**
     * Calculates the coordinates of a point on a circle based on the step and total steps.
     *
     * @param step       The current step in the circle drawing process.
     * @param totalSteps The total number of steps to complete the circle.
     * @param origin     The origin point of the circle.
     * @param radius     The radius of the circle.
     * @return The coordinates of the point on the circle.
     */
    private Point getPointOnCircle (int step, int totalSteps, Point origin, double radius){
        double theta = 2 * Math.PI * ((double)step / totalSteps);
        int x = (int)Math.floor(Math.cos(theta) * radius);
        int y = (int)Math.floor(Math.sin(theta) * radius);
        return new Point(origin.x + x, origin.y + y);
    }
    /**
     * Draws a circle on the screen using the provided parameters.
     *
     * @param driver  The Appium driver instance.
     * @param origin  The origin point of the circle.
     * @param radius  The radius of the circle.
     * @param steps   The number of steps to complete the circle.
     */
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
        driver.perform(List.of(circle));
    }

    public void drawPoint(AppiumDriver driver,Point startPoint, int canvasWidth, int canvasHeight){
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence shape = new Sequence(finger, 0);

        shape.addAction(finger.createPointerMove(Duration.ofMillis(0),PointerInput.Origin.viewport(), startPoint.getX(), startPoint.getY()));
        shape.addAction(finger.createPointerDown(MouseButton.LEFT.asArg()));

        int offsetX = canvasWidth / 4;
        int offsetY = canvasHeight / 4;
        for (int i = 1; i <= 4; i++) {
            int x = startPoint.getX() + i * offsetX;
            int y = startPoint.getY() + i * offsetY;
            shape.addAction(finger.createPointerMove(Duration.ofMillis(20), PointerInput.Origin.viewport(), x, y));
        }
        shape.addAction(finger.createPointerUp(MouseButton.LEFT.asArg()));
        driver.perform(List.of(shape));
    }
}
