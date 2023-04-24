package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UiPageObjects {

	private WebDriver driver;
	
	//By Locators
	private By taskid = By.id("input1");
	private By name = By.id("input2");
	private By phone = By.id("input3");
	private By city = By.id("input4");
	private By genrateBtn = By.id("btn1");
	
	//Constructor
	public UiPageObjects(WebDriver driver) {
		this.driver = driver;
	}
	
	//Actions
	public void enterTaskId(String actualTaskId) {
		driver.findElement(taskid).sendKeys(actualTaskId);
	}
	
	public void enterName(String actualName) {
		driver.findElement(name).sendKeys(actualName);
	}
	
	public void enterPhone(String actualPhone) {
		driver.findElement(phone).sendKeys(actualPhone);
	}
	
	public void enterCity(String actualCity) {
		driver.findElement(city).sendKeys(actualCity);
	}
	
	public void clickGenerateExcelBtn() {
		driver.findElement(genrateBtn).click();
	}
}
