package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebDriverException;
import pageobjects.DriverRegistry;

public class AppUtils {
    private static AppiumDriver<MobileElement> driver = DriverRegistry.getDriver();

    void restartApp() {
        try {
            driver.closeApp();
            driver.launchApp();
        } catch (WebDriverException e) {
            System.out.println("Could not restart app!");
        }
    }

    void hideKeyboardIfAndroid() {
        if(DriverRegistry.getPlatform().equals("android")) {
            try {
                driver.hideKeyboard();
            } catch(WebDriverException e) {
                System.out.println("Could not close keyboard.");
            }
        }
    }
}
