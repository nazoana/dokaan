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
import widgets.LabelWidget;
import widgets.ScrollPaneWidget;
import widgets.TableWidget;
import widgets.TableWidgetModel;
import widgets.TextWidget;

import controller.CustomerController;
import controller.CustomerListController;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-11 1.0
 *
 */
public class CustomerListView extends AbstractViewPanel{

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
    
    private TableWidgetModel model;
    
    private TextWidget txtWidget;
	
	public CustomerListView(CustomerListController controller){
		super("pnlCustomerListView");
		logger.info("Initializing the Customer List View");
		this.controller = controller;
		setLayout(new GridBagLayout());
        setBackground(Globals.WHITE);
        initComponents();
	}
	
	/**
     * Initializes the panel with all the necessary components
     */
	private void initComponents() {
		lblTitle = new LabelHeadingWidget("lblTitle", "Customers List", LabelHeadingWidget.CENTER);
		tblCustomers = new TableWidget("tblCustomerList", populateTableModel());
		setColumnsWidth();
		LabelWidget lblFilter = new LabelWidget("lblFilter", "Enter Text To Filter Table");
		txtWidget = new TextWidget("txtFilter");
		tblCustomers.enableFilter(txtWidget);
		
		ScrollPaneWidget scrllrTblCustomers = new ScrollPaneWidget("scrllrTblCustomers", tblCustomers);
		scrllrTblCustomers.setPreferredSize(new Dimension(800, 200));
		scrllrTblCustomers.setMinimumSize(new Dimension(800, 200));
		
		add(lblTitle, Util.defineConstraint(1, 0, 0, 0, 2, 1, true, 11));
		
		add(lblFilter, Util.defineConstraint(0, 0, 0, 1, 1, 1, false, 17));
		
		
		add(txtWidget, Util.defineConstraint(1, 0, 1, 1, 1, 1, true, 17));
		add(scrllrTblCustomers, Util.defineConstraint(1,1,0,2,2,1,true,17));
	}
	
	/**
	 * Sets up the TableWidget (JTable) model by specifying the column labels,
	 * column types, and whether columns are edit-able as well as specifying 
	 * the actual data to be shown in the table.
	 *  
	 * @return a TableWidgetModel instance.
	 */
	private TableWidgetModel populateTableModel(){
		String[] columnNames = new String[] {"Id", "Full Name", "Business Address", "Phone", "Date Created", "Date Modified"};
		Class<?>[] colTypes = new Class[] { Long.class, String.class, String.class, String.class, Date.class, Timestamp.class };
        int[] colEditable = new int[] { 0, 0, 0, 0, 0, 0 };
        
		model = new TableWidgetModel(
				columnNames, colTypes, colEditable, controller.getCustomers(null));
		
		return model;
	}
	
	/**
	 * Specifies the widths of various columns.
	 */
	private void setColumnsWidth(){
		tblCustomers.setColumnsWidths(new int[] { 40, 130, 225, 110, 120, 170 });
	}
	
	
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		String propertyName = evt.getPropertyName();
		if (propertyName.equals(CustomerController.ELEMENT_NAME_PROPERTY)){
            model.setValueAt(evt.getNewValue(), 1, 1);
        } 
	}

	/**
     * Overrides the update method from the Observer object
     */
	@Override
	public void update(Observable observable, Object object) {
		if (object instanceof Customer) {
			tblCustomers.setModel(populateTableModel());
			tblCustomers.configure();
			setColumnsWidth();
		}
	}
}
