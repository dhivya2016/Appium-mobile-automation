package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import pageobjects.DriverRegistry;

import java.util.HashMap;

public class ElementFinder {
    private static AppiumDriver<MobileElement> driver = DriverRegistry.getDriver();
    private static String platform = DriverRegistry.getPlatform();

    public static void scrollToElementId(MobileElement el) {

        switch(platform) {
            case "android":
                scrollToElementIdAndroid(el);
                break;
            case "ios":
                scrollToElementIdIos(el);
                break;
        }
    }

    private static String scrollToElementIdAndroid(MobileElement el) {
        //TO IMPLEMENT
        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().resourceIdMatches(\".*" + el.getAttribute("accessibilityId") + "\");")); //.instance(0));"));
        return driver.findElement(By
                .xpath("//android.widget.TextView[contains(@id, '" + el.getAttribute("id") + "')]")).getAttribute("id");
    }

    private static String scrollToElementIdIos(MobileElement el) {
        JavascriptExecutor js = DriverRegistry.getDriver();
        HashMap<String, Object> params = new HashMap<>();
        params.put("direction", "down");
        params.put("id", el);
        js.executeScript("mobile: scroll", params);
        return driver.findElement(By
                .xpath("//XCUIElementTypeOther[contains(@name, '" + el.getAttribute("name") +"')]")).getAttribute("name");
    }
}
