package com.open.hotel.hooks;

import com.open.abddf.context.TestContext;
import com.open.abddf.logger.LoggerClass;
import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

import java.text.ParseException;

public class Hooks{

	private TestContext context;
	WebDriver driver;
	public Hooks(TestContext context){
		this.context = context;
	}

	@Before()
	public void beforeScenario(Scenario scenario){
		this.context.setVar("testCaseName", scenario.getName().split(":")[1]);
		this.context.setVar("testCaseID", scenario.getName().split(":")[0]);
		this.context.setVar("scenario", scenario);

		org.apache.log4j.Logger log = LoggerClass.getThreadLogger("Thread" + Thread.currentThread().getName(), this.context.getVar("testCaseName").toString());
		log.info("Thread ID:'" + Thread.currentThread().getId() + "' 'PASS'");
	}
			
	@After()
	public void afterScenario(Scenario scenario) throws ParseException {
		driver = this.context.driver();
		if(driver != null){
			driver.close();
			driver.quit();
		}
	}

}