package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import pageobjects.DriverRegistry;

import java.util.HashMap;

import static java.lang.String.format;
import static utils.Direction.UP;

public class Gestures {
    private static AppiumDriver<MobileElement> driver = DriverRegistry.getDriver();

    public static void scroll() {
        if(DriverRegistry.getPlatform().equals("android")){
            swipe(UP);
        } else {
            JavascriptExecutor js = DriverRegistry.getDriver();
            HashMap<String, Object> params = new HashMap<>();
            params.put("direction", "down");
            //temp workaround until better solution is available
            js.executeScript("mobile: scroll", params);
        }
    }

    public static void swipe(Direction direction) {
        AppiumDriver<MobileElement> driver;
        driver = DriverRegistry.getDriver();

        int startX;
        int startY;
        int endX;
        int endY;
        Dimension size;

        switch(direction){
            case UP:
                size = driver.manage().window().getSize();
                startX = size.width / 2;
                startY = (int) (size.height * 0.80);
                endX = size.width / 2;
                endY = (int) (size.height * 0.30);
                driver.swipe(startX, startY, endX, endY, 5000);
                break;
            case DOWN:
                size = driver.manage().window().getSize();
                startX = size.width / 2;
                startY = (int) (size.height * 0.20);
                endX = size.width / 2;
                endY = (int) (size.height * 0.90);
                driver.swipe(startX, startY, endX, endY, 500);
                break;
            default:
                throw new IllegalArgumentException(format("Invalid direction: %s", direction));
        }
    }
}
