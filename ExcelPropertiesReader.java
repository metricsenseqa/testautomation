package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ExcelPropertiesReader {

	public Properties xlprop;
	public Properties initXlProp() {
		
		xlprop = new Properties();
		
		FileInputStream ip;
		try {
			
			ip = new FileInputStream("src/test/resources/config/xlprop.properties");
			xlprop.load(ip);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return xlprop;
	}
	
}
