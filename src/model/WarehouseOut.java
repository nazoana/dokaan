package model;

import java.sql.Timestamp;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.ForeignKeyAction;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-04-30
 *
 */
@PersistenceCapable(identityType=IdentityType.DATASTORE)
@DatastoreIdentity(strategy=IdGeneratorStrategy.INCREMENT)
public class WarehouseOut extends AbstractModel{

    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    @PrimaryKey
	private long id;
    
	@Persistent
	@Column(name="productId")
	@ForeignKey(name="fk_productId_in_WarehouseOut", deleteAction=ForeignKeyAction.CASCADE, updateAction=ForeignKeyAction.CASCADE)
	private Product product;
	
	@Persistent
	@Column(name="warehouseId")
	@ForeignKey(name="fk_warehouseId_in_WarehouseOut", deleteAction=ForeignKeyAction.CASCADE, updateAction=ForeignKeyAction.CASCADE)
	private Warehouse warehouse;
	
	@Persistent
	@Column(name="orderToSupplierId")
	@ForeignKey(name="fk_orderToSupplierId_in_WarehouseOut", deleteAction=ForeignKeyAction.CASCADE, updateAction=ForeignKeyAction.CASCADE)
	private OrderToSupplier orderToSupplier;
	
	@Persistent
	@Column(name="unit", jdbcType="VARCHAR", length=20)
	private String unit;
	
	@Persistent
	@Index(name="index_for_quantity_in_WarehouseOut")
	private Double quantity;
	
	@Persistent
	@Column(name="notes", jdbcType="VARCHAR", length=254)	
	private String notes;
	
	@Persistent
	private Timestamp dateCreated;
	
	@Persistent
	private Timestamp dateModified;

	public WarehouseOut(long id, Product product, Warehouse warehouse, 
			OrderToSupplier orderToSupplierId,
			String unit, Double quantity,
			String notes, Timestamp dateCreated, Timestamp dateModified) {
		super();
		this.id = id;
		this.product = product;
		this.warehouse = warehouse;
		this.orderToSupplier = orderToSupplierId;
		this.unit = unit;
		this.quantity = quantity;
		this.notes = notes;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}
	
	public Warehouse getWarehouse(){
		return warehouse;
	}
	
	public void setWarehouse(Warehouse warehouse){
		this.warehouse = warehouse;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public OrderToSupplier getOrderToSupplier() {
		return orderToSupplier;
	}
	
	public void setOrderToSupplier(OrderToSupplier orderToSupplier){
		this.orderToSupplier = orderToSupplier;
	}
	
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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
