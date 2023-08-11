package touch;

import io.appium.java_client.AppiumDriver;
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
    /**
     * Draws a shape on the screen using the provided list of points.
     *
     * @param driver The AppiumDriver instance.
     * @param points The list of points defining the shape to be drawn.
     *               The points should be in the order of the desired drawing sequence.
     *               The first and last points should be the same to close the shape.
     * @throws IllegalArgumentException if the provided points list is null or empty.
     */
    public void drawShape(AppiumDriver driver, List<Point> points) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence shape = new Sequence(finger, 0);

        for (int s = 0; s < points.size(); s++) {
            Point point = points.get(s);
            Duration duration = Duration.ofMillis(s == 0 ? 0 : 100);
            PointerInput.Origin origin = s == 0 ? PointerInput.Origin.viewport() : PointerInput.Origin.pointer();

            shape.addAction(finger.createPointerMove(duration, origin, point.getX(), point.getY()));

            if (s == 0) shape.addAction(finger.createPointerDown(MouseButton.LEFT.asArg()));
        }
        shape.addAction(finger.createPointerUp(MouseButton.LEFT.asArg()));
        driver.perform(List.of(shape));
    }
}
