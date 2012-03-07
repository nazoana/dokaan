package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import model.Customer;
import utilities.AppLogger;
import controller.CstmrCtrllr;
import controller.Persistence;

/**
* The main class that starts the application.
*
* @author Mahmood Khan
* @Version 2012-02-29 1.0
*
*/
public class DokaanMain {
    
    /** The logger object used to log messages */
    private static final Logger logger = AppLogger.getAppLogger(CustomerView.class.getName());
    
    /** Default AWT Toolkit */
    private Toolkit toolkit;
    
    public DokaanMain(){
    	toolkit = Toolkit.getDefaultToolkit();
    	logger.log(Level.SEVERE, "Starting");
        CstmrCtrllr ctrllr = new CstmrCtrllr();
        Customer customerModel = new Customer();
        CustomerView customerView = new CustomerView(ctrllr);
        
        ctrllr.addView(customerView);
        ctrllr.addModel(customerModel);
        
        JFrame displayFrame = new JFrame("Customer View");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to set UIManager to use system look & feel:" 
            		+ e.getMessage());
        }
        displayFrame.getContentPane().add(customerView, BorderLayout.WEST);
        displayFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        displayFrame.setTitle("Mission Metrics");
        Dimension size = toolkit.getScreenSize();
        displayFrame.pack();
        displayFrame.setLocation((size.width / 2) - (displayFrame.getWidth() / 2),
                (size.height / 2) - (displayFrame.getHeight() / 2));
        //displayFrame.setIconImage(new ImageIcon(this.getClass().getResource(
        //        "res/logo.gif")).getImage());
        displayFrame.setVisible(true);
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Persistence.getInstance().getPm();
        DokaanMain main = new DokaanMain();
    }
}
