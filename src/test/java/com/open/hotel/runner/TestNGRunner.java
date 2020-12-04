package com.open.hotel.runner;

import com.open.abddf.config.Config;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import io.cucumber.testng.CucumberOptions;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

@CucumberOptions(
		plugin={
				"pretty",
				"html:target/cucumberReport",
				"json:target/cucumberReport/cucumber.json",
		},
		tags={"@Compare2files4"},
		features = {"src/test/java/com/open/hotel/features"},
		glue={"com.open.hotel.hooks", "com.open.hotel.stepdefinitions"},
		strict = true,
		dryRun = false
)
public class TestNGRunner extends AbstractTestNGCucumberTests {

	//public static Properties properties = null;
	@Override
	@DataProvider (parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}

	@BeforeSuite()
	public void setup(){
		Config.createFolder(Config.properties.getProperty("resultFolder"));
		Config.createFolder(Config.properties.getProperty("resultFolderName"));
	}

	@AfterSuite()
	public void tearDown(){
		List<String> reports = new LinkedList<>();
		reports.add("target/cucumberReport/cucumber.json");
		TestNGRunner.generateCucumberReport(reports);
	}


	public static void generateCucumberReport(List<String> jsonFiles){
		try{
			File reportOutputDirectory = new File("target/cucumber-html-full-report");
			String buildName = "20";
			String projectName = "Sample Project";
			Configuration configuration = new Configuration(reportOutputDirectory, projectName);
			configuration.setBuildNumber(buildName);

			configuration.addClassifications("Browser", "Chriome");
			configuration.setSortingMethod(SortingMethod.NATURAL);
			configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
			configuration.addPresentationModes(PresentationMode.PARALLEL_TESTING);
			configuration.setTrendsStatsFile(new File("target/test-classes/demo-trends.json"));

			ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
			reportBuilder.generateReports();
		}catch (Exception e){
			//LOGGER.error("Failed to generate HTML report, error: " + e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
