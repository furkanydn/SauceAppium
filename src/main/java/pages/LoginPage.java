package pages;

import components.AppBar;
import components.MenuItem;
import utils.Config;
import utils.Linker;

public class LoginPage extends BasePage {
    MenuItem menu = new MenuItem();
    public void goLoginPageWithLink(){
        Linker.Go(Linker.Links.Login);
    }

    public void goLoginPageWithClicks(){
        switch (Config.platformName){
            case ANDROID:
                break;
            case IOS:
                menu.LogIn();
                break;
        }
    }
}
