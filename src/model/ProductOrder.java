package model;

import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(objectIdClass=ComposedIdKey.class)
public class ProductOrder extends AbstractModel{

	/** The product that this order is for */
	@PrimaryKey
	private Product product;
	
	/** The order that contains this orderline*/
	@PrimaryKey
	private Order order;
	
	/** The quantity sold */
	@Persistent
	private Double quantity;
	
	/** The price at which this item is sold */
	@Persistent
	private Double salePrice;
	
	/** Sales costs may include discounts, labor, packaging, and other costs */
	@Persistent
	private Double totalSaleCost;
	
	/** The data on which this product was delivered */
	@Persistent
	private Date dateProductDelivered;
	
	@Persistent
	private Date dateCreated;
	
	@Persistent
	private Date dateModified;
	
	public ProductOrder(){
		super();
	}
	
	public ProductOrder(Product product, Order order, Double quantity,
			Double salePrice, Double totalSaleCost, Date dateProductDelivered,
			Date dateCreated, Date dateModified) {
		super();
		this.product = product;
		this.order = order;
		this.quantity = quantity;
		this.salePrice = salePrice;
		this.totalSaleCost = totalSaleCost;
		this.dateProductDelivered = dateProductDelivered;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getSalePrice(){
		return salePrice;
	}
	
	public void setSalePrice(Double salePrice){
		this.salePrice = salePrice;
	}
	
	public Double getTotalSaleCost() {
		return totalSaleCost;
	}

	public void setTotalSaleCost(Double totalSaleCost) {
		this.totalSaleCost = totalSaleCost;
	}

	public Date getDateProductDelivered() {
		return dateProductDelivered;
	}

	public void setDateProductDelivered(Date dateProductDelivered) {
		this.dateProductDelivered = dateProductDelivered;
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
