package core;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class extentReporting {
	
	protected File reportFile;
	public ExtentReports extent;
	
	public ExtentReporter reporter;
	
	public ExtentReports initReporter() {
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		String timeStamp = now.format(formatter);
		
		extent = new ExtentReports();
		reportFile = new File("src/test/resources/output/reports/excel-comparison-report-"+timeStamp+".html");
		ExtentSparkReporter spark = new ExtentSparkReporter(reportFile);
		spark.config().setTheme(Theme.DARK);
		spark.config().setReportName("TurboPay Web Automation");
		spark.config().setDocumentTitle("TurboPay Web Automation Report");
		spark.config().setCss("extent.css");
		spark.config().setJs("extent.js");
		spark.config().enableOfflineMode(true);
		extent.attachReporter(spark);
		extent.setSystemInfo("Environment","ST2");
		extent.setSystemInfo("Tester",System.getProperty("user.name"));
		extent.setSystemInfo("APP URL","https://excel-comp.com");
		return extent;
		
	}
	
}
