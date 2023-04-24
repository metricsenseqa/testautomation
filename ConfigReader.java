package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	public Properties prop;
	public Properties initProp() {
		
		prop = new Properties();
		
		FileInputStream ip;
		try {
			
			ip = new FileInputStream("src/test/resources/config/config.properties");
			prop.load(ip);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return prop;
	}
	
}
