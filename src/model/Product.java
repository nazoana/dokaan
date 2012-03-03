package model;

import java.util.Date;

import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
@DatastoreIdentity(strategy=IdGeneratorStrategy.INCREMENT)
public class Product extends AbstractModel{

	@PrimaryKey
	private int id;
	
	@Persistent
	private String description;
	
	@Persistent
	private String unit;
	
	@Persistent
	private Double unitsInStock;
	
	@Persistent
	private Double purchasePrice;
	
	@Persistent
	private Double salePrice;
	
	@Persistent
	private Supplier supplier;
	
	@Persistent
	private Date dateCreated;
	
	@Persistent
	private Date dateModified;
	
	public Product(){
		super();
	}

	public Product(int id, String description, String unit,
			Double unitsInStock, Double purchasePrice, Double salePrice,
			Supplier supplier, Date dateCreated, Date dateModified) {
		super();
		this.id = id;
		this.description = description;
		this.unit = unit;
		this.unitsInStock = unitsInStock;
		this.purchasePrice = purchasePrice;
		this.salePrice = salePrice;
		this.supplier = supplier;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	
}
