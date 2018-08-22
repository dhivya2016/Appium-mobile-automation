package utils;

import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import pageobjects.DriverRegistry;

import java.util.HashMap;

public class TextFinder {
    private static String platform = DriverRegistry.getPlatform();

    public static void scrollTextIntoView(String text) {

        switch(platform) {
            case "android":
                scrollTextIntoViewAndroid(text);
                break;
            case "ios":
                scrollTextIntoViewIos(text);
                break;
        }
    }

    private static String scrollTextIntoViewAndroid(String text) {
        DriverRegistry.getDriver().findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector()).scrollIntoView(new UiSelector().text(\"" + text + "\"));"));
        return DriverRegistry.getDriver().findElement(By
                .xpath("//android.widget.TextView[contains(@text, '" + text + "')]")).getAttribute("text");
    }

    private static String scrollTextIntoViewIos(String text) {
        JavascriptExecutor js = DriverRegistry.getDriver();
        HashMap<String, String> params = new HashMap<>();
        params.put("direction", "down");
        params.put("text", text);
        js.executeScript("mobile: scroll", params);
        return text;
        //Ezra adding value to buttons so we can locate by text
        //driver.findElement(By
                //.xpath("//XCUIElementTypeStaticText[contains(@text, '" + text +"')]")).getAttribute("text");
    }
}
