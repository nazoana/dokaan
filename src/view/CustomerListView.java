package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Observable;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import model.Customer;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.BalloonTip.AttachLocation;
import net.java.balloontip.BalloonTip.Orientation;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.ToolTipBalloonStyle;
import utilities.AppLogger;
import utilities.Globals;
import utilities.Util;
import widgets.ActionPanel;
import widgets.AppMenuItem;
import widgets.LabelHeadingWidget;
import widgets.LabelWidget;
import widgets.PanelWidget;
import widgets.ScrollPaneWidget;
import widgets.TableWidget;
import widgets.TableWidgetModel;
import widgets.TextWidget;
import controller.Controllers;
import controller.CustomerController;
import controller.CustomerListController;

/**
 * This is the view to create or edit a customer record. The ActionListener is used to to manage user clicks
 * on the right-click menu, e.g. the edit or delete actions.
 * 
 * The MouseListener is used to determine if one of the actionPanels has been clicked on.
 * If it is, then expand the associated panel and hide the other panels.
 * 
 * @author Mahmood Khan
 * @version 2012-03-11 1.0
 *
 */
public class CustomerListView extends AbstractViewPanel implements ActionListener, MouseListener{

	/**
	 * This has to do with serialization. It is not important, but it is
	 * here to avoid compiler warning.
	 */
	private static final long serialVersionUID = 1L;
	
    /** The logger object used to log messages */
    private static final Logger logger = AppLogger.getAppLogger(CustomerListView.class.getName());
    
    /** The controller that is associated with this view */
    private CustomerListController controller;
    
    /** The title for this view */
    private LabelHeadingWidget lblTitle;
    
    /** the table that lists all of the customers */
    private TableWidget tblCustomers;
    
    /** The customized JTable model for the customer table */ 
    private TableWidgetModel model;
    
    /** The Text field used to filter the customers table */
    private TextWidget txtFilter;
    
    /** The panels that are clicked show/hide the other panels */
    private ActionPanel[] actionPanels;
    
    /** The panel that holds the customer table and filter field */
    private PanelWidget pnlCustomers;

    /** An array of the panels that are added to this view and paired with an actionPanel */
    private JPanel[] panels;
    
    /** The style for a balloon pop-up */
    private BalloonTipStyle edgedLook = new ToolTipBalloonStyle(Globals.WHITE, Globals.GRAY); //RoundedBalloonStyle(5, 5, Globals.YELLOW_LIGHT, Globals.GRAY);
    
    /** The balloon pop-up object */
    private BalloonTip balloonTip ;
	
    private static final int FIRST_COLUMN = 0;
    
    /**
     * Constructor 
     * 
     * @param controller - the controller object that mediates this view's
     * 					   interaction with the model
     */
	public CustomerListView(CustomerListController controller){
		super("pnlCustomerListView");
		logger.info("Initializing the Customer List View");
		this.controller = controller;
		setLayout(new GridBagLayout());
        setBackground(Globals.WHITE);
        initComponents();
	}
	
    /**
     * Toggles visibility of a panel
     * 
     * @param actionPanel
     */
    private void togglePanelVisibility(ActionPanel actionPanel) {
        int index = getPanelIndex(actionPanel);
        panels[index].setVisible(!panels[index].isShowing());
        actionPanel.getParent().validate();
    }
    
    /**
     * Returns the index of a panel
     * @param actionPanel
     * @return
     */
    private int getPanelIndex(ActionPanel actionPanel) {
        for(int i = 0; i < actionPanels.length; i++) {
            if(actionPanel == actionPanels[i]) {
                return i;
            }
        }
        return -1;
    }
    
	/**
     * Creates and setups all of the action panels
     */
    private void assembleActionPanels() {
        String[] headings = { "Customers List", "Time Allocation", "Address(es)", "Contact(s)" };
        actionPanels = new ActionPanel[headings.length];
        for(int j = 0; j < actionPanels.length; j++) {
            actionPanels[j] = new ActionPanel(headings[j], this);
        }
    }
    
    /**
     * Sets up all of the panels that contain controls
     */
    private void assemblePanels() {
        PanelWidget p3 = new PanelWidget("p3", new BorderLayout());
        JTextArea textArea = new JTextArea(8,12);
        textArea.setLineWrap(true);
        p3.add(new JScrollPane(textArea));
        PanelWidget p4 = new PanelWidget("p4", new GridBagLayout());
        panels = new PanelWidget[] { pnlCustomers, p3, p4 };
    }
    
	/**
     * Initializes the panel with all the necessary components
     */
	private void initComponents() {
		
	    pnlCustomers = new PanelWidget("pnlCustomers");
	    pnlCustomers.setLayout(new GridBagLayout());
	    pnlCustomers.setBackground(Globals.WHITE);
        assembleActionPanels();
        assemblePanels();
        
		lblTitle = new LabelHeadingWidget("lblTitle", "Customers List", LabelHeadingWidget.CENTER);
		tblCustomers = new TableWidget("tblCustomerList", populateTableModel());
		tblCustomers.setFocusable(true);
		setColumnsWidth();
		LabelWidget lblFilter = new LabelWidget("lblFilter", "Search ");
		txtFilter = new TextWidget("txtFilter");
		tblCustomers.enableFilter(txtFilter);
		
		ScrollPaneWidget scrllrTblCustomers = new ScrollPaneWidget("scrllrTblCustomers", tblCustomers);
		scrllrTblCustomers.setPreferredSize(new Dimension(800, 500));
		scrllrTblCustomers.setMinimumSize(new Dimension(800, 500));
		
		//This has to be called after the table object (tblCustomers) is instantiated.
		setupTablePopupMenu();
		
		pnlCustomers.add(lblTitle, Util.defineConstraint(1, 0, 0, 0, 2, 1, true, 18));
		
		pnlCustomers.add(lblFilter, Util.defineConstraint(0, 0, 0, 1, 1, 1, false, 18));
		
		pnlCustomers.add(txtFilter, Util.defineConstraint(1, 0, 1, 1, 1, 1, true, 18));
		pnlCustomers.add(scrllrTblCustomers, Util.defineConstraint(1,1,0,2,2,1,true,18));
		
		//Adds the first set of panels (Personal Info)
        add(actionPanels[0], Util.defineConstraint(1, 0, 0, 00, 1, 1, true, 18));
        actionPanels[0].toggleSelection();
        add(panels[0], Util.defineConstraint(1, 0, 0, 01, 1, 1, true, 18));
        
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
	
	/**
	 * It adds additional menu items to the AppPopupMenu that are only
	 * relevant for this view.
	 */
	private void setupTablePopupMenu(){
		JPopupMenu menu = tblCustomers.getPopupMenu();
		
		AppMenuItem delete = new AppMenuItem("Delete", Util.getImageIcon("../resources/delete.png"));
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        delete.setToolTipText("Deletes the selected row");
        
		AppMenuItem edit = new AppMenuItem("Edit", Util.getImageIcon("../resources/edit.png"));
        edit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        edit.setToolTipText("Edits the selected row");
        
        delete.setActionCommand("delete");
        delete.addActionListener(this);

        
        edit.setActionCommand("edit");
        edit.addActionListener(this);

        menu.add(delete);
		menu.add(edit);
	}
	
	/**
	 * Overrides the modelPropertyChange method defined in the AbstractViewPanel.
	 * This gets invoked by the propertyChange method defined in the AbstractController.
	 * It happens whenever the AbstractController detects changes in the properties of a
	 * data model for which this view is subscribed to receive notifications.
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		String propertyName = evt.getPropertyName();
		System.out.println("From CustomerListView: " + propertyName);
		if (propertyName.equals(CustomerController.ELEMENT_NAME_PROPERTY)){
            model.setValueAt(evt.getNewValue(), 1, 1);
        } 
	}

	/**
     * Overrides the update method from the Observer interface implemented by the AbstractViewPanel. 
     * Since this class (view) is added as an observer to the CustomerController class (Observable), 
     * then when the save method is successfully invoked in the CustomerController class, 
     * this method gets invoked.
     */
	@Override
	public void update(Observable observable, Object object) {
		if (object instanceof Customer) {
			tblCustomers.setModel(populateTableModel());
			tblCustomers.configure("tblCustomers");
			setColumnsWidth();
		}
	}

	/**
     * This method is implemented as a result of implementing 
     * the ActionListener class. This method is invoked whenever
     * any object that has added this class as ActionListener triggers
     * an action. For example, any time the content of a JTextField, 
     * JTextArea, etc. changes, this method is invoked.
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		
	    String action = e.getActionCommand();
	    
		if (action.equals("edit")) {
		    int selectedRow = tblCustomers.getRightClickedRow();
            Long modelRowId = (Long) tblCustomers.getValueAt(selectedRow, FIRST_COLUMN);
            Controllers.CUSTOMER_CONTROLLER.getOrCreateObject(modelRowId);
            PanelWidget newCustomer = new CustomerView(Controllers.CUSTOMER_CONTROLLER, modelRowId);
            /*
             * If there is already a balloon tip, then close it so the
             * new can be shown.
             */
            if (balloonTip != null && balloonTip.isVisible()) {
            	balloonTip.closeBalloon();
            }
            balloonTip = new BalloonTip(
            		txtFilter, 
            		newCustomer, 
            		edgedLook, 
            		Orientation.LEFT_ABOVE, 
            		AttachLocation.CENTER, 
            		40, 
            		20, 
            		true);
            balloonTip.setVisible(true);
		}
	}

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }

    /**
     * If an actionPanel is clicked, then toggle the visibility of
     * actionPanels and their associated panels.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        ActionPanel actionPanel = (ActionPanel)e.getSource();
        if (actionPanel.getTarget().contains(e.getPoint())) {
            actionPanel.toggleSelection();
            togglePanelVisibility(actionPanel);
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
    }
}
