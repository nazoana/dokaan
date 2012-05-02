package model;

import java.sql.Timestamp;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.ForeignKeyAction;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-04-28
 *
 */
@PersistenceCapable(identityType=IdentityType.DATASTORE)
@DatastoreIdentity(strategy=IdGeneratorStrategy.INCREMENT)
public class AccountsReceivable extends AbstractModel{

    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    @PrimaryKey
    private long id;
    
    @Column(name="customerId")
    @ForeignKey(name="fk_customerId_in_AccountsPayable", deleteAction=ForeignKeyAction.CASCADE, updateAction=ForeignKeyAction.CASCADE)
    private Customer customer;

    @Column(name="orderFromCustomerId")
    @ForeignKey(name="fk_orderFromCustomerId_in_AccountsPayable", deleteAction=ForeignKeyAction.CASCADE, updateAction=ForeignKeyAction.CASCADE)
    private OrderFromCustomer orderFromCustomer;
    
    @Column(name="currencyId")
    @ForeignKey(name="fk_currency_in_AccountsPayable", deleteAction=ForeignKeyAction.CASCADE, updateAction=ForeignKeyAction.CASCADE)
    private Currency currency;
    
    @Persistent
    private Timestamp datePaymentReceived;
    
    @Column(name="description", jdbcType="VARCHAR")
    private String description;
    
    @Persistent
    private Timestamp dateCreated;
    
    @Persistent
    private Timestamp dateModified;

    public AccountsReceivable(long id, Customer customer,
            OrderFromCustomer orderFromCustomer, Currency currency,
            Timestamp datePaymentReceived, String description,
            Timestamp dateCreated, Timestamp dateModified) {
        super();
        this.id = id;
        this.customer = customer;
        this.orderFromCustomer = orderFromCustomer;
        this.currency = currency;
        this.datePaymentReceived = datePaymentReceived;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderFromCustomer getOrderFromCustomer() {
        return orderFromCustomer;
    }

    public void setOrderFromCustomer(OrderFromCustomer orderFromCustomer) {
        this.orderFromCustomer = orderFromCustomer;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Timestamp getDatePaymentReceived() {
        return datePaymentReceived;
    }

    public void setDatePaymentReceived(Timestamp datePaymentReceived) {
        this.datePaymentReceived = datePaymentReceived;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getDateModified() {
        return dateModified;
    }

    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
    }
    
    
}
