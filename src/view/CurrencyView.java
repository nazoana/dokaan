package view;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Observable;
import java.util.logging.Logger;

import controller.CurrencyController;

import utilities.AppLogger;
import utilities.Globals;
import utilities.Util;
import widgets.LabelHeadingWidget;
import widgets.LabelWidget;
import widgets.ScrollPaneWidget;
import widgets.TableWidget;
import widgets.TableWidgetModel;
import widgets.TextWidget;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-05-01 1.0
 *
 */
public class CurrencyView extends AbstractViewPanel{
	/**
	 * This has to do with serialization; it is not needed, but 
	 * it is placed here to prevent the compiler warning
	 */
	private static final long serialVersionUID = 1L;
	
    /** The logger object used to log messages */
    private static final Logger LOGGER = AppLogger.getAppLogger(CurrencyView.class.getName());
    
    /** The controller that is associated with this view */
    private CurrencyController controller;
	
    /** The title for this view */
    private LabelHeadingWidget lblTitle;
    
    /** the table that lists all of the suppliers */
    private TableWidget tblCurrencies;
    
    /** The customized JTable model for the supplier table */ 
    private TableWidgetModel model;
    
    /** The Text field used to filter the suppliers table */
    private TextWidget txtFilter;
    
	/**
	 * Constructor
	 * @param id - the name of this view
	 */
	public CurrencyView(CurrencyController controller) {
		super("pnlCurrencyView");
		LOGGER.info("Initializing the Currencies List View");
		this.controller = controller;
		setLayout(new GridBagLayout());
        setBackground(Globals.WHITE);
        initComponents();
	}

	/**
     * Initializes the panel with all the necessary components
     */
	private void initComponents() {
		
		lblTitle = new LabelHeadingWidget("lblTitle", "Currency List", LabelHeadingWidget.CENTER);
		tblCurrencies = new TableWidget("tblCurrencies", populateTableModel());
		tblCurrencies.setFocusable(true);
		setColumnsWidth();
		LabelWidget lblFilter = new LabelWidget("lblFilter", "Search ");
		txtFilter = new TextWidget("txtFilter");
		tblCurrencies.enableFilter(txtFilter);
		
		ScrollPaneWidget scrllrTblCurrencies = new ScrollPaneWidget("scrllrTblCurrencies", tblCurrencies);
		scrllrTblCurrencies.setPreferredSize(new Dimension(800, 500));
		scrllrTblCurrencies.setMinimumSize(new Dimension(800, 500));
		
		//This has to be called after the table object (tblSuppliers) is instantiated.
		//setupTablePopupMenu();
		
		add(lblTitle, Util.defineConstraint(1, 0, 0, 0, 2, 1, true, 18));
		
		add(lblFilter, Util.defineConstraint(0, 0, 0, 1, 1, 1, false, 18));
		
		add(txtFilter, Util.defineConstraint(1, 0, 1, 1, 1, 1, true, 18));
		add(scrllrTblCurrencies, Util.defineConstraint(1,1,0,2,2,1,true,18));
		
	}
	
	/**
	 * Specifies the widths of various columns.
	 */
	private void setColumnsWidth(){
		tblCurrencies.setColumnsWidths(new int[] { 40, 130, 225, 120, 170 });
	}
	
	/**
	 * Sets up the TableWidget (JTable) model by specifying the column labels,
	 * column types, and whether columns are edit-able as well as specifying 
	 * the actual data to be shown in the table.
	 *  
	 * @return a TableWidgetModel instance.
	 */
	private TableWidgetModel populateTableModel(){
		String[] columnNames = new String[] {"Id", "Currency", "Complete Name", "Date Created", "Date Modified"};
		Class<?>[] colTypes = new Class[] { Long.class, String.class, String.class, Date.class, Timestamp.class };
        int[] colEditable = new int[] { 0, 1, 1, 0, 0 };
        
		model = new TableWidgetModel(
				columnNames, colTypes, colEditable, controller.getCurrencies(null));
		
		return model;
	}
	

	@Override
	public void update(Observable observable, Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

}
