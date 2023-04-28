import components.MenuItem;
import utils.AppiumServer;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        AppiumServer.start();

        MenuItem menuItem = new MenuItem();
        menuItem.Catalog();

        AppiumServer.stop();
    }
}
