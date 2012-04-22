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
import net.java.balloontip.BalloonTip.AttachLocation;
import net.java.balloontip.BalloonTip.Orientation;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.RoundedBalloonStyle;
import net.java.balloontip.utils.ToolTipUtils;
import utilities.AppLogger;
import utilities.Globals;
import utilities.Util;
import widgets.ButtonWidget;
import widgets.LabelBorderlessWidget;
import widgets.LabelHeadingWidget;
import widgets.LabelWidget;
import widgets.ScrollPaneWidget;
import widgets.TextAreaWidget;
import widgets.TextWidget;
import controller.SupplierController;

/**
* This is a view to edit and view a Supplier object. It implements the ActionListener
* because as the content of a JTextField changes, it creates an action, which are caught
* to notify the controller, which in turn notify all the other views of the change.
*
* @author Mahmood Khan
* @version 2012-02-29 1.0
*
*/
public class SupplierView extends AbstractViewPanel implements ActionListener{

    /**
     * This has to do with serialization. It is here to
     * avoid a compiler warning.
     */
    private static final long serialVersionUID = 1L;

    /** The logger object used to log messages */
    private static final Logger LOGGER = AppLogger.getAppLogger(SupplierView.class.getName());
    
    private SupplierController controller;
    
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
    
    private ButtonWidget btnReset;
    
    private BalloonTip balloonTip;
    
    /** This is a style for the balloon tool-tip object */
    private static final BalloonTipStyle EDGED_LOOK = new RoundedBalloonStyle(5, 5, Globals.YELLOW_LIGHT, Globals.GRAY);
       
    /**
     * Constructor
     * 
     * @param controller - the controller responsible for mediating 
     * 					   between the data model and this view
     */
    public SupplierView(SupplierController supplier, Long id){
    	super("pnlSupplierView");
        this.controller = supplier;
        setLayout(new GridBagLayout());
        setBackground(Globals.WHITE);
        initComponents();
        if (id != -1L){
            populate(id);
        }
    }

    
    
    /**
     * Initializes the panel with all the necessary components
     */
    private void initComponents(){        
        
        lblTitle = new LabelHeadingWidget("lblTitle", "Supplier View", LabelHeadingWidget.CENTER);
        
        LabelWidget lblAddressCounter = new LabelWidget("lblAddressCounter", " ");
        LabelWidget lblNotesCounter = new LabelWidget("lblNotesCounter", " ");
        
        lblId = new LabelWidget("lblId", " Id ");
        txtId = new TextWidget("txtId", 20);
        txtId.setEditable(false);
        txtId.setEnabled(false);
        
        balloonTip = new BalloonTip(txtId, 
        		new LabelBorderlessWidget("lblTxtId", "locked for editing"), 
        		EDGED_LOOK, 
        		Orientation.LEFT_ABOVE, 
        		AttachLocation.CENTER, 
        		40, 20, false);
        ToolTipUtils.balloonToToolTip(balloonTip, 100, 10000);
        
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
        
        btnReset = new ButtonWidget("btnReset", "Reset", Util.getImageIcon("../resources/cancel.png"));
        btnReset.addActionListener(this);
        btnReset.setActionCommand("cancel");
        
        add(lblTitle, Util.defineConstraint(1, 0, 0, 0, 4, 1, true, 11));

        add(lblId, Util.defineConstraint(0.0, 0, 0, 1, 1, 1, true, 18));
        add(txtId, Util.defineConstraint(0.5, 0, 1, 1, 1, 1, true, 18));
  
        add(lblName, Util.defineConstraint(0.0, 0, 0, 2, 1, 1, true, 18));
        add(txtName, Util.defineConstraint(0.5, 0, 1, 2, 1, 1, true, 18));
        
        add(lblEmail, Util.defineConstraint(0.0, 0, 0, 3, 1, 1, true, 18));
        add(txtEmail, Util.defineConstraint(0.5, 0, 1, 3, 1, 1, true, 18));
        
        add(lblPhone, Util.defineConstraint(0.0, 0, 0, 4, 1, 1, true, 18));
        add(txtPhone, Util.defineConstraint(0.5, 0, 1, 4, 1, 1, true, 18));
        
        add(lblAddress, Util.defineConstraint(0.0, 0, 0, 5, 1, 1, true, 18));
        add(scrAddress, Util.defineConstraint(0.5, 0, 1, 5, 1, 1, true, 18));
        add(lblAddressCounter, Util.defineConstraint(0, 0, 1, 6, 1, 1, false, 18));
        
        add(lblNotes, Util.defineConstraint(0.0, 0, 0, 7, 1, 1, true, 18));
        add(scrNotes, Util.defineConstraint(0.5, 0, 1, 7, 1, 1, true, 18));
        add(lblNotesCounter, Util.defineConstraint(0, 0, 1, 8, 1, 1, false, 18));

        add(btnSave,   Util.defineConstraint(0, 0, 0, 9, 1, 1, false, 18));
        add(btnReset, Util.defineConstraint(0, 0, 1, 9, 1, 1, false, 12));
    }
    
    /**
     * Populates the fields on the screen with data
     * 
     * @param id - the id of the supplier whose data is to be populated on the form fields
     */
    private void populate(Long id){
        controller.getOrCreateObject(id);
    	txtId.setText(controller.getModelProperty(SupplierController.ELEMENT_ID_PROPERTY));
    	txtName.setText(controller.getModelProperty(SupplierController.ELEMENT_NAME_PROPERTY));
    	txtEmail.setText(controller.getModelProperty(SupplierController.ELEMENT_EMAIL_PROPERTY));
    	txtPhone.setText(controller.getModelProperty(SupplierController.ELEMENT_PHONE_PROPERTY));
    	txtAddress.setText(controller.getModelProperty(SupplierController.ELEMENT_ADDRESS_PROPERTY));
    	txtNotes.setText(controller.getModelProperty(SupplierController.ELEMENT_NOTES_PROPERTY));
    }
    
    /**
     * Overrides the modelPropertyChange method defined in the AbstractViewPanel.
	 * This gets invoked by the propertyChange method defined in the AbstractController.
	 * It happens whenever the AbstractController detects changes in the properties of a
	 * data model for which this view is subscribed to receive notifications.
	 * 
	 * This method is invoked by the SupplierController object
     * whenever the Supplier object changes.
     */
    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(SupplierController.ELEMENT_NAME_PROPERTY)){
            txtName.setText((String) evt.getNewValue());
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
    public void actionPerformed(ActionEvent evt) {
    	
        Object sourceObject = evt.getSource();
        if (sourceObject instanceof TextWidget){
        	
        	// If it is a text area
        	if (((TextWidget) sourceObject).getText().equals("txtArea")){
        		if ( ((TextWidget)sourceObject).getName().equals("txtAddressActionListener")){
        		    controller.setModelProperty(SupplierController.ELEMENT_ADDRESS_PROPERTY, txtAddress.getText());
        		} else if ( ((TextWidget)sourceObject).getName().equals("txtNotesActionListener")){
        		    controller.setModelProperty(SupplierController.ELEMENT_NOTES_PROPERTY, txtNotes.getText());
        		}
        	// otherwise it is text field
        	} else {
	    		if (((TextWidget)sourceObject).getName().equals("txtName")){
	    		    controller.setModelProperty(SupplierController.ELEMENT_NAME_PROPERTY, txtName.getText());
	    		} else if (((TextWidget)sourceObject).getName().equals("txtPhone")){
	    		    controller.setModelProperty(SupplierController.ELEMENT_PHONE_PROPERTY, txtPhone.getText());
	    		}  else if (((TextWidget)sourceObject).getName().equals("txtEmail")){
	    		    controller.setModelProperty(SupplierController.ELEMENT_EMAIL_PROPERTY, txtEmail.getText());
	    		}
        	}
    	}
        else if (sourceObject instanceof ButtonWidget){
    		if (evt.getActionCommand().equals("save")){
    			LOGGER.log(Level.INFO, "Save button pressed in Supplier View");
    			controller.save();
                populate(Long.parseLong(controller.getModelProperty(SupplierController.ELEMENT_ID_PROPERTY)));
    		} else if (evt.getActionCommand().equals("cancel")){
    			LOGGER.log(Level.INFO, "Cancel button pressed in Supplier View");
    			controller.rollback();
    		}
    	}
    }

    /**
     * Overrides the update method from the Observer interface implemented by the AbstractViewPanel. 
     * Since this class (view) is added as an observer to the SupplierController class (Observable), 
     * then when the save method is successfully invoked in the SupplierController class, 
     * this method gets invoked.
     */
	@Override
	public void update(Observable observable, Object object) {
		// TODO Auto-generated method stub
		System.out.println("SupplierView observer: view should get updated");
	}
	
}
