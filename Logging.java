package utilities;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Logging {

	public Logger LOGGER;
	
	public Logger initLogger() {

		LOGGER = Logger.getLogger("excel-comparison-logger");
		PropertyConfigurator.configure("src/test/resources/config/log4j.properties");
		return LOGGER;
		
	}
	
}
