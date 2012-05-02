package controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import model.Currency;
import utilities.AppLogger;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-05-01 1.0
 *
 */
public class CurrencyController extends AbstractController{

    /** The persistence manager used to manage connection to the database */
    private PersistenceManager pm;
    
    /** The logger object used to log messages */
    private static final Logger logger = AppLogger.getAppLogger(CurrencyController.class.getName());
    
    /** The Currency object instance that is to be changed */
    private List<Currency> currencies;
    
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
	public ArrayList<Object[]> getCurrencies(String filter){
    	initializePm();
    	Query query = pm.newQuery(model.Currency.class);
    	query.setFilter(filter);
    	currencies = (List<Currency>) query.execute();
    	
    	ArrayList<Object[]>  data = new ArrayList<Object[]>();
    	
    	Iterator<Currency> currenciesIterator = currencies.iterator();
    	
    	while (currenciesIterator.hasNext()) {
    		Currency currency = currenciesIterator.next();
    		data.add(new Object[] {
    				currency.getId(),
    				currency.getShortName(),
    				currency.getLongName(),
    				(Date)currency.getDateCreated(),
    				(Timestamp)currency.getDateModified()
    				});
    	}
    	return data;
    }
}
