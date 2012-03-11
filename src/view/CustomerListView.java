package view;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

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
public class CustomerListView extends AbstractViewPanel implements ActionListener {

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
		
		String[] columnNames = new String[] {"Id", "name", "address"};
		Class<?>[] colTypes = new Class[] { Long.class, String.class, String.class };
        int[] colEditable = new int[] { 0, 0, 0 };
        
		TableWidgetModel model = new TableWidgetModel(
				columnNames, colTypes, colEditable, controller.getCustomers(null));
		
		tblCustomers = new TableWidget("tblCustomerList", model);
		
		ScrollPaneWidget scrllrTblCustomers = new ScrollPaneWidget("a", tblCustomers);
		scrllrTblCustomers.setPreferredSize(new Dimension(450, 300));
		
		add(lblTitle, Util.defineConstraint(1, 0, 0, 0, 1, 1, true, 11));
		add(scrllrTblCustomers, Util.defineConstraint(1,1,0,1,1,1,true,17));
	}
	
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

}
