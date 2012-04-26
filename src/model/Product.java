package model;

import java.sql.Timestamp;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType=IdentityType.DATASTORE)
@DatastoreIdentity(strategy=IdGeneratorStrategy.INCREMENT)
public class Product extends AbstractModel{

    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    @PrimaryKey
	private long id;
	
	@Persistent
	@Column(name="description", jdbcType="VARCHAR", length=254)
	@Index(name="index_for_description_in_Product")
	private String description;
	
	@Persistent
	@Column(name="unit", jdbcType="VARCHAR", length=20)
	private String unit;
	
	@Persistent
	@Index(name="index_for_unitInStock_in_Product")
	private Double unitsInStock;
	
	@Persistent
	@Index(name="index_for_purchasePrice_in_Product")
	private Double purchasePrice;
	
	@Persistent
	@Index(name="index_for_salePrice_in_Product")
	private Double salePrice;
	
	/*
	@Persistent
	@Column(name="supplierId")
	@ForeignKey(name="fk_supplierId_in_Product", deleteAction=ForeignKeyAction.CASCADE, updateAction=ForeignKeyAction.CASCADE)
	private Supplier supplier;
	*/
	
	@Persistent
	@Column(name="notes", jdbcType="VARCHAR", length=254)	
	private String notes;
	
	@Persistent
	private Timestamp dateCreated;
	
	@Persistent
	private Timestamp dateModified;
	
	public Product(){
		super();
	}

	public Product(long id, String description, String unit,
			Double unitsInStock, Double purchasePrice, Double salePrice,
			String notes, Timestamp dateCreated, Timestamp dateModified) {
		super();
		this.id = id;
		this.description = description;
		this.unit = unit;
		this.unitsInStock = unitsInStock;
		this.purchasePrice = purchasePrice;
		this.salePrice = salePrice;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getUnitsInStock() {
		return unitsInStock;
	}

	public void setUnitsInStock(Double unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}
	
	public String getNotes(){
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
