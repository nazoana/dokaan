package utilities;

import java.awt.Color;
import java.awt.Font;

/**
* This class defines a set of Global fields to be available
* throughout the application
*
* @author Mahmood Khan
* @version 2012-02-24 1.0
*
*/
public class Globals {
    
    /** The file separator string */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    
    /** The new line character */
    public static final String NEW_LINE = System.getProperty("line.separator");
    
    /** The path to home directory; it is platform independent */
    public static final String HOME_DIR = System.getProperty("user.home");
    
    public static final String APP_ROOT = HOME_DIR + FILE_SEPARATOR + "store";
    
    public static final String DB_PATH = APP_ROOT + FILE_SEPARATOR + "storedb";
    
    public static final String DB_PATH_EXT = APP_ROOT + FILE_SEPARATOR + "storedb.h2.db";
    
    /** The path to the datanucleus.properties file used for creating the db and its schema */
    public static final String DATNUCLEUS_PROPERTIES = APP_ROOT + FILE_SEPARATOR + "datanucleus.properties";
    
    public static final String LOG_FILE_PATH = APP_ROOT + FILE_SEPARATOR + "applog.log";
    
    public static final String LOG_FILE_CONFIG = APP_ROOT + FILE_SEPARATOR + "logging.properties";
    
    /** Font used in all Titles */
    public static final Font FONT_TITLE = new Font("Verdana", Font.BOLD, 16);
    
    /** BOld font used in sub-headings */
    public static final Font FONT_BOLD = new Font("Verdana", Font.BOLD, 11);
    
    /** Regular font used throughout the application */
    public static final Font FONT_REGULAR = new Font("Verdana", Font.PLAIN, 13);
    
    public static final Color RED = Color.decode("#B20838"); 
    
    public static final Color BLUE = Color.decode("#6699CC"); //"#6699CC" CC6666
    
    public static final Color YELLOW = Color.decode("#FFFFDD");
    
    public static final Color BLACK = Color.decode("#444444");
    
    public static final Color GREEN = Color.decode("#025D0E");
    
    public static final Color LIGHT_GREEN = Color.decode("#CCFFDD");
    
    public static final Color WHITE = Color.WHITE;
    
    public static final Color LIGHT_GRAY = Color.decode("#E8E8E8");
    
}
