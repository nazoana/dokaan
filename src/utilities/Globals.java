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

    
    
    
    
    
    public static final Font FONT_LABEL_HEADING_WIDGET = new Font("Helvetica", Font.BOLD, 16);
    
    public static final Font FONT_BOLD = new Font("Helvetica", Font.BOLD, 12);
    
    public static final Font FONT_APPLICATION = new Font("Helvetica", Font.PLAIN, 12);
    
    
    
    
    
    
    public static final Color RED = Color.decode("#B20838"); 
    
    public static final Color BLUE = Color.decode("#6699CC"); //"#6699CC" CC6666
    
    public static final Color BLUE_LIGHT = Color.decode("#E6E6FF");
    
    public static final Color YELLOW = Color.decode("#FFFFDD");
    
    public static final Color YELLOW_LIGHT = Color.decode("#FFFFF0");
    
    public static final Color BLACK = Color.decode("#444444");
    
    public static final Color GREEN = Color.decode("#64AE64");
    
    public static final Color GREEN_LIGHT = Color.decode("#CCFFDD");
    
    public static final Color GREEN_VERY_LIGHT = Color.decode("#DEFADE");//F3F9E6
    
    public static final Color WHITE = Color.WHITE;
    
    public static final Color GRAY_VERY_LIGHT = Color.decode("#F0F0F0");
    
    public static final Color GRAY_LIGHT = Color.decode("#E8E8E8");
    
    public static final Color GRAY = Color.decode("#C0C0C0");
    
    public static final Color GRAY_DARK = Color.decode("#606060");
    
}
