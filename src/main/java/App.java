import utils.AppiumServer;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        AppiumServer.start();

        AppiumServer.stop();
    }
}
