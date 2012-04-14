package controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import model.Supplier;
import utilities.AppLogger;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-04-13 1.0
 *
 */
public class SupplierListController extends AbstractController{

    /** The persistence manager used to manage connection to the database */
    private PersistenceManager pm;
    
    /** The logger object used to log messages */
    private static final Logger logger = AppLogger.getAppLogger(SupplierListController.class.getName());
    
    /** The supplier object instance that is to be changed */
    private List<Supplier> suppliers;
    
    /** 
     * Begin the transaction.
     */
    private void initializePm(){
        if (pm == null ||  pm.isClosed()){
            pm = Persistence.getInstance().getPm();
            logger.info("Initialized PersistenceManager (pm) object ");
        }
    }
    
    @SuppressWarnings("unchecked")
	public ArrayList<Object[]> getSuppliers(String filter){
    	initializePm();
    	Query query = pm.newQuery(model.Supplier.class);
    	query.setFilter(filter);
    	suppliers = (List<Supplier>) query.execute();
    	
    	ArrayList<Object[]>  data = new ArrayList<Object[]>();
    	
    	Iterator<Supplier> suppliersIterator = suppliers.iterator();
    	
    	while (suppliersIterator.hasNext()) {
    		Supplier supplier = suppliersIterator.next();
    		data.add(new Object[] {
    				supplier.getId(), 
    				supplier.getName(), 
    				supplier.getAddress(),
    				supplier.getPhone(),
    				(Date)supplier.getDateCreated(),
    				(Timestamp)supplier.getDateModified()
    				});
    	}
    	
    	/**
    	 * Query query = pm.newQuery(MyClass.class);
		 * query.setFilter("field2 < threshold");
		 * query.declareImports("import java.util.Date");
		 * query.declareParameters("Date threshold");
		 * query.setOrdering("field1 ascending");
		 * List results = (List)query.execute(my_threshold);
    	 */
    	return data;
    }
    /*
    public static void main(String[] args) {
    	new CustomerListController().query("name == \"Inayat Khan\" && id == 2");
	}
	*/
}
