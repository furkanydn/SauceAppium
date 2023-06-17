package pages;

import components.MenuItem;
import org.openqa.selenium.Point;
import touch.Drawer;
import utils.Config;

public class DrawingPage extends BasePage {

    public void goDrawingPageWithClicks(){
        new MenuItem().Drawing();
    }
    public void drawFace(){
        Point head = new Point(220,450);
        Point leftEye = head.moveBy(-50, -50);
        Point rightEye = head.moveBy(50, -50);
        Point mouth = head.moveBy(0, 50);

        switch (Config.platformName){
            case ANDROID -> {
                new Drawer().drawCircle(androidDriver,head,120,30);
                new Drawer().drawCircle(androidDriver,leftEye, 20, 20);
                new Drawer().drawCircle(androidDriver, rightEye, 20, 20);
                new Drawer().drawCircle(androidDriver, mouth, 40, 20);
            }
            case IOS -> {
                new Drawer().drawCircle(iosDriver,head,120,30);
                new Drawer().drawCircle(iosDriver,leftEye, 20, 20);
                new Drawer().drawCircle(iosDriver, rightEye, 20, 20);
                new Drawer().drawCircle(iosDriver, mouth, 40, 20);
            }
        }
    }
}
