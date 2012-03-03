package utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
* A class that has a few convenience methods that checks for valid values
*
* @author Mahmood Khan
* @version 2012-02-29 1.0
*
*/
public class Validator {

    private static final String ALLOWED_CHARS = "A-Za-z0-9 ~!\\@#%\\^&*()_\\-\\+=\\.\\[\\{\\]\\}|:;<>?/\"\'`";

    private static final Pattern ALLOWED_CHARACTERS = Pattern.compile("[" + ALLOWED_CHARS +"]+");

    private static final Pattern NUMBERS = Pattern.compile("\\d+");
    
    private static final Pattern NUMBERS_DECIMAL = Pattern.compile("[0-9.]+");
    
    private static final Pattern LETTERS = Pattern.compile("\\p{Alpha}+");
    
    private static final Pattern ALPHANUMERIC = Pattern.compile("[a-zA-Z0-9]+");
    
    private static final Pattern ALPHASPACE = Pattern.compile("[a-zA-Z ]+");
    
    /**
     * 
     * @param text
     * @return
     */
    public static final boolean isInt(String text) {
        if (text == null) {
            return false;
        }
        return NUMBERS.matcher(text).matches();
    }
    
    /**
     * 
     * @param text
     * @return
     */
    public static final boolean isDouble(String text){
        if (text == null) {
            return false;
        }
        boolean isItDouble = false;
        isItDouble = NUMBERS_DECIMAL.matcher(text).matches();
        try {
            Double.parseDouble(text);
        } catch (NumberFormatException e){
            isItDouble = false;
        } catch (NullPointerException e1){
            isItDouble = false;
        }
        return isItDouble;
    }

    /**
     * 
     * @param text
     * @return
     */
    public static final boolean isAlpha(String text) {
        if (text == null) {
            return false;
        }
        return LETTERS.matcher(text).matches();
    }

    /**
     * 
     * @param text
     * @return
     */
    public static final boolean isAlphaNumeric(String text) {
        if (text == null) {
            return false;
        }
        return ALPHANUMERIC.matcher(text).matches();
    }
    
    /**
     * 
     * @param text
     * @return
     */
    public static final boolean isAlphaSpace(String text) {
        if (text == null) {
            return false;
        }
        return ALPHASPACE.matcher(text).matches();
    }
    
    /**
     * 
     * @param text
     * @return
     */
    public static final boolean areValidChars(String text) {
        if (text == null) {
            return false;
        }
        return ALLOWED_CHARACTERS.matcher(text).matches();
    }
    
    
    /**
     * 
     * @param text
     * @return
     */
    public static String cleanString(String text){
        if (text == null) {
            return null;
        }
        String cleanString = text.replaceAll("[^( " + ALLOWED_CHARS +")]", " ");
        return cleanString;
    }
    


	
	/**
	 * Validates if a text-field is blank or not
	 * 
	 * @param value to check if it is null or empty
	 * @return true if the value is null or empty otherwise false
	 */
	public static boolean isBlankOrNull(String value){
		if (value == null){
			return true;
		}
		if (value.length() <= 0){
			return true;
		}
		return false;
	}
	

	/**
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
    public static boolean isDate(String date, String dateFormat) {
        try {
            DateFormat df = new SimpleDateFormat(dateFormat);
            df.setLenient(false); // this is important!
            df.parse(date);
        } catch (ParseException e) {
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
	
}
