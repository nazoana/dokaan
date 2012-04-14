package view;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.util.Observable;

import utilities.Globals;
import utilities.Util;
import widgets.LabelHeadingWidget;
import widgets.LabelWidget;

/**
 * This view is supposed to show a high-level overview/summary of 
 * dokaan's (business') operations. 
 * 
 * @author Mahmood Khan
 * @version 2012-03-27 1.0
 *
 */
public class Overview extends AbstractViewPanel{

	/**
	 * This has to do with serialization; it is not important, but is placed
	 * here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;
	
	/** The headign used for this view */
    private LabelHeadingWidget lblTitle;
    

    /**
     * Constructor
     */
	public Overview() {
		super("pnlOverview");
		setLayout(new GridBagLayout());
        setBackground(Globals.WHITE);
        initComponents();
	}
	
	/**
	 * Initialize components in this view
	 */
	private void initComponents() {
		lblTitle = new LabelHeadingWidget("lblTitle", "Overview", LabelHeadingWidget.CENTER);
		LabelWidget lblFiller = new LabelWidget("lblFiller", "Place Holder");
		lblFiller.setPreferredSize(new Dimension(1000, 640));
		add(lblTitle, Util.defineConstraint(1, 1, 0, 0, 1, 1, true, 18));
		add(lblFiller, Util.defineConstraint(1, 1, 0, 1, 1, 1, true, 18));
	}

	/**
	 * This method gets invoked anything the Observable object such as the CustomerController,
	 * OrderController, etc sets off notification to the subscribed views that the underlying 
	 * model has changed; therefore, the views should manage updating themselves
	 */
	@Override
	public void update(Observable observable, Object object) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * This method is invoked whenever a model (for which it is registered to listen) 
	 * property changes
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

}
