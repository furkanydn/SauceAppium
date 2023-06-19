package pages;

import components.MenuItem;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import touch.Drawer;
import utils.Config;

public class DrawingPage extends BasePage {

    public void goDrawingPageWithClicks(){
        new MenuItem().Drawing();
    }
    public void drawFace() {
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
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
    public enum Dimensions {WIDTH,HEIGHT}

    private int getDimension(Dimensions dimension) {
        Dimension size = switch (Config.platformName) {
            case ANDROID -> androidDriver.manage().window().getSize();
            case IOS -> iosDriver.manage().window().getSize();
        };

        return dimension == Dimensions.WIDTH ? size.getWidth() : size.getHeight();
    }
}
