package view;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import utilities.AppLogger;
import utilities.Globals;
import utilities.Util;
import widgets.ButtonWidget;
import widgets.LabelHeadingWidget;
import widgets.LabelWidget;
import widgets.ScrollPaneWidget;
import widgets.TextAreaWidget;
import widgets.TextWidget;
import controller.CustomerController;

/**
* This is a view to edit and view a Customer object
*
* @author Mahmood Khan
* @version 2012-02-29 1.0
*
*/
public class CustomerView extends AbstractViewPanel implements ActionListener{

    /**
     * This has to do with serialization. It is here to
     * avoid a compiler warning.
     */
    private static final long serialVersionUID = 1L;

    /** The logger object used to log messages */
    private static final Logger logger = AppLogger.getAppLogger(CustomerView.class.getName());
    
    private CustomerController controller;
    
    private LabelHeadingWidget lblTitle;
    
    private LabelWidget lblCounter;
    
    private LabelWidget lblId;
    private TextWidget txtId;
    
    private LabelWidget lblName;
    private TextWidget txtName;
    
    private LabelWidget lblAddress;
    private TextAreaWidget txtAddress;
    
    private LabelWidget lblPhone;
    private TextWidget txtPhone;
    
    private ButtonWidget btnSave;
    
    private ButtonWidget btnCancel;
    
    /**
     * Constructor
     * 
     * @param controller
     */
    public CustomerView(CustomerController controller){
    	super();
        this.controller = controller;
        setLayout(new GridBagLayout());
        setBackground(Globals.WHITE_FOR_FG_HEADING_LABEL);
        initComponents();
    }
    
    /**
     * Initializes the panel with all the necessary components
     */
    private void initComponents(){
        
        lblTitle = new LabelHeadingWidget("lblTitle", "Customer View", LabelHeadingWidget.CENTER);
        
        lblCounter = new LabelWidget("lblCounter", " ");
        
        lblId = new LabelWidget("lblId", " Id ");
        txtId = new TextWidget("txtId", 20);
        //txtId.setEditable(false);
        
        lblName = new LabelWidget("lblName", " Name ");
        txtName = new TextWidget("txtName", 20);
        txtName.addActionListener(this);
        
        lblPhone = new LabelWidget("lblPhone", " Phone ");
        txtPhone = new TextWidget("txtPhone", 20);
        txtPhone.addActionListener(this);
        
        lblAddress = new LabelWidget("lblAddress", " Business Address ");
        txtAddress = new TextAreaWidget("txtAddress", 8, 24);
        txtAddress.addActionListener(this);
        txtAddress.setCounterLabel(lblCounter);
        ScrollPaneWidget scrlPnlAddress = new ScrollPaneWidget("scrlPnlAddress", txtAddress);
        
        btnSave = new ButtonWidget("btnSave", "Save", Util.getImageIcon("../resources/save.png"));
        btnSave.addActionListener(this);
        btnSave.setActionCommand("save");
        
        btnCancel = new ButtonWidget("btnCancel", "Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setActionCommand("cancel");
        
        add(lblTitle, Util.defineConstraint(1, 0, 0, 0, 2, 1, true, 11));

        add(lblId, Util.defineConstraint(0, 0, 0, 1, 1, 1, true, 17));
        add(txtId, Util.defineConstraint(1, 0, 1, 1, 1, 1, true, 17));
  
        add(lblName, Util.defineConstraint(0, 0, 0, 2, 1, 1, true, 17));
        add(txtName, Util.defineConstraint(1, 0, 1, 2, 1, 1, true, 17));
        
        add(lblPhone, Util.defineConstraint(0, 0, 0, 3, 1, 1, true, 17));
        add(txtPhone, Util.defineConstraint(1, 0, 1, 3, 1, 1, true, 17));
        
        add(lblAddress, Util.defineConstraint(0, 0, 0, 4, 1, 1, true, 17));
        add(scrlPnlAddress, Util.defineConstraint(1, 0, 1, 4, 1, 1, true, 17));
        add(lblCounter, Util.defineConstraint(0, 0, 1, 5, 1, 1, false, 13));

        add(btnCancel, Util.defineConstraint(0, 0, 0, 6, 1, 1, false, 17));
        add(btnSave,Util.defineConstraint(0, 0, 1, 6, 1, 1, false, 13));
    }
    
    
    /**
     * This method is invoked by the CustomerController object
     * whenever the Customer object changes.
     */
    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        lblCounter.setText("Object has changed");
        if (evt.getPropertyName().equals(CustomerController.ELEMENT_NAME_PROPERTY)){
            txtName.setText((String) evt.getNewValue());
        }
    }

    /**
     * This method is implemented as a result of implementing 
     * the ActionListener class. This method is invoked whenever
     * any object that has added this class as ActionListener triggers
     * an action
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
    	String id = txtId.getText() ;
    	if (id == null | id.equals("null") || id.length() == 0){
    		id = "-1";
    	}
        controller.getOrCreateObject(new Long(id));
        Object sourceObject = evt.getSource();
        if (sourceObject instanceof TextWidget){
    		if (((TextWidget)sourceObject).getName().equals("txtName")){
    			controller.setModelProperty(CustomerController.ELEMENT_NAME_PROPERTY, txtName.getText());
    		}
    		else if (((TextWidget)sourceObject).getName().equals("txtPhone")){
    			controller.setModelProperty(CustomerController.ELEMENT_PHONE_PROPERTY, txtPhone.getText());
    		}
    	}  else if (sourceObject instanceof TextAreaWidget){
    		if ( ((TextAreaWidget)sourceObject).getName().equals("txtAddress")){
    			controller.setModelProperty(CustomerController.ELEMENT_ADDRESS_PROPERTY, txtAddress.getText());
    		}
    	} else if (sourceObject instanceof ButtonWidget){
    		if (evt.getActionCommand().equals("save")){
    			logger.log(Level.INFO, "Save button pressed in Customer View");
                controller.setModelProperty(CustomerController.ELEMENT_ADDRESS_PROPERTY, txtAddress.getText());
                controller.save();
    		}
    		else if (evt.getActionCommand().equals("cancel")){
    			logger.log(Level.INFO, "Cancel button pressed in Customer View");
                controller.rollback();
                lblCounter.setText("");
    		}
    	}
    }

    /**
     * Overrides the update method from the Observer object
     */
	@Override
	public void update(Observable observable, Object object) {
		// TODO Auto-generated method stub
		System.out.println("observer: view should get updated");
	}
}
