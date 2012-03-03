package controller;

/**
 * This interface provides a consistent way for all controller
 * classes to have similar functionality and implementation
 * 
 * @author Mahmood Khan
 * @version 2012-02-20 1.0
 *
 */
public interface ControllerInterface {
    
    // STEP:1: Start the transaction
    void beginTransaction();
    
    // STEP:2: Relate the Java Object to the database
    void getOrCreateObject(String id);
    
    // STEP:3: Update/Populate object's properties
    // The implementing class is supposed to implement
    // this part
    
    
    //STEP 4: Update/Create the object in the database
    boolean save();
    
    void rollback();
    
    void setModelProperty(String propertyName, Object newValue);
}
