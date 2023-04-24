package core;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;

import factories.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import utilities.ConfigReader;
import utilities.ExcelPropertiesReader;
import utilities.Logging;

public class AppHooks {

	private DriverFactory driverFactory;
	private ConfigReader configProperties;
	
	Properties prop;
		
	@Before(order = 0)
	public void getProperty() {
		System.out.println("Before Hook : Initiating Config Reader");
		configProperties = new ConfigReader();
		prop = configProperties.initProp();
	}
	
	@Before(order = 1)
	public void launchBrowser() {
		System.out.println("Before Hook : Initiating Web Driver");
		String browserName = prop.getProperty("browser");
		driverFactory = new DriverFactory();
		driverFactory.initDriver(browserName);
	}
	
	@After(order = 0)
	public void quitBrowser() {
		System.out.println("");
		DriverFactory.getDriver().quit();
	}

}
