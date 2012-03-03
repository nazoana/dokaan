package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
* This is an abstract class that serve as the super class
* for all model classes. This class provides the propertyChangeSupport
* mechanism for model classes. So observers are notified when a
* property of a model class changes.
*
* @author Mahmood Khan
* @version 2012-02-20 1.0
*
*/
public abstract class AbstractModel{
	
    /**
     * Convenience class that allow others to observe changes to the model properties
     */
    protected PropertyChangeSupport propertyChangeSupport;
    
    /**
     * Default constructor. Instantiates the PropertyChangeSupport class.
     */
    
    public AbstractModel() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }
    
    /**
     * Adds a property change listener to the observer list.
     * @param l The property change listener
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }
    
   /**
    * Removes a property change listener from the observer list.
    * @param l The property change listener
    */
   public void removePropertyChangeListener(PropertyChangeListener l) {
       propertyChangeSupport.removePropertyChangeListener(l);
   }
   
   
   /**
    * Fires an event to all registered listeners informing them that a property in
    * this model has changed.
    * @param propertyName The name of the property
    * @param oldValue The previous value of the property before the change
    * @param newValue The new property value after the change
    */
   protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
       propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
   }

}
