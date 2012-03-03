package controller;


import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import model.Customer;
import model.Order;
import model.Product;
import model.ProductOrder;
import model.Supplier;

import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;
import org.datanucleus.store.schema.SchemaAwareStoreManager;

import utilities.*;

/**
* This class is mainly to setup the persistence layer i.e.
* creating the database, creating the schema, creating the
* JDO persistence manager factory as well as creating the
* persistence manager object
*
* @author Mahmood Khan
* @version 2012-02-20 1.0
*
*/
public class Persistence {
    
    /** The JDOPersistenceManagerFactory object */
    private static JDOPersistenceManagerFactory pmf = null;
    
    /** The PersistenceManager object used for manipulation of database */
    private static PersistenceManager pm = null;
    
    /** The logger object used to log messages */
    private static final Logger logger = AppLogger.getAppLogger(Persistence.class.getName());
    
    /** Part of the implementation of the Singleton design pattern */
    private static Persistence instance = null;
    
    private static boolean doesDbExist;
    
    /** Used to hold errors */
    private String errMsg;
    
    /**
     * Constructor
     */
    private Persistence(){
        if (isAssetFolder() == false){
            if (createAssetFolder() == false) {
                return;
            }
        }
        doesDbExist = doesDatabaseExist();
        try {
            pmf = (JDOPersistenceManagerFactory) JDOHelper.getPersistenceManagerFactory(getDatanucleusPropertiesFile());
            pm = pmf.getPersistenceManager();
        } catch (Exception e) {
            errMsg = "Failed to create PersistenceManagerFactory. ";
            Util.showError(null, errMsg + e.getMessage(), "Exception");
        }
        if (doesDbExist == false) {
            createDatabase();            
        }
    }
    
    /**
     * The Singleton design patter implementation
     * @return
     *      Returns the same instance to any other class that is
     *      requesting an instance of this class
     */
    public static Persistence getInstance(){
        if (instance == null) {
            instance = new Persistence();
        }
        return instance;
    }
    
    
    public PersistenceManager getPm(){
        if (pm == null || pm.isClosed()) {
            logger.log(Level.INFO, "Getting PersistenceManager from PersistenceManagerFactory object");
            pm = pmf.getPersistenceManager();
        }
        return pm;
    }
    
    private boolean isAssetFolder(){
        File assetFolder = new File(Globals.APP_ROOT);
        if (assetFolder.exists()) {
            return true;
        }
        return false;
    }
    
    private boolean doesDatabaseExist(){
        File assetDb = new File(Globals.DB_PATH_EXT );
        return assetDb.exists();
    }
    
    /**
     * Creates the asset folder in the user's home directory
     * @return returns false if the folder already exist or 
     *          the application failed to create a folder.
     *          Otherwise, it returns true if the folder is created
     */
    private boolean createAssetFolder(){
        if (isAssetFolder() == true){
            return false;
        }
        logger.log(Level.INFO, "Creating  " + Globals.APP_ROOT);
        File assetFolder = new File(Globals.APP_ROOT);
        if (assetFolder.mkdir() == false){
            errMsg = "Failed to created the asset-folder in " + Globals.APP_ROOT;
            logger.log(Level.SEVERE, errMsg);
            return false;
        }
        return true;
    }
    
    /**
     * Creates the database from the package.jdo file specified
     * in the org.mercycorps.model package.
     * @param dataNucleusPropertiesFile
     * @return
     *      Returns true if the database is created successfully 
     *      or else false
     */
    private boolean createDatabase() {
        Set<String> classNames = new HashSet<String>();
        classNames.add(Customer.class.getName());
        classNames.add(Order.class.getName());
        classNames.add(Product.class.getName());
        classNames.add(ProductOrder.class.getName());
        classNames.add(Supplier.class.getName());
        try {
            Properties properties = new Properties();
            ((SchemaAwareStoreManager)pmf.getNucleusContext().getStoreManager()).createSchema(classNames, properties);
        } catch (Exception e) {
            errMsg = "Failed to create database schema.";
            logger.log(Level.SEVERE, errMsg + e.getMessage());
            return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        Persistence.getInstance();
        System.out.println("done");
        System.exit(0);
    }
    
    /**
     * Returns the datanucleus.properties file, if it does not exist,
     * it will be written and then return it.
     * @return 
     *      It returns the datanucleus.properties file
     */
    private File getDatanucleusPropertiesFile() {
        String content = null;
        File datanucleusPropertiesFile = new File(Globals.DATNUCLEUS_PROPERTIES);
        if (datanucleusPropertiesFile.exists()){
            return datanucleusPropertiesFile;
        }
        String dbPath = Globals.DB_PATH.replace("\\", "/");
        content = "javax.jdo.PersistenceManagerFactoryClass=org.datanucleus.api.jdo.JDOPersistenceManagerFactory\n"
                + "javax.jdo.option.ConnectionDriverName=org.h2.Driver\n"
                + "javax.jdo.option.ConnectionURL=jdbc:h2:" + dbPath + "\n"
                + "javax.jdo.option.ConnectionUserName=sa\n"
                + "javax.jdo.option.ConnectionPassword=mercy97204\n"
                + "#schema creation\n"
                + "#javax.jdo.mapping.Catalog=PUBLIC\n"
                + "javax.jdo.option.Mapping=h2\n"
                + "datanucleus.autoCreateSchema=true\n"
                + "datanucleus.autoCreateTables=false\n"
                + "datanucleus.autoCreateColumns=false\n"
                + "datanucleus.autoCreateConstraints=false\n"
                + "#schema validation\n"
                + "datanucleus.validateTables=true\n"
                + "datanucleus.validateColumns=false\n"
                + "datanucleus.validateConstraints=false\n"
                + "datanucleus.rdbms.initializeColumnInfo=None";
        try {
            Util.writeTextFile(datanucleusPropertiesFile.getAbsolutePath(), content, true);
        } catch (IOException e) {
            errMsg = "Failed to write datanucleus.properties file. ";
            Util.showError( null, errMsg, "IOException");
            logger.log(Level.SEVERE, errMsg + e.getMessage());
        }
        return datanucleusPropertiesFile;
    }
}
