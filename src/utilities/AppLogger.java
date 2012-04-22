package utilities;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import utilities.Globals;

/**
* This class customizes the java.util.logging.Logger object
* for this application.
*
* @author Mahmood Khan
* @Version 2012-03-06 1.0
*
*/
public class AppLogger {

	/** Java logger object */
	private static Logger logger;
	
	/** FileHandler for the log file */
	private static FileHandler handler;
	
	/**Error message container */
	private static String errMsg;

	/**
	 * 
	 * @param className
	 * @return
	 */
	public static Logger getAppLogger(String className) {

		if (logger == null) {
			logger = Logger.getLogger(className);
			
			// Create a file handler that write log record to a file
			try {
				handler = new FileHandler(Globals.LOG_FILE_PATH, 102400, 1, true);
			} catch (SecurityException e) {
				errMsg = "Coult nod instantiate FileHandler for log file |" + e.getMessage() + "|" + e.getCause();
			} catch (IOException e) {
				errMsg = "Coult nod instantiate FileHandler for log file |" + e.getMessage() + "|" + e.getCause();
			}

			AppLoggerFormatter formatter = new AppLoggerFormatter();
			handler.setFormatter(formatter);

			logger.addHandler(handler);
			logger.setLevel(Level.WARNING);
		}
		
		
		//logger.setLevel(Level.SEVERE);
		return logger;
	}
	
	/**
	 * Accessor method for the errMsg field
	 * @return
	 */
	public String getErrMsg(){
		return errMsg;
	}

	public static void main(String[] args) {
		getAppLogger(AppLogger.class.getName()).log(Level.SEVERE, "Hello");
		getAppLogger(AppLogger.class.getName()).log(Level.SEVERE, "Hi, again.");
	}
}
