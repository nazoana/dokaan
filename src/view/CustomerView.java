package view;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.utils.ToolTipUtils;
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
    
    private LabelWidget lblId;
    private TextWidget txtId;
    
    private LabelWidget lblName;
    private TextWidget txtName;
    
    private LabelWidget lblEmail;
    private TextWidget txtEmail;
    
    private LabelWidget lblAddress;
    private TextAreaWidget txtAddress;
    
    private LabelWidget lblNotes;
    private TextAreaWidget txtNotes;
    
    private LabelWidget lblPhone;
    private TextWidget txtPhone;
    
    private ButtonWidget btnSave;
    
    private ButtonWidget btnCancel;
    
    private BalloonTip balloonTip;
    
    /**
     * Constructor
     * 
     * @param controller
     */
    public CustomerView(CustomerController controller){
    	super("pnlCustomerView");
        this.controller = controller;
        setLayout(new GridBagLayout());
        setBackground(Globals.WHITE);
        initComponents();
    }
    
    /**
     * Initializes the panel with all the necessary components
     */
    private void initComponents(){
        
        lblTitle = new LabelHeadingWidget("lblTitle", "Customer View", LabelHeadingWidget.CENTER);
        
        LabelWidget lblAddressCounter = new LabelWidget("lblAddressCounter", " ");
        LabelWidget lblNotesCounter = new LabelWidget("lblNotesCounter", " ");
        
        lblId = new LabelWidget("lblId", " Id ");
        txtId = new TextWidget("txtId", 20);
        txtId.setEditable(false);
        //txtId.setToolTipText("This field is locked for editing");
        balloonTip = new BalloonTip(txtId, "This is locked for editing");
        ToolTipUtils.balloonToToolTip(balloonTip, 500, 10000);
        
        lblName = new LabelWidget("lblName", " Name ");
        txtName = new TextWidget("txtName", 20);
        txtName.addActionListener(this);
        
        lblEmail = new LabelWidget("lblEmail", " Email");
        txtEmail = new TextWidget("txtEmail", 20);
        txtEmail.addActionListener(this);
        
        lblPhone = new LabelWidget("lblPhone", " Phone ");
        txtPhone = new TextWidget("txtPhone", 20);
        txtPhone.addActionListener(this);
        
        lblAddress = new LabelWidget("lblAddress", " Address ");
        txtAddress = new TextAreaWidget("txtAddress", 2, 24);
        txtAddress.addActionListener(this);
        txtAddress.setCounterLabel(lblAddressCounter);
        ScrollPaneWidget scrAddress = new ScrollPaneWidget("scrlPnlAddress", txtAddress);
        scrAddress.setPreferredSize(new Dimension(100, 60));
        scrAddress.setMinimumSize(new Dimension(100, 60));
        
        lblNotes = new LabelWidget("lblNotes", " Notes ");
        txtNotes = new TextAreaWidget("txtNotes", 2, 24);
        txtNotes.addActionListener(this);
        txtNotes.setCounterLabel(lblNotesCounter);
        ScrollPaneWidget scrNotes = new ScrollPaneWidget("scrlPnlNotes", txtNotes);
        scrNotes.setPreferredSize(new Dimension(200, 60));
        scrNotes.setMinimumSize(new Dimension(200, 60));
        
        btnSave = new ButtonWidget("btnSave", "Save", Util.getImageIcon("../resources/save.png"));
        btnSave.addActionListener(this);
        btnSave.setActionCommand("save");
        
        btnCancel = new ButtonWidget("btnCancel", "Cancel", Util.getImageIcon("../resources/cancel.png"));
        btnCancel.addActionListener(this);
        btnCancel.setActionCommand("cancel");
        
        add(lblTitle, Util.defineConstraint(1, 0, 0, 0, 4, 1, true, 11));

        add(lblId, Util.defineConstraint(0.0, 0, 0, 1, 1, 1, true, 17));
        add(txtId, Util.defineConstraint(0.5, 0, 1, 1, 1, 1, true, 17));
  
        add(lblName, Util.defineConstraint(0.0, 0, 2, 1, 1, 1, true, 17));
        add(txtName, Util.defineConstraint(0.5, 0, 3, 1, 1, 1, true, 17));
        
        add(lblEmail, Util.defineConstraint(0.0, 0, 0, 3, 1, 1, true, 17));
        add(txtEmail, Util.defineConstraint(0.5, 0, 1, 3, 1, 1, true, 17));
        
        add(lblPhone, Util.defineConstraint(0.0, 0, 2, 3, 1, 1, true, 17));
        add(txtPhone, Util.defineConstraint(0.5, 0, 3, 3, 1, 1, true, 17));
        
        add(lblAddress, Util.defineConstraint(0.0, 0, 0, 5, 1, 1, true, 17));
        add(scrAddress, Util.defineConstraint(0.5, 0, 1, 5, 1, 1, true, 17));
        add(lblAddressCounter, Util.defineConstraint(0, 0, 1, 6, 1, 1, false, 17));
        
        add(lblNotes, Util.defineConstraint(0.0, 0, 2, 5, 1, 1, true, 17));
        add(scrNotes, Util.defineConstraint(0.5, 0, 3, 5, 1, 1, true, 17));
        add(lblNotesCounter, Util.defineConstraint(0, 0, 3, 6, 1, 1, false, 17));

        add(btnSave,Util.defineConstraint(0, 0, 0, 9, 1, 1, false, 17));
        add(btnCancel, Util.defineConstraint(0, 0, 3, 9, 1, 1, false, 13));
    }
    
    
    /**
     * This method is invoked by the CustomerController object
     * whenever the Customer object changes.
     */
    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
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
        	
        	// If it is a text area
        	if (((TextWidget) sourceObject).getText().equals("txtArea")){
        		if ( ((TextWidget)sourceObject).getName().equals("txtAddressActionListener")){
        			controller.setModelProperty(CustomerController.ELEMENT_ADDRESS_PROPERTY, txtAddress.getText());
        		} else if ( ((TextWidget)sourceObject).getName().equals("txtNotesActionListener")){
        			controller.setModelProperty(CustomerController.ELEMENT_NOTES_PROPERTY, txtNotes.getText());
        		}
        	// otherwise it is text field
        	} else {
	    		if (((TextWidget)sourceObject).getName().equals("txtName")){
	    			controller.setModelProperty(CustomerController.ELEMENT_NAME_PROPERTY, txtName.getText());
	    		} else if (((TextWidget)sourceObject).getName().equals("txtPhone")){
	    			controller.setModelProperty(CustomerController.ELEMENT_PHONE_PROPERTY, txtPhone.getText());
	    		}  else if (((TextWidget)sourceObject).getName().equals("txtEmail")){
	    			controller.setModelProperty(CustomerController.ELEMENT_EMAIL_PROPERTY, txtEmail.getText());
	    		}
        	}
    	}
        else if (sourceObject instanceof ButtonWidget){
    		if (evt.getActionCommand().equals("save")){
    			logger.log(Level.INFO, "Save button pressed in Customer View");
                controller.save();
    		} else if (evt.getActionCommand().equals("cancel")){
    			logger.log(Level.INFO, "Cancel button pressed in Customer View");
                controller.rollback();
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
