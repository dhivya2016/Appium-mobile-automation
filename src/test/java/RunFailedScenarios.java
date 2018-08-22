import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "@rerun.txt",
        plugin = {"pretty",
                "json:build/cucumber-json-report/cucumber_rerun.json",
                "html:build/cucumber-html-report"}
)

public  class RunFailedScenarios {

}
