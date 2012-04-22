package controller;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import model.Supplier;
import utilities.AppLogger;
import utilities.Util;

/**
* This is the controller for the Supplier model class
*
* @author Mahmood Khan
* @version 2012-04-13 1.0
*
*/
public class SupplierController extends AbstractController implements ControllerInterface{
    
    /** The persistence manager used to manage connection to the database */
    private PersistenceManager pm;

    /** The transaction to be associated with the persistence manager */
    private Transaction tx;
    
    /** Indicates whether a new object was created */
    private Integer newObject ;
    
    /** The logger object used to log messages */
    private static final Logger LOGGER = AppLogger.getAppLogger(SupplierController.class.getName());
    
    /** The supplier object instance that is to be changed */
    private Supplier supplier;

    public static final String ELEMENT_ID_PROPERTY = "Id";
    
    public static final String ELEMENT_NAME_PROPERTY = "Name";
    
    public static final String ELEMENT_PHONE_PROPERTY = "Phone";
    
    public static final String ELEMENT_EMAIL_PROPERTY = "Email";
    
    public static final String ELEMENT_ADDRESS_PROPERTY = "Address";
    
    public static final String ELEMENT_NOTES_PROPERTY = "Notes";
    
    /**
     * Constructor
     */
    public SupplierController(){
        super();
    }

    /** 
     * Begin the transaction.
     */
    @Override
    public void beginTransaction(){
        if (pm == null ||  pm.isClosed()|| tx == null ){
            pm = Persistence.getInstance().getPm();
            tx = pm.currentTransaction();
        }
        if (! tx.isActive()){
            tx.begin();
        }
    }

    /**
     * If the object exists, retrieve it or else create it.
     * 
     * @param id
     */
    @Override
    public Long getOrCreateObject(Long id){
    	if ( tx == null || !tx.isActive()){
    		beginTransaction();
    	}
        try { 
            supplier = pm.getObjectById(Supplier.class, id);
            newObject = 0;
        } catch(JDOObjectNotFoundException e) { 
            LOGGER.log(Level.INFO, "A supplier with ID=" + id 
                    + " does not exist; creating new supplier()"
                    + " | " + e.getMessage() + " | " + e.getCause());
            this.supplier = new Supplier();
            newObject = 1;
        }
        /*
         * Add this model to the models vector in the AbstractController 
         * so that a PropertyChangeListener can be registered for it.
         */
        addModel(supplier);
        return supplier.getId();
    }
    
    /**
     * Can access all of the getter methods in the Supplier class
     * 
     * @param propertyName - the name of the field
     * 
     * @return - the value returned a getter in Supplier class
     */
    public String getModelProperty(String propertyName){
    	try {
    		Method method = supplier.getClass().getMethod("get" + propertyName);
    		String value = method.invoke(supplier) + "";
    		return value.equals("null") ? null : value;
    	} catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    	return null;
    }
    
    /**
     * Calls the right method for the Supplier Object
     * 
     * @param propertyName - the name of the field whose value should change
     * @param newValue - the new value for the referred field name
     */
    @Override
    public void setModelProperty(String propertyName, Object newValue) {
        try {
            Method method = supplier.getClass().getMethod("set" + propertyName,
                    new Class[] { newValue.getClass() });
            Boolean success = (Boolean) method.invoke(supplier, newValue);
            if (success == false) {
                Util.showError(null, "Invalid value (" + newValue 
                        + ") for the field, " + propertyName, "Validation Check");
            }
        } catch (Exception e) {
            try {
            LOGGER.log(Level.SEVERE, "The method set" + propertyName + "()=" 
                    + newValue + " in " + supplier.getClass() 
                    + " failed |" + e.getMessage() + "|" + e.getCause());
            } catch (NullPointerException e1) {
                LOGGER.log(Level.FINE, e.getMessage() + "|" + e.getCause());
            }
        }
    }
    
    /**
     * Saves the object to the database
     * 
     * @param id
     * @return
     */
    @Override
    public boolean save(){
        if (newObject == 1){
        	supplier.setDateCreated(Util.getTimestamp());
            pm.makePersistent(supplier);
        } else {
        	supplier.setDateModified(Util.getTimestamp());
        }
        try {
            tx.commit();
        } catch (Exception e){
            LOGGER.log(Level.SEVERE, "Failed to comit transaction |" + e.getMessage());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
                LOGGER.log(Level.WARNING, "Rolled back transaction.");
                return false;
            }
            pm.close();
        }
        LOGGER.log(Level.FINE, "Setting off notifications to observers.");
        setChanged();
        notifyObservers(supplier);
        return true;
    }
    
    public void rollback(){
        if (tx.isActive()) {
            tx.rollback();
            LOGGER.log(Level.WARNING, "Rolled back transaction.");
        }
    }
    
    
}
