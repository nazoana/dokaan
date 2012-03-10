package controller;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import utilities.AppLogger;
import utilities.Util;

import model.Customer;

import controller.Persistence;

/**
* This is the controller for the Customer model class
*
* @author Mahmood Khan
* @version 2012-03-06 1.0
*
*/
public class CstmrCtrllr extends AbstractController implements ControllerInterface{
    
    /** The persistence manager used to manage connection to the database */
    private PersistenceManager pm;

    /** The transaction to be associated with the persistence manager */
    private Transaction tx;
    
    /** Indicates whether a new object was created */
    private boolean newObject ;
    
    /** The logger object used to log messages */
    private static final Logger logger = AppLogger.getAppLogger(CstmrCtrllr.class.getName());
    
    /** The customer object instance that is to be changed */
    private Customer customer;

    public static final String ELEMENT_ID_PROPERTY = "Id";
    
    public static final String ELEMENT_NAME_PROPERTY = "Name";
    
    public static final String ELEMENT_PHONE_PROPERTY = "Phone";
    
    public static final String ELEMENT_ADDRESS_PROPERTY = "Address";
    
    
    public CstmrCtrllr(){
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
    public void getOrCreateObject(Long id){
    	if ( tx != null && tx.isActive()){
    		return;
    	}
        beginTransaction();
        try { 
            customer = pm.getObjectById(Customer.class, id);
            newObject = false;
        } catch(JDOObjectNotFoundException e){ 
            logger.log(Level.INFO, "A Customer with ID=" + id 
                    + " does not exist; creating new Customer()"
                    + " | " + e.getMessage() + " | " + e.getCause());
            this.customer = new Customer();
            newObject = true;
        }
    }
    
    /**
     * Updates the right method for the Customer Object
     */
    @SuppressWarnings("unused")
    @Override
    public void setModelProperty(String propertyName, Object newValue) {
        try {
            Method method = customer.getClass().getMethod("set" + propertyName,
                    new Class[] { newValue.getClass() });
            Boolean success = (Boolean) method.invoke(customer, newValue);
            if (success = false) {
                Util.showError(null, "Invalid value (" + newValue 
                        + ") for the field, " + propertyName, "Validation Check Failed");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "The method set" + propertyName + "=" 
                    + newValue + " in " + customer.getClass() 
                    + " failed | " + e.getMessage() + " | " + e.getCause());
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
        if (newObject){
        	customer.setDateCreated(Util.getTimestamp());
            pm.makePersistent(customer);
        } else {
        	customer.setDateModified(Util.getTimestamp());
        }
        try {
            tx.commit();
        } catch (Exception e){
            logger.log(Level.SEVERE, "Failed to comit transaction for customer," 
                    + customer.getName() + ". "
                    + " |" + e.getMessage() + "|" + e.getCause());
        } finally {
            if (tx.isActive()) {
                tx.rollback();
                logger.log(Level.WARNING, "Rolled back transaction.");
                return false;
            }
            pm.close();
        }
        logger.log(Level.FINE, "Setting off notifications to observers.");
        setChanged();
        notifyObservers(customer);
        return true;
    }
    
    public void rollback(){
        if (tx.isActive()) {
            tx.rollback();
            logger.log(Level.WARNING, "Rolled back transaction.");
        }
    }
    
    
}
