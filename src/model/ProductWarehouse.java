package model;

import java.sql.Timestamp;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.ForeignKeyAction;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-05-01 1.0
 *
 */
@PersistenceCapable(objectIdClass=ProductWarehouseCompositeIdKey.class)
public class ProductWarehouse {

	@PrimaryKey
	@Column(name="productId")
	@ForeignKey(name="fk_productId_in_ProductWarehouse", deleteAction=ForeignKeyAction.CASCADE, updateAction=ForeignKeyAction.CASCADE)
	private Product product;
	
	/** The order that contains this orderline*/
	@PrimaryKey
	@Column(name="warehouseId")
	@ForeignKey(name="fk_warehouseId_in_ProductWarehouse", deleteAction=ForeignKeyAction.CASCADE, updateAction=ForeignKeyAction.CASCADE)
	private Warehouse warehouse;
	
	@Persistent
	@Index(name="index_for_unitInStock_in_ProductWarehouse")
	private Double unitsInStock;
	
	@Persistent
	private Timestamp dateCreated;
	
	@Persistent
	private Timestamp dateModified;

	public ProductWarehouse(Product product, Warehouse warehouse,
			Double unitsInStock, Timestamp dateCreated, Timestamp dateModified) {
		super();
		this.product = product;
		this.warehouse = warehouse;
		this.unitsInStock = unitsInStock;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Double getUnitsInStock() {
		return unitsInStock;
	}

	public void setUnitsInStock(Double unitsInStock) {
		this.unitsInStock = unitsInStock;
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
