package com.open.abddf.context;

import com.open.abddf.config.Config;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestContext {
	private Map<Object, Object> vars = new HashMap<>();

	public TestContext(){

	}

	public WebDriver driver(){
		WebDriver driver = (WebDriver) vars.get(ContextVars.DRIVER);
		if(driver == null){
			//LOGGER.debug("Driver has not initialized");
		}
		return driver;
	}

	public void OpenBrowser() {
		String ExecutionMode = Config.properties.getProperty("ExecutionMode");
		WebDriver driver = null;
		if (ExecutionMode.contains("Local")) {
			driver = createLocalDriver();
		} else if (ExecutionMode.contains("Remote")) {
			driver = createRemoteDriver();
		}
		vars.put(ContextVars.DRIVER, driver);
	}

	public Object getVar(Object name){
		return vars.get(name);
	}

	public void setVar(Object name, Object value){
		this.vars.put(name, value);
	}

	public Map getVars(){
		return vars;
	}

	public void setVar(Map vars){
		for(Object key : vars.keySet()){
			this.vars.put(key, vars.get(key));
		}
	}

	public void removeVar(Object var){
		vars.remove(var);
	}

	public WebDriver createLocalDriver() {
		WebDriver driver = null;
		String browser = Config.properties.getProperty("Browser");
		String driverPath = System.getProperty("user.dir");
		if (browser.toUpperCase().contains("CH")) {
			System.setProperty("webdriver.chrome.driver", driverPath + "\\src\\test\\resources\\drivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);
			options.setExperimentalOption("useAutomationExtension", false);
			options.addArguments("no-sandbox");
			options.addArguments("start-maximized");
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			options.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking"));

			driver = new ChromeDriver(options);
		}
		return driver;
	}

	public WebDriver createRemoteDriver() {
		RemoteWebDriver driver = null;
		String browser = "CH";
		DesiredCapabilities cap = null;
		if (browser.toUpperCase().contains("CH")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("no-sandbox");
			options.addArguments("start-maximized");
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking"));
			cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			cap.setBrowserName("chrome");
			cap.setPlatform(Platform.WINDOWS);
		}
		try {
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return driver;
	}
}
