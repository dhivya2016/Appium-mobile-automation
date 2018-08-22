import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.OutputType;
import pageobjects.DriverRegistry;

public class Hooks {

    @Before
    public static void setUp() throws Exception {
        DriverRegistry.getDriver();
    }

    @After
    public static void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
//                final byte[] screenshot = DriverRegistry.getDriver()
//                        .getScreenshotAs(OutputType.BYTES);
//                scenario.embed(screenshot, "image/png");
                if(DriverRegistry.getDesiredCapabilities().getCapability(MobileCapabilityType.FULL_RESET).equals(false)){
                    DriverRegistry.getDesiredCapabilities().setCapability(MobileCapabilityType.FULL_RESET, true);
                    DriverRegistry.getDesiredCapabilities().setCapability(MobileCapabilityType.NO_RESET, false);
                }
            }
            final byte[] snapshot = DriverRegistry.getDriver().getScreenshotAs(OutputType.BYTES);
            scenario.embed(snapshot, "image/png");
        } finally {
            if(DriverRegistry.getPlatform().equals("android")) {
                try {
                    DriverRegistry.getDriver().removeApp("");
                } catch (Exception e) {
                    System.out.println("No app found with that bundle Id!");
                }
                try {
                    DriverRegistry.getDriver().removeApp("");
                } catch (Exception e) {
                    System.out.println("No app found with that bundle Id!");
                }
            }
                DriverRegistry.getDriver().quit();
        }
    }
}
