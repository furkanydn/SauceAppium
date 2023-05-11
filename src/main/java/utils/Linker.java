package utils;

import java.util.Objects;


public class Linker extends AppiumServer {
    public enum Links {
        Products,
        ProductDetails,
        Login,
        Webview,
        CheckoutAddress,
        CheckoutPayment,
        CheckoutComplete,
        QRCodeScanner,
        GeoLocation,
        Drawing,
        About
    }
    private static final String PREFIX = "mydemoapprn://";
    private static String getDeepLink(Links pages) {
        return switch (pages) {
            case Products -> "store-overview";
            case ProductDetails -> "product-details/1";
            case Login -> "login";
            case Webview -> "webview";
            case CheckoutAddress -> "checkout-address";
            case CheckoutPayment -> "checkout-payment";
            case CheckoutComplete -> "checkout-complete";
            case QRCodeScanner -> "qr-code-scanner";
            case GeoLocation -> "geo-locations";
            case Drawing -> "drawing";
            case About -> "about";
        };
    }

    public static void Go(Links page){
        String deepLink = PREFIX + getDeepLink(page);
        if ((Objects.equals(Config.getProperties("appium.remote.platform.name"), "Android"))) {
            androidDriver.get(deepLink);
        } else {
            iosDriver.get(deepLink);
        }
    }
}
