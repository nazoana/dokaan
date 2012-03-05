package view;

import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import utilities.AppLogger;

import model.Customer;

import controller.CstmrCtrllr;
import controller.Persistence;

/**
* The main class that starts the application.
*
* @author Mahmood Khan
* @Version 2012-02-29 1.0
*
*/
public class AppMainStore {
    
    /** The logger object used to log messages */
    private static final Logger logger = AppLogger.getAppLogger(CustomerView.class.getName());
    
    public AppMainStore(){
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
        displayFrame.pack();
        displayFrame.setVisible(true);
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Persistence.getInstance().getPm();
        AppMainStore main = new AppMainStore();
    }
}
