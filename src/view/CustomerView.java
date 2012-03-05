package view;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.CstmrCtrllr;

import utilities.AppLogger;
import utilities.Util;
import utilities.Globals;

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
    
    private CstmrCtrllr ctrllr;
    
    private JLabel lblViewHeading;
    
    private JLabel lblStatus;
    
    private JLabel lblId;
    private JTextField txtId;
    
    private JLabel lblName;
    private JTextField txtName;
    
    private JLabel lblAddress;
    private JTextField txtAddress;
    
    private JLabel lblPhone;
    private JTextField txtPhone;
    
    private JButton btnSave;
    
    private JButton btnCancel;
    
    /**
     * Constructor
     * 
     * @param customerController
     */
    public CustomerView(CstmrCtrllr customerController){
        this.ctrllr = customerController;
        setLayout(new GridBagLayout());
        setBackground(Globals.WHITE);
        initComponents();
    }
    
    /**
     * Initializes the panel with all the necessary components
     */
    private void initComponents(){
        
        lblViewHeading = new JLabel("Customer View");
        
        lblStatus = new JLabel("");
        
        lblId = new JLabel("Id: ");
        txtId = new JTextField(20);
        
        lblName = new JLabel("Name: ");
        txtName = new JTextField(20);
        txtName.addActionListener(this);
        txtName.setActionCommand("nameChange");
        
        lblPhone = new JLabel("phone: ");
        txtPhone = new JTextField(20);
        txtPhone.addActionListener(this);
        txtPhone.setActionCommand("phoneChange");
        
        lblAddress = new JLabel("Address: ");
        txtAddress = new JTextField(80);
        txtAddress.addActionListener(this);
        txtAddress.setActionCommand("addressChange");
        
        btnSave = new JButton("Save");
        btnSave.addActionListener(this);
        btnSave.setActionCommand("save");
        
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setActionCommand("cancel");
        
        add(lblViewHeading, Util.defineConstraint(1, 0, 0, 0, 2, 1, false, 11));

        add(lblId, Util.defineConstraint(0, 0, 0, 1, 1, 1, true, 17));
        add(txtId, Util.defineConstraint(1, 0, 1, 1, 1, 1, true, 17));
  
        add(lblName, Util.defineConstraint(0, 0, 0, 2, 1, 1, true, 17));
        add(txtName, Util.defineConstraint(1, 0, 1, 2, 1, 1, true, 17));
        
        add(lblPhone, Util.defineConstraint(0, 0, 0, 3, 1, 1, true, 17));
        add(txtPhone, Util.defineConstraint(1, 0, 1, 3, 1, 1, true, 17));
        
        add(lblAddress, Util.defineConstraint(0, 0, 0, 4, 1, 1, true, 17));
        add(txtAddress, Util.defineConstraint(1, 0, 1, 4, 1, 1, true, 17));

        add(btnSave, Util.defineConstraint(0, 0, 0, 5, 1, 1, false, 13));
        add(btnCancel,Util.defineConstraint(0, 0, 1, 5, 1, 1, false, 13));
    }
    
    
    /**
     * This method is invoked by the CustomerController object
     * whenever the Customer object changes.
     */
    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        lblStatus.setText("Object has changed");
        if (evt.getPropertyName().equals(CstmrCtrllr.ELEMENT_NAME_PROPERTY)){
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
        ctrllr.getOrCreateObject(new Integer(txtId.getText()));
        if (evt.getActionCommand().equals("save")){
            logger.log(Level.INFO, "Save button pressed in Customer View");
            ctrllr.setModelProperty(CstmrCtrllr.ELEMENT_NAME_PROPERTY, txtName.getText());
            ctrllr.setModelProperty(CstmrCtrllr.ELEMENT_PHONE_PROPERTY, txtPhone.getText());
            ctrllr.setModelProperty(CstmrCtrllr.ELEMENT_ADDRESS_PROPERTY, txtAddress.getText());
            ctrllr.save();
        } else if (evt.getActionCommand().equals("cancel")){
            logger.log(Level.INFO, "Cancel button pressed in Customer View");
            ctrllr.rollback();
            lblStatus.setText("");
        } else if (evt.getActionCommand().equals("nameChange")) {
            ctrllr.setModelProperty(CstmrCtrllr.ELEMENT_NAME_PROPERTY, txtName.getText());
        } else if (evt.getActionCommand().equals("phoneChange")){
            ctrllr.setModelProperty(CstmrCtrllr.ELEMENT_PHONE_PROPERTY, txtPhone.getText());
        } else if (evt.getActionCommand().equals("addressChange")){
            ctrllr.setModelProperty(CstmrCtrllr.ELEMENT_ADDRESS_PROPERTY, txtAddress.getText());
        }
    }

}
