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
public class AccountsPayable extends AbstractModel{

    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    @PrimaryKey
    private long id;
    
    @Column(name="supplierId")
    @ForeignKey(name="fk_supplierId_in_AccountsReceivable", deleteAction=ForeignKeyAction.CASCADE, updateAction=ForeignKeyAction.CASCADE)
    private Supplier supplier;

    @Column(name="orderToSupplierId")
    @ForeignKey(name="fk_orderToSupplierId_in_AccountsPayable", deleteAction=ForeignKeyAction.CASCADE, updateAction=ForeignKeyAction.CASCADE)
    private OrderToSupplier orderToSupplier;
    
    @Column(name="currencyId")
    @ForeignKey(name="fk_currency_in_AccountsReceivable", deleteAction=ForeignKeyAction.CASCADE, updateAction=ForeignKeyAction.CASCADE)
    private Currency currency;
    
    @Persistent
    private Timestamp datePaymentReceived;
    
    @Column(name="description", jdbcType="VARCHAR")
    private String description;
    
    @Persistent
    private Timestamp dateCreated;
    
    @Persistent
    private Timestamp dateModified;

    public AccountsPayable(long id, Supplier supplier,
            OrderToSupplier order, Currency currency,
            Timestamp datePaymentReceived, String description,
            Timestamp dateCreated, Timestamp dateModified) {
        super();
        this.id = id;
        this.supplier = supplier;
        this.orderToSupplier = order;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public OrderToSupplier getOrderToSupplier() {
        return orderToSupplier;
    }

    public void setOrderToSupplier(OrderToSupplier order) {
        this.orderToSupplier = order;
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
