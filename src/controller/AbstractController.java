package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Observable;

import model.AbstractModel;
import view.AbstractViewPanel;

/**
* This is an abstract class that serves as the super class for all controllers
*
* @author Mahmood Khan
* @version 2012-02-20 1.0
*
*/
public abstract class AbstractController extends Observable implements PropertyChangeListener{
	
    /** Vectors that hold a list of the registered models and views for this controller. */
    private ArrayList<AbstractViewPanel> registeredViews;
    private ArrayList<AbstractModel> registeredModels;
    
    
    /** Creates a new instance of Controller */
    public AbstractController() {
        registeredViews = new ArrayList<AbstractViewPanel>();
        registeredModels = new ArrayList<AbstractModel>();
    }
    
    /**
     * Binds a model to this controller. Once added, the controller will listen for all 
     * model property changes and propagate them on to registered views. In addition,
     * it is also responsible for resetting the model properties when a view changes
     * state.
     * @param model The model to be added
     */
    public void addModel(AbstractModel model) {
        registeredModels.add(model);
        model.addPropertyChangeListener(this);
    }

    /**
     * Unbinds a model from this controller.
     * @param model The model to be removed
     */
    public void removeModel(AbstractModel model) {
        registeredModels.remove(model);
        model.removePropertyChangeListener(this);
    }
    
    
    /**
     * Binds a view to this controller. The controller will propogate all model property
     * changes to each view for consideration.
     * @param view The view to be added
     */
    public void addView(AbstractViewPanel view) {
        registeredViews.add(view);
        addObserver(view);
    }

    /**
     * Unbinds a view from this controller.
     * @param view The view to be removed
     */
    public void removeView(AbstractViewPanel view) {
        registeredViews.remove(view);
    }

    
    /**
     * This method is used to implement the PropertyChangeListener interface. Any model
     * changes will be sent to this controller through the use of this method.
     * In other words, it is used to observe property changes from registered models and 
     * propagate them on to all the registered views.
     * 
     * @param evt An object that describes the model's property change.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        for (AbstractViewPanel view: registeredViews) {
            view.modelPropertyChange(evt);
        }
    }
}
