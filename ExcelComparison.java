package core;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utilities.ExcelPropertiesReader;

public class ExcelComparison {

	private Logger LOGGER;
	private ExtentTest test;
	
	public ExcelComparison(Logger logger, ExtentTest test) {
		this.LOGGER = logger;
		this.test = test;
	}
	
	ExcelPropertiesReader ExcelProp = new ExcelPropertiesReader();
	Properties xlprop = ExcelProp.initXlProp();
	
	public void compaareExcelSheets(Workbook wb1, Workbook wb2) {
		
		LOGGER.info("Starting Cell to Cell Comparison");
		
		int sheetCount = wb1.getNumberOfSheets();
		//Iterating Through All Sheets
		for(int i=0; i<sheetCount; i++) {
			Sheet s1 = wb1.getSheetAt(i);
			Sheet s2 = wb2.getSheetAt(i);
			
			int rowCount = s1.getPhysicalNumberOfRows();
			System.out.println(" Comparing "+rowCount+" rows.");
			LOGGER.info("Total Number of Rows : " + rowCount);
			
			for(int j=0; j<rowCount;j++) {
				if(s1.getRow(j) == null) {
					rowCount=rowCount+1;
					continue;
				}
				int cellCount = s1.getRow(j).getPhysicalNumberOfCells();
				for(int k=0; k<cellCount; k++) {
					Cell c1 = s1.getRow(j).getCell(k);
					Cell c2 = s2.getRow(j).getCell(k);
					if(c1 == null && c2 == null) {
						cellCount = cellCount+1;
						continue;
					}
					
					String v1 = c1.getStringCellValue().toString();
					LOGGER.debug("Cell 1 (" + c1.getAddress() + " from Sheet 1) : " + v1);
					String v2 = c2.getStringCellValue().toString();
					LOGGER.debug("Cell 2 (" + c2.getAddress() + " from Sheet 2) : " + v2);
					
					if(v1.equals(v2)) {
						System.out.println("Its Matched : " + v1 + " === " + v2);
						LOGGER.debug("Cells are equal : " + v1 + "(ACTUAL), " + v2 + "(EXPECTED)");
						test.log(Status.PASS, xlprop.getProperty(c1.getAddress().toString()) + " Matched " + v1 + "(ACTUAL), " + v2 + "(EXPECTED) at Cell " + c1.getAddress());
					}
					else {
						System.out.println("Diffrence Identified : " + v1 + " != " + v2 + ", at Cell " + c1.getAddress());
						LOGGER.debug("Cells are not equal : " + v1 + "(ACTUAL), " + v2 + "(EXPECTED)");
						test.log(Status.FAIL, xlprop.getProperty(c1.getAddress().toString()) + " Mismatched " + v1 + "(ACTUAL), " + v2 + "(EXPECTED) at Cell " + c1.getAddress());
					}
				}
			}
		}
		
	}
	
}
