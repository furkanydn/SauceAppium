package utils;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Pather {
    /**
     * Converts the specified resource path to a local file system path.
     *
     * @param resourcePath the path of the resource
     * @return a Path object representing the local file system path of the resource
     * @throws IllegalArgumentException if the specified resource path cannot be found
     */
    public static Path resourcePathToLocalPath(String resourcePath) {
        URL url = ClassLoader.getSystemResource(resourcePath);
        if (url == null) {
            throw new IllegalArgumentException(String.format("Cannot find the '%s' resource", resourcePath));
        }
        return Paths.get(url.getPath());
    }

    public static Path androidApk(){
        return resourcePathToLocalPath("app/Android-MyDemoAppRN.1.3.0.build-244.apk");
    }

    public static Path iosApp(){
        return resourcePathToLocalPath("app/MyRNDemoApp.app");
    }

    public static String nodePath(){
        return "/Users/furkanaydin/.nvm/versions/node/v19.8.1/bin/node";
    }
}
