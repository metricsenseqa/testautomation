package factories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

	public WebDriver initDriver(String browser) {

		System.out.println("Executing test cases in " + browser + " browser");

		if (browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			tlDriver.set(new ChromeDriver());
		} else if (browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			tlDriver.set(new FirefoxDriver());
		} else if (browser.equals("ie")) {
			WebDriverManager.iedriver().setup();
			tlDriver.set(new InternetExplorerDriver());
		} else {
			System.out.println("Please pass the correct browser value: " + browser);
		}

		return getDriver();

	}

	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}

}
