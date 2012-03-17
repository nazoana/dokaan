package utilities;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import utilities.DateChooser;

/**
* This class provides a set of utility and convenience methods
* that are commonly used in the application
*
* @author Mahmood Khan
* @version 2012-02-27 1.0
*
*/
public class Util {

    
    private static final Logger logger = Logger.getLogger(Util.class.getName());
   

    private static String errMsg;
    
    private static final JFrame defaultFrame = new JFrame();
    /**
     * Constructor defined as private to defeat instantiation
     */
    private Util(){
        //this class should be accessed statically
    }

   

    /**
     * @param absoulatePath
     *            The absolute location including the file name to be created
     * @param content
     *            The actual content to go into the file
     * @param override
     *            Whether the file should be overridden if it exists already
     * @throws IOException
     */
    public static void writeTextFile(String absolutePath, String content,
            boolean override) throws IOException {
        File file = new File(absolutePath);
        if (file.exists() == true && override == true) {
            file.delete();
        } 
        FileWriter fielWriter = null;
        try {
            fielWriter = new FileWriter(file);
            fielWriter.write(content);
            fielWriter.flush();
        } finally {
            fielWriter.close();
        }
    }
    
    /**
     * 
     * @param inputStream
     *          The inputStream source to read data from
     * @return
     */
    public static String readFromTextFile(InputStream inputStream) {
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream);
            reader = new BufferedReader(inputStreamReader);
            String text = null;
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(Globals.NEW_LINE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return contents.toString();
    }
    
    /**
     * @param file
     *          The file object itself.
     * @throws FileNotFoundException
     */
    public static String readFromTextFile(File file) 
            throws FileNotFoundException {
        StringBuilder content = new StringBuilder();
        Scanner scanner = new Scanner(new FileInputStream(file),
                "UTF-8");
        try {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine() + Globals.NEW_LINE);
            }
            return content.toString();
        } finally {
            scanner.close();
        }
    }
    
    /**
     * @param absolutePath
     *          The absolute path of the file to read from
     * @throws FileNotFoundException 
     */
    public static String readFromTextFile(String absolutePath) 
            throws FileNotFoundException{
        StringBuilder content = new StringBuilder();
        Scanner scanner = new Scanner(new FileInputStream(absolutePath),
                "UTF-8");
        try {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine() + Globals.NEW_LINE);
            }
            return content.toString();
        } finally {
            scanner.close();
        }
    }
    
    /**
     * Pops up a Date chooser from which to select a date
     * @param owner
     *          The frame that owns this dialog box
     * @param dateFormat
     *          The format in which to return date
     * @return
     */
    public static String chooseDate(JFrame owner, String dateFormat){
        GregorianCalendar date = new GregorianCalendar();
        DateChooser dc = new DateChooser(owner, date);
        SimpleDateFormat dateFormatter = null;
        if (dc.showDateChooser() == DateChooser.OK_OPTION) {
          date = dc.getDate();
          dateFormatter =  new SimpleDateFormat(dateFormat);
          return dateFormatter.format(date.getTime());
        }
        return null;
    }


    /**
     * Returns an ImageIcon, or null if the path was invalid.
     * 
     * @param path
     *          Relative path to the class of Utility
     * @return
     */
    public static ImageIcon getImageIcon(String path) {
        java.net.URL imgURL = Util.class.getResource(path); 
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            errMsg = "could not find " + path;
            showWarning(null, errMsg, "FileNotFound");
            logger.log(Level.SEVERE, errMsg);
            return null;
        }
    }

    /**
     * 
     * @param title
     *            Title text for border
     * @param heading
     *          Indicates whether heading font or regular font be used
     * @return
     */
    public static TitledBorder makeBorder(String title, boolean heading) {
        TitledBorder border = new TitledBorder(new LineBorder(Color.LIGHT_GRAY),
                title);
        border.setTitleColor(Globals.RED_FOR_BG_HEADING_LABEL);
        //border.setTitleJustification(TitledBorder.CENTER);
        border.setTitleFont(heading ? Globals.FONT_LABEL_HEADING_WIDGET : Globals.FONT_BOLD);
        return border;
    }

    public static String formatDate(java.util.Date date, String format) {
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	String val = sdf.format((Date) date);
    	return val;
    }
    
    /**
     * Returns the current Timestamp;
     * @return
     */
    public static Timestamp getTimestamp(){
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }

  
    /**
     * @param owner
     *            The frame that owns this dialog box
     * @param message
     *            Message that shows up in the dialog box
     * @param title
     *            Title that shows up in the dialog's title bar
     */
    public static void showInfo(JFrame owner, String message, String title) {
        JOptionPane.showConfirmDialog(owner, message, title,
                JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * @param owner
     *            The frame that owns this dialog box
     * @param message
     *            Message that shows up in the dialog box
     * @param title
     *            Title that shows up in the dialog's title bar
     */
    public static void showError(JFrame owner, String message, String title) {

        JOptionPane.showConfirmDialog( (owner == null ? defaultFrame : owner),
                message, title, JOptionPane.PLAIN_MESSAGE,
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * @param owner
     *            The frame that owns this dialog box
     * @param message
     *            Message that shows up in the dialog box
     * @param title
     *            Title that shows up in the dialog's title bar
     * @return
     */
    public static void showWarning(JFrame owner, String message, String title) {
        JOptionPane.showConfirmDialog((owner == null ? defaultFrame : owner), message, title,
                JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE);
    }

    
    /**
     * Shows an input dialog box for user to type a value in it
     * @param frame
     * @param message
     * @param title
     * @return
     */
    public static String showInputDialog(JFrame owner, String message, String title) {
        String response = null;
        response = JOptionPane.showInputDialog((owner == null ? defaultFrame : owner), message, title,
                JOptionPane.QUESTION_MESSAGE);
        return response;
    }

    /**
     * @param owner
     *            The frame that owns this dialog box
     * @param message
     *            Message that shows up in the dialog box
     * @param title
     *            Title that shows up in the dialog's title bar
     * @return -1: indicates neither 'yes' nor 'no' is selected 
     *    1: indicates 'no' is selected 0: indicates 'yes' is selected
     */
    public static int showConfirm(JFrame owner, String message, String title) {
        int response = JOptionPane.showConfirmDialog((owner == null ? defaultFrame : owner), message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        
        return response;
    }
    
    public static int showCancel(JFrame owner, String message, String title) {
        int response = JOptionPane.showConfirmDialog((owner == null ? defaultFrame : owner), message, title,
                JOptionPane.CANCEL_OPTION, JOptionPane.CANCEL_OPTION);
        
        
        return response;
    }
    
    /**
     * Returns a GridBagLayout constraint that forces a component to be places
     * in a specific location inside the grid
     * 
     * @param weightx
     *            Whether the component stretches horizontally as the form is
     *            stretched or shrunk
     * @param weighty
     *            Whether the component stretches vertically when the form is
     *            stretched or shrunk
     * @param gridx
     *            The position of component on the x-axis in terms of the number
     *            of column in a grid
     * @param gridy
     *            The position of component on the y-axis in terms of the number
     *            of row in a grid
     * @param gridwidth
     *            Specifies the number of columns that the component occupies
     * @param gridheight
     *            Specifies the number of rows that the component occupies
     * @param fill
     *            When set to true, the component fills all available space, if
     *            any.
     * @param anchor
     *            If the 'fill' parameter is set to false, then using 'anchor'
     *            one can specify where to locate the component, if there is
     *            more space than the component can occupy.
     * @return
     */
    public static GridBagConstraints defineConstraint(double weightx, double weighty,
            int gridx, int gridy, int gridwidth, int gridheight, boolean fill,
            int anchor) {
        GridBagConstraints theConstraint = new GridBagConstraints();
        theConstraint.weightx = weightx; // resizing behavior horizontally
        theConstraint.weighty = weighty; // resizing behavior vertically
        theConstraint.gridx = gridx; // column in the grid
        theConstraint.gridy = gridy; // row in the grid
        theConstraint.gridwidth = gridwidth; // # of columns to merge
        theConstraint.gridheight = gridheight; // # of rows to merge
        theConstraint.fill = fill ? GridBagConstraints.HORIZONTAL
                : GridBagConstraints.NONE;
        theConstraint.anchor = anchor; // WEST=17, EAST=13, SOUTH=15, NORTH=11, NORTHWEST=18
        //System.out.println(GridBagConstraints.NORTHWEST);
        theConstraint.insets = new Insets(2, 5, 5, 5);
        return theConstraint;
    }
    
    
    /**
     * 
     * @param imageFilePath
     *          This is the path within the JAR file e.g.
     *          "/org/mercycorps/images/flags/"
     * @return
     */
    public static Image getImage(String imageFilePath){
        URL url = Util.class.getResource(imageFilePath);  
        Image img = Toolkit.getDefaultToolkit().getImage(url);
        return img;
    }
    
    /**
     * Reads an image file from within jar file and returns it as an
     * Image
     * @param imageFilePath
     *          The imageFilePath needs to be formatted as:
     *          /org/mercycorps/images/logo.gif"
     * @return
     */
    public static Image getImageViaInputStream(String imageFilePath) {
        Image img = null;
        InputStream is = Util.class.getResourceAsStream(imageFilePath);
        if (is != null) {
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                int ch;
                while ((ch = bis.read()) != -1) {
                    baos.write(ch);
                }
                img = Toolkit.getDefaultToolkit()
                        .createImage(baos.toByteArray());
            } catch (IOException e) {
                showError(null, "Failed to read: " + 
                        imageFilePath + " "  + e.getMessage(), "IOException");
            }
        }
        return img;
    }

}
