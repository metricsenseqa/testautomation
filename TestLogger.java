package core;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestLogger {

	private static final Logger logger = Logger.getLogger(TestLogger.class);
	
	public static void main(String[] args) {
		
		PropertyConfigurator.configure("src/main/java/core/log4j.properties");

	    // Log some messages at various levels
	    logger.debug("This is a debug message");
	    logger.info("This is an info message");
	    logger.warn("This is a warning message");
	    logger.error("This is an error message");
	    logger.fatal("This is a fatal message");
		
	}
	
}
