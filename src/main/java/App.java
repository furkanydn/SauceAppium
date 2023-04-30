import components.AppBar;
import components.MenuItem;
import utils.AppiumServer;

import java.io.IOException;

import static components.AppBar.SortComp.*;

public class App {
    public static void main(String[] args) throws IOException {
        AppiumServer.start();

        var appBar = new AppBar();
        appBar.horizontalScroll();

        AppiumServer.stop();
    }
}
