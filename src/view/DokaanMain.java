package view;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.MenuSelectionManager;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import model.Customer;
import utilities.AppLogger;
import utilities.Globals;
import utilities.Util;
import widgets.ContextMenuForTextComps;
import controller.CustomerController;
import controller.CustomerListController;
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
    
    private JFrame displayFrame ;
    
    private MenuSelectionManager menuSelectionMgr ;
    
    public DokaanMain(){
    	toolkit = Toolkit.getDefaultToolkit();
    	displayFrame = new JFrame("Customer View");
    	displayFrame.getContentPane().setBackground(Globals.WHITE);
    	AppToolbar toolbar = new AppToolbar("toolbar", AppToolbar.TOP);
    	displayFrame.add(toolbar);
    	
    	 //used to de-select all menus.
		menuSelectionMgr = javax.swing.MenuSelectionManager.defaultManager();
		
    	displayFrame.setJMenuBar( new AppMenubar());
    
    	displayFrame.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if (e.getY() > 10){
					menuSelectionMgr.clearSelectedPath();
					//javax.swing.MenuSelectionManager.defaultManager().clearSelectedPath(); 
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	//To provide a context menu (with cut, copy, paste etc) in text components
    	toolkit.getSystemEventQueue().push(new ContextMenuForTextComps());
    	
    	logger.log(Level.SEVERE, "Starting");
    	
    	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch (Exception e) {
    		logger.log(Level.WARNING, "Failed to set UIManager to use system look & feel:" 
    				+ e.getMessage());
    	}
    	
    	displayFrame.setIconImage(Util.getImage("../resources/logo.png"));
    	
        CustomerController ctrllr = new CustomerController();
        Customer customerModel = new Customer();
        CustomerView customerView = new CustomerView(ctrllr);
        
        CustomerListController customerListController = new CustomerListController();
        CustomerListView customerListView = new CustomerListView(customerListController);
        
        ctrllr.addView(customerView);
        ctrllr.addView(customerListView);
        ctrllr.addModel(customerModel);
        
        customerListController.addView(customerListView);
        
        
        displayFrame.setLayout(new GridBagLayout());
        
        displayFrame.getContentPane().add(toolbar, Util.defineConstraint(1, 0, 0, 0, 1, 1, true, 17));
        
        displayFrame.getContentPane().add(customerView, 
        		Util.defineConstraint(1, 1, 0, 1, 1, 1, true, 17));
        
        displayFrame.getContentPane().add(customerListView, 
        		Util.defineConstraint(1, 1, 0, 2, 1, 1, true, 17));
        
        displayFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        displayFrame.setTitle("Dokaan");
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
        /*
         * Schedule a job for the event-dispatching thread:
         * creating and showing this application's GUI.
         */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	DokaanMain main = new DokaanMain();
            }
        });
    }
}
