import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import pageobjects.DriverRegistry;

@RunWith(Cucumber.class)
        @CucumberOptions(
        tags = {"@api_demos"},
        features = "classpath:features",
        plugin = {"pretty",
                "json:build/cucumber-json-report/cucumber.json",
                "html:build/cucumber-html-report",
                "rerun:rerun.txt"}
)

public class RunCukesTest {

    @BeforeClass
    public static void startAppium() {
        DriverRegistry.startService();
    }

    @AfterClass
    public static void stopAppium() { DriverRegistry.stopService(); }
}
