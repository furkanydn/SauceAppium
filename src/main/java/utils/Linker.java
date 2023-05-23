package utils;

import java.util.Objects;

/**
 * This class provides methods for navigating to deep links in the app using Appium driver.
 */
public class Linker extends AppiumServer {
    /**
     * This enumeration represents the available deep links in the app.
     */
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
    /**
     * Returns the deep link for the given {@link Links} enum value.
     *
     * @param pages the {@link Links} enum value for which to get the deep link
     * @return the deep link for the given {@link Links} enum value
     */
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
    /**
     * Navigates to the specified page by deep linking with the page's URL.
     * If the platform is Android, it opens the deep link with "mydemoapprn://" prefix.
     * If the platform is iOS, it opens the deep link with "mydemoapprn://" prefix.
     *
     * @param page The page to navigate to.
     */
    public static void Go(Links page){
        if ((Objects.equals(Config.getProperties("appium.remote.platform.name"), "Android"))) {
            androidDriver.get("mydemoapprn://" + getDeepLink(page));
        } else iosDriver.get("mydemoapprn://" + getDeepLink(page));
    }
    /**
     * Navigates to the specified URL by deep linking with the URL.
     * If the platform is Android, it opens the deep link with "mydemoapprn://" prefix.
     * If the platform is iOS, it opens the deep link with "mydemoapprn://" prefix.
     *
     * @param optional The URL to navigate to.
     */
    public static void Go(String optional){
        if ((Objects.equals(Config.getProperties("appium.remote.platform.name"), "Android"))) {
            androidDriver.get("mydemoapprn://" + optional);
        } else iosDriver.get("mydemoapprn://" + optional);
    }
}
