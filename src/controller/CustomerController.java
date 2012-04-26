package controller;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import model.Customer;
import utilities.AppLogger;
import utilities.Util;

/**
* This is the controller for the Customer model class
*
* @author Mahmood Khan
* @version 2012-03-06 1.0
*
*/
public class CustomerController extends AbstractController implements ControllerInterface{
    
    /** The persistence manager used to manage connection to the database */
    private PersistenceManager pm;

    /** The transaction to be associated with the persistence manager */
    private Transaction tx;
    
    /** Indicates whether a new object was created */
    private Integer newObject ;
    
    /** The logger object used to log messages */
    private static final Logger LOGGER = AppLogger.getAppLogger(CustomerController.class.getName());
    
    /** The customer object instance that is to be changed */
    private Customer customer;

    /** the id property in the customer model */
    public static final String ELEMENT_ID_PROPERTY = "Id";
    
    /** The name property in the customer model */
    public static final String ELEMENT_NAME_PROPERTY = "Name";
    
    /** The phone property in the customer model */
    public static final String ELEMENT_PHONE_PROPERTY = "Phone";
    
    /** The email property in the customer model */
    public static final String ELEMENT_EMAIL_PROPERTY = "Email";
    
    /** The address property in the customer model */
    public static final String ELEMENT_ADDRESS_PROPERTY = "Address";
    
    public static final String ELEMENT_NOTES_PROPERTY = "Notes";
    
    /**
     * Constructor
     */
    public CustomerController(){
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
            customer = pm.getObjectById(Customer.class, id);
            newObject = 0;
        } catch(JDOObjectNotFoundException e) { 
            LOGGER.log(Level.INFO, "A Customer with ID=" + id 
                    + " does not exist; creating new Customer()"
                    + " | " + e.getMessage() + " | " + e.getCause());
            customer = new Customer();
            newObject = 1;
        }
        
        /*
         * Add this model to the models vector in the AbstractController 
         * so that a PropertyChangeListener can be registered for it.
         */
        addModel(customer);
        return customer.getId();
    }
    
    /**
     * Can access all of the getter methods in the Customer class
     * 
     * @param propertyName - the name of the field
     * 
     * @return - the value returned a getter in Customer class
     */
    public String getModelProperty(String propertyName){
    	try {
    		Method method = customer.getClass().getMethod("get" + propertyName);
    		String value = method.invoke(customer) + "";
    		return value.equals("null") ? "" : value;
    	} catch (Exception e) {
            LOGGER.log(Level.SEVERE, "The method get" + propertyName + "() failed.");
        }
    	return null;
    }
    
    /**
     * Calls the right method for the Customer Object
     * 
     * @param propertyName - the name of the field whose value should change
     * @param newValue - the new value for the referred field name
     */
    @Override
    public void setModelProperty(String propertyName, Object newValue) {
        try {
            Method method = customer.getClass().getMethod("set" + propertyName,
                    new Class[] { newValue.getClass() });
            Boolean success = (Boolean) method.invoke(customer, newValue);
            if (success == false) {
                Util.showError(null, "Invalid value (" + newValue 
                        + ") for the field, " + propertyName, "Validation Check Failed");
            }
        } catch (Exception e) {
            try {
                LOGGER.log(Level.SEVERE, "The method set" + propertyName + "()=" 
                        + newValue + " in " + customer.getClass() 
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
        	customer.setDateCreated(Util.getTimestamp());
            pm.makePersistent(customer);
            newObject = 0;
        } else {
        	customer.setDateModified(Util.getTimestamp());
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
        notifyObservers(customer);
        return true;
    }
    
    /**
     * Rolls back the active transaction.
     */
    public void rollback(){
        if (tx.isActive()) {
            tx.rollback();
            LOGGER.log(Level.WARNING, "Rolled back transaction.");
        }
    }
    
    
}
