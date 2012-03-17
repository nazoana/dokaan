package view;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Observable;
import java.util.logging.Logger;

import model.Customer;

import utilities.AppLogger;
import utilities.Globals;
import utilities.Util;
import widgets.LabelHeadingWidget;
import widgets.ScrollPaneWidget;
import widgets.TableWidget;
import widgets.TableWidgetModel;

import controller.CustomerListController;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-11 1.0
 *
 */
public class CustomerListView extends AbstractViewPanel  {

	/**
	 * This has to do with serialization. It is not important, but it is
	 * here to avoid compiler warning.
	 */
	private static final long serialVersionUID = 1L;
	
    /** The logger object used to log messages */
    private static final Logger logger = AppLogger.getAppLogger(CustomerListView.class.getName());
    
    private CustomerListController controller;
    
    private LabelHeadingWidget lblTitle;
    
    private TableWidget tblCustomers;
	
	public CustomerListView(CustomerListController controller){
		super();
		logger.info("Initializing the Customer List View");
		this.controller = controller;
		setLayout(new GridBagLayout());
        setBackground(Globals.WHITE_FOR_FG_HEADING_LABEL);
        initComponents();
	}
	
	/**
     * Initializes the panel with all the necessary components
     */
	private void initComponents() {
		lblTitle = new LabelHeadingWidget("lblTitle", "Customers List", LabelHeadingWidget.CENTER);
		
		String[] columnNames = new String[] {"Id", "Full Name", "Business Address", "Phone", "Date Created", "Date Modified"};
		Class<?>[] colTypes = new Class[] { Long.class, String.class, String.class, String.class, Date.class, Timestamp.class };
        int[] colEditable = new int[] { 0, 0, 0, 0, 0, 0 };
        
		TableWidgetModel model = new TableWidgetModel(
				columnNames, colTypes, colEditable, controller.getCustomers(null));
		
		tblCustomers = new TableWidget("tblCustomerList", model);
		tblCustomers.setColumnsWidths(new int[] { 40, 130, 230, 120, 130, 180 });
		
		ScrollPaneWidget scrllrTblCustomers = new ScrollPaneWidget("scrllrTblCustomers", tblCustomers);
		scrllrTblCustomers.setPreferredSize(new Dimension(850, 300));
		
		add(lblTitle, Util.defineConstraint(1, 0, 0, 0, 1, 1, true, 11));
		add(scrllrTblCustomers, Util.defineConstraint(1,1,0,1,1,1,true,17));
	}
	
	
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		System.out.println("new value: " + evt.getNewValue());
	}

	/**
     * Overrides the update method from the Observer object
     */
	@Override
	public void update(Observable observable, Object object) {
		// TODO Auto-generated method stub
		if (object instanceof Customer) {
			Customer c = (Customer) object;
			System.out.println("observer: update row id: " + c.getId() + " : "  + c.getName());
		}
	}

}
