package pageobjects;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Page {
    private static final long WEB_DRIVER_WAIT_TIMEOUT_IN_SECONDS = 45L;
    public static AppiumDriver<MobileElement> driver;
    private DesiredCapabilities capabilities = DriverRegistry.getDesiredCapabilities();

    public Page() {
        driver = DriverRegistry.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    void setFullResetToFalse() {
        DriverRegistry.getDriver().quit();
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
        driver = DriverRegistry.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    void setFullResetToTrue() {
        DriverRegistry.getDriver().quit();
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        driver = DriverRegistry.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    static void waitForElement(MobileElement element) {
        final WebDriverWait wait = new WebDriverWait(driver, WEB_DRIVER_WAIT_TIMEOUT_IN_SECONDS);
        wait.until(ExpectedConditions.visibilityOf(element));
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

    public Dimension getScreenSizeDimension() {
        return driver.manage().window().getSize();
    }


    public void scrollToTopOfScreen(MobileElement visibleElement) {
        // Context is the Y for Height.
        Dimension screenSize = getScreenSizeDimension();
        System.out.println("Screen Size is (W, H): (" + screenSize.getWidth() + ", " + screenSize.getHeight() + ")");
        Point elementLocation = visibleElement.getLocation();
        System.out.println("Element Location (X,Y) is: " + elementLocation.getX() + ", " + elementLocation.getY());
        int iOSHeightOfMenuAccountHeader = 137;
        int offset = 10;
        int heightOfMenu = 210 + offset;
        // The Method description: swipe(int startx, int starty, int endx, int endy, int duration)
        if(DriverRegistry.getPlatform().equals("android")) {
            driver.swipe(elementLocation.getX(), elementLocation.getY(), elementLocation.getX(), heightOfMenu, 2000);
        } else {
            driver.swipe(elementLocation.getX(), elementLocation.getY(), elementLocation.getX(), iOSHeightOfMenuAccountHeader, 4000);
        }

    }

}
