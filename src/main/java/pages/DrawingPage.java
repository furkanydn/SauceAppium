package pages;

import components.MenuItem;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import touch.Drawer;
import utils.Config;
import utils.Linker;

public class DrawingPage extends BasePage {

    public void goDrawingPageWithClicks(){
        new MenuItem().Drawing();
    }

    /**
     This method navigates to the drawing page in the application using a deep link.
     */
    public void goDrawingPageWithDeepLink(){
        Linker.Go(Linker.Links.Drawing);
    }
    /**
     * Draws a face on the screen using the specified dimensions and platform.
     * The face consists of a head, two eyes, and a mouth.
     */
    public void drawFace() {
        Point head = new Point(getDimension(Dimensions.WIDTH) / 2, getDimension(Dimensions.HEIGHT) / 2);
        Point leftEye = head.moveBy(-getDimension(Dimensions.WIDTH) / 10, -getDimension(Dimensions.HEIGHT) / 10);
        Point rightEye = head.moveBy(getDimension(Dimensions.WIDTH) / 10, -getDimension(Dimensions.HEIGHT) / 10);
        Point mouth = head.moveBy(0, getDimension(Dimensions.HEIGHT) / 10);

        switch (Config.platformName) {
            case ANDROID -> {
                new Drawer().drawCircle(androidDriver, head, (double) getDimension(Dimensions.HEIGHT) / 6, 50);
                new Drawer().drawCircle(androidDriver, leftEye, (double) getDimension(Dimensions.HEIGHT) / 30, 30);
                new Drawer().drawCircle(androidDriver, rightEye, (double) getDimension(Dimensions.HEIGHT) / 30, 30);
                new Drawer().drawCircle(androidDriver, mouth, (double) getDimension(Dimensions.HEIGHT) / 20, 40);
            }
            case IOS -> {
                new Drawer().drawCircle(iosDriver, head, (double) getDimension(Dimensions.HEIGHT) / 6, 50);
                new Drawer().drawCircle(iosDriver, leftEye, (double) getDimension(Dimensions.HEIGHT) / 30, 30);
                new Drawer().drawCircle(iosDriver, rightEye, (double) getDimension(Dimensions.HEIGHT) / 30, 30);
                new Drawer().drawCircle(iosDriver, mouth, (double) getDimension(Dimensions.HEIGHT) / 20, 40);
            }
        }
    }
    /**
     * Represents the dimension type (WIDTH or HEIGHT).
     */
    public enum Dimensions {WIDTH,HEIGHT}
    /**
     * Gets the dimension (width or height) of the screen based on the provided dimension type and platform.
     *
     * @param dimension The dimension type (WIDTH or HEIGHT).
     * @return The value of the specified dimension.
     */
    private int getDimension(Dimensions dimension) {
        Dimension size = switch (Config.platformName) {
            case ANDROID -> androidDriver.manage().window().getSize();
            case IOS -> iosDriver.manage().window().getSize();
        };

        return dimension == Dimensions.WIDTH ? size.getWidth() : size.getHeight();
    }

    public void drawOnCanvas(){
        int canvasWidth = (int) (getDimension(Dimensions.WIDTH) * 0.75);
        int canvasHeight = (int) (getDimension(Dimensions.HEIGHT) * 0.75);

        Point canvasCenter = new Point(canvasWidth / 2, canvasHeight / 2);

        switch (Config.platformName) {
            case ANDROID -> new Drawer().drawPoint(androidDriver,canvasCenter,canvasWidth,canvasHeight);
            case IOS -> new Drawer().drawPoint(iosDriver,canvasCenter,canvasWidth,canvasHeight);
        }
    }
}
