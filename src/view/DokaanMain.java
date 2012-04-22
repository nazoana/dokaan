package view;

import java.awt.BorderLayout;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import utilities.AppLogger;
import utilities.Globals;
import utilities.Util;
import widgets.AppTabbedPane;
import widgets.PanelWidget;
import controller.Controllers;
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
	private static final Logger LOGGER = AppLogger.getAppLogger(CustomerView.class.getName());

	/** The JTabbedPane object used to show differnt objects in its own tab */
	private static AppTabbedPane tabbedPane;
	
	
	/**
	 * Constructor
	 */
	public DokaanMain() {
		LOGGER.info("Initializing ...");
		Initialization.Initialize();
		tabbedPane = new AppTabbedPane("tabbedPane", JTabbedPane.TOP);
		
		//Add the tabbedPane container object to the main frame.
		Globals.MAIN_FRMAE.getContentPane().add(tabbedPane, BorderLayout.NORTH);
		
		//Show the overview tab, always.
		showOverviewTab();
		
		Globals.MAIN_FRMAE.pack();
		Globals.MAIN_FRMAE.alignCenter();
		Globals.MAIN_FRMAE.setVisible(true);
	}

	

	/**
	 * Creates and returns a JPanel
	 * 
	 * @return - returns a JPanel
	 */
	private static JPanel createPanel(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Globals.WHITE);
		panel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		return panel;
	}
	
	/**
     * Adds the New Customer panel to the main frame.
     */
    public static void showNewSupplierTab() {
        Controllers.SUPPLIER_CONTROLLER.getOrCreateObject(-1L);
        SupplierView newSupplier = new SupplierView(Controllers.SUPPLIER_CONTROLLER, -1L);
        Controllers.SUPPLIER_CONTROLLER.addView(newSupplier);
        JPanel panel = createPanel();
        panel.add(newSupplier, BorderLayout.NORTH);
        tabbedPane.addTab("New Supplier", panel, Util.getImageIcon("../resources/newSupplier.png"));
    }
    
    /**
     * Adds the List of customer panel to the main frame
     */
    public static void showSuppliersTab() {
        SupplierListView suppliers = new SupplierListView(Controllers.SUPPLIERS_LIST_CONTROLLER);
        Controllers.SUPPLIERS_LIST_CONTROLLER.addView(suppliers);
        Controllers.SUPPLIER_CONTROLLER.addView(suppliers);
        JPanel panel = createPanel();
        panel.add(suppliers, BorderLayout.NORTH);
        tabbedPane.addTab("Supplier", panel, Util.getImageIcon("../resources/supplier.png"));
    }
    
	/**
	 * Adds the List of customer panel to the main frame
	 */
	public static void showCustomersTab() {
		CustomerListView customers = new CustomerListView(Controllers.CUSTOMERS_LIST_CONTROLLER);
		Controllers.CUSTOMERS_LIST_CONTROLLER.addView(customers);
		Controllers.CUSTOMER_CONTROLLER.addView(customers);
		JPanel panel = createPanel();
		panel.add(customers, BorderLayout.NORTH);
		tabbedPane.addTab("Customer", panel, Util.getImageIcon("../resources/customer.png"));
	}

	/**
	 * Adds the New Customer panel to the main frame.
	 */
	public static void showNewCustomerTab() {
		Controllers.CUSTOMER_CONTROLLER.getOrCreateObject(-1L);
		CustomerView newCustomer = new CustomerView(Controllers.CUSTOMER_CONTROLLER, -1L);
		Controllers.CUSTOMER_CONTROLLER.addView(newCustomer);
		JPanel panel = createPanel();
		panel.add(newCustomer, BorderLayout.NORTH);
		tabbedPane.addTab("New Customer", panel, Util.getImageIcon("../resources/newCustomer.png"));
	}

	/**
	 * Adds the Overview panel to the main frame
	 */
	public static void showOverviewTab() {
		PanelWidget overview = new Overview();
		JPanel panel = createPanel();
		panel.add(overview, BorderLayout.NORTH);
		tabbedPane.addTab(" Overview", panel, Util.getImageIcon("../resources/summary.png"));
	}

	/**
	 * The main entry point into the application
	 * 
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Persistence.getInstance().getPm();
		/*
		 * Schedule a job for the event-dispatching thread: creating and showing
		 * this application's GUI.
		 */
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				DokaanMain main = new DokaanMain();
			}
		});
	}
}
