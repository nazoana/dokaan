package controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import model.Customer;
import utilities.AppLogger;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-11 1.0
 *
 */
public class CustomerListController extends AbstractController{

    /** The persistence manager used to manage connection to the database */
    private PersistenceManager pm;
    
    /** The logger object used to log messages */
    private static final Logger logger = AppLogger.getAppLogger(CustomerListController.class.getName());
    
    /** The customer object instance that is to be changed */
    private List<Customer> customers;
    
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
	public ArrayList<Object[]> getCustomers(String filter){
    	initializePm();
    	Query query = pm.newQuery(model.Customer.class);
    	query.setFilter(filter);
    	customers = (List<Customer>) query.execute();
    	
    	ArrayList<Object[]>  data = new ArrayList<Object[]>();
    	
    	Iterator<Customer> customersIterator = customers.iterator();
    	
    	while (customersIterator.hasNext()) {
    		Customer customer = customersIterator.next();
    		data.add(new Object[] {
    				customer.getId(), 
    				customer.getName(), 
    				customer.getAddress(),
    				customer.getPhone(),
    				(Date)customer.getDateCreated(),
    				(Timestamp)customer.getDateModified()
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
