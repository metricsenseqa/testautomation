package stepDefinitions;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import core.ExcelComparison;
import core.extentReporting;
import factories.DataFactory;
import factories.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.UiPageObjects;
import utilities.ConfigReader;
import utilities.ExcelOperations;
import utilities.ExcelPropertiesReader;
import utilities.Logging;

public class excelComparisonSteps {
	
	protected File [] inputFiles;
	protected File [] baseFiles;
	protected Workbook inputXl;
	protected Workbook baseExcel;
	protected Workbook toCompareExcel;
	protected File reportFile;
	
	private UiPageObjects uiPage;
	private ExcelOperations xlOps = new ExcelOperations();
	private DataFactory df = new DataFactory();
	private DriverFactory driverFactory;
	private extentReporting extReport;
	private ExtentReports extent;
	private ExtentTest test;
	private ExcelPropertiesReader xlProperties;
	private ConfigReader configProperties;
	private Logging logs;
	
	
	Properties prop;
	Properties xlProp;
	Logger LOGGER; 
	
	
	
	@Before
	public void beforeScenario() {
		
		xlProperties = new ExcelPropertiesReader();
		xlProp = xlProperties.initXlProp();
		
		logs = new Logging();
		LOGGER = logs.initLogger();
		
		configProperties = new ConfigReader();
		prop = configProperties.initProp();

		extReport = new extentReporting();
		extent = extReport.initReporter();
		
	}
	
	
	@Given("user gets the  list of all the input files available in shared location")
	public void user_gets_the_list_of_all_the_input_files_available_in_shared_location() {
	    
		File [] files = new File("src/main/resources/ExcelSheets").listFiles();
		if (files == null) {
	            System.out.println("Failed to list files in directory: " );
	            LOGGER.info("Specified Directory has no excel sheets");
	            return;
	        }
		
		Instant cuttOffDate = Instant.now().minus(3, ChronoUnit.DAYS);
		LOGGER.info("Picking Excel Sheets Modified after : " + cuttOffDate);
		baseFiles = Arrays.stream(files).filter(file -> {
	            long lastModifiedTime = file.lastModified();
	            Instant lastModifiedInstant = Instant.ofEpochMilli(lastModifiedTime);
	            return lastModifiedInstant.isAfter(cuttOffDate);
	        }).toArray(File[]::new);
	    
	    System.out.println("--------");
	    System.out.println(Arrays.toString(baseFiles));
	    LOGGER.info("Picked Files for Processing : " + baseFiles);
	    
	}

	@When("user will generate an excel from UI and compare with existing base Excel for each file in shared location")
	public void user_will_generate_an_excel_from_ui_and_compare_with_existing_base_excel() {
	    
		int baseFileCount = baseFiles.length;
		LOGGER.info("Processing " + baseFileCount + " files");
		
		for(int i=0; i<baseFileCount; i++) {
			
			File inputFile = baseFiles[i];
			LOGGER.info("Processing " + baseFiles[i].getName());
			test = extent.createTest(baseFiles[i].getName().replace(".xlsx", ""));
			test.assignCategory("Excel Comparison Test");
			LOGGER.info("Creating Comparison Test ... ");
			
			try {
				user_reads_and_gets_the_input_data_from_input_excel(inputFile);
			} catch (EncryptedDocumentException | IOException e) { e.printStackTrace();}
			
			user_will_launch_browser_and_open_webapplication();
			enter_data_into_given_ui();
			
			try {
				clicks_on_generate_excel_button_to_download_the_excel();
			} catch (InterruptedException e) { e.printStackTrace(); }
			
			user_checks_if_the_excel_file_is_downloaded_successfully(inputFile);
			compares_both_the_excel_files(toCompareExcel, baseExcel);
			
		}
		
	}
	
	@When("user reads and gets the input data from input excel")
	public void user_reads_and_gets_the_input_data_from_input_excel(File file) throws EncryptedDocumentException, IOException {
			
			inputXl = xlOps.setWorkbook(file);
			
			LOGGER.info("Setting Input Vaiables for UI to POJO");
			
			String taskid = xlOps.getCellValueByAddress(inputXl, "B1", "Sheet2");
			df.setTaskId(taskid);
			LOGGER.info("Task Id : " + taskid);
			
			String name = xlOps.getCellValueByAddress(inputXl, "B2", "Sheet2");
			df.setName(name);
			LOGGER.info("Name : " + name);
			
			String phone = xlOps.getCellValueByAddress(inputXl, "B3", "Sheet2");
			df.setPhone(phone);
			LOGGER.info("Phone : " + phone);
			
			String city = xlOps.getCellValueByAddress(inputXl, "B4", "Sheet2");
			df.setCity(city);
			LOGGER.info("City : " + city);
			
	}

	@Then("user will Launch Browser and open webapplication")
	public void user_will_launch_browser_and_open_webapplication() {
		LOGGER.info("Launching Web Application ...");
		DriverFactory.getDriver().get("file:///C:/Users/Owner/eclipse-workspace/ExcelTestAutomation/src/main/resources/testApplication/index.html");
	}

	@Then("enter data into given UI")
	public void enter_data_into_given_ui() {
		
		uiPage = new UiPageObjects(DriverFactory.getDriver());
		
		uiPage.enterTaskId(df.getTaskId());
		LOGGER.info("Setting Task Id to : " + df.getTaskId());
		
		uiPage.enterName(df.getName());
		LOGGER.info("Setting Name to : " + df.getName());
		
		uiPage.enterPhone(df.getPhone());
		LOGGER.info("Setting Phone to : " + df.getPhone());
		
		uiPage.enterCity(df.getCity());
		LOGGER.info("Setting City to : " + df.getCity());
		
	}

	@Then("clicks on Generate Excel button to download the excel")
	public void clicks_on_generate_excel_button_to_download_the_excel() throws InterruptedException {
		uiPage.clickGenerateExcelBtn();
		Thread.sleep(10000);
		LOGGER.info("Waiting for file download to complete ...");
	}

	@When("user checks if the excel file is downloaded successfully")
	public void user_checks_if_the_excel_file_is_downloaded_successfully(File file) {
	    
		System.out.println(file.getName());
		String fileName = file.getName();
		
		int dashIndex = fileName.lastIndexOf('.');
	    String fileNameWithoutExtension = (dashIndex == -1) ? fileName : fileName.substring(0, dashIndex);
	    
	    String toCompareFileName = fileNameWithoutExtension + "-ui.xlsx";
		System.out.println(toCompareFileName);
		LOGGER.info("File to Compare is : " + toCompareFileName);
		
		String baseFileName = fileNameWithoutExtension + ".xlsx";
		System.out.println(baseFileName);
		LOGGER.info("Base File for Comparison is : " + baseFileName);
		
		String downloadsDir = "C:/Users/Owner/Downloads/";
		String excelDir = "src/main/resources/ExcelSheets/";
		File toCompareFile = new File(downloadsDir+toCompareFileName);
		File baseFile = new File(excelDir+baseFileName);
		
		LOGGER.info("Checking id both files exist : " + baseFileName + " and " + toCompareFileName);
		if(toCompareFile.exists() && baseFile.exists()) {
			System.out.println("Both Files Exists");
			LOGGER.info("Both Files Exist");
			try {
				LOGGER.info("Creating Workbooks for Both Files");
				baseExcel = WorkbookFactory.create(baseFile);
				toCompareExcel = WorkbookFactory.create(toCompareFile);
				
			} catch (EncryptedDocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	@When("compares both the excel files")
	public void compares_both_the_excel_files(Workbook wb1, Workbook wb2) {
	    
		ExcelComparison ec = new ExcelComparison(LOGGER, test);
		ec.compaareExcelSheets(wb1, wb2);

	}

	@Then("user generates extent-report")
	public void user_generates_extent_report() {
		LOGGER.info("Generating Excel Comparison Report");
	    extent.flush();
	}
	
	
//	@After
//	public void afterScenario() {
//		DriverFactory.getDriver().quit();
//	}
	
}
