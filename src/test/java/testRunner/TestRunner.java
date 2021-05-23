package testRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		
		features = {".//features/Customers.feature",
					".//features/login.feature"},
		glue = "stepDefinations",
		monochrome=true,
		//tags= "@sanity",
		plugin = {"pretty",
				"html:Reports",
					"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
		
		
		)




public class TestRunner {

}
