package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelOperations {
	
	public Workbook setWorkbook(File file) throws EncryptedDocumentException, IOException {
		Workbook wb = WorkbookFactory.create(file);
		return wb;
	}
	
	private static int getRowIndex(String cellAddress) {
        return Integer.parseInt(cellAddress.replaceAll("[^\\d]", "")) - 1;
    }
    
    private static int getColumnIndex(String cellAddress) {
        String column = cellAddress.replaceAll("[^A-Za-z]", "");
        int columnNumber = 0;
        for (int i = 0; i < column.length(); i++) {
            columnNumber = columnNumber * 26 + (column.charAt(i) - 'A' + 1);
        }
        return columnNumber - 1;
    }
	
	public String getCellValueByAddress(Workbook wb, String cellAddress, String sheetName) {
		Sheet sheet = wb.getSheet(sheetName);
		Cell cell = sheet.getRow(getRowIndex(cellAddress)).getCell(getColumnIndex(cellAddress));
		String cellValue = cell.getStringCellValue();
		return cellValue;
	}
		
}
