package utils;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Pather {
    public static Path resourcePathToLocalPath(String s){
        URL url = ClassLoader.getSystemResource(s);
        Objects.requireNonNull(url, String.format("Cannot find the '%s' resource", s));
        return Paths.get(url.getPath());
    }

    public static Path androidApk(){
        return resourcePathToLocalPath("app/Android-MyDemoAppRN.1.3.0.build-244.apk");
    }

    public static Path iosApp(){
        return resourcePathToLocalPath("app/MyRNDemoApp.app");
    }

    public static Path nodePath(){
        return resourcePathToLocalPath("/Users/furkanaydin/.nvm/versions/node/v19.8.1/bin/node");
    }
}
