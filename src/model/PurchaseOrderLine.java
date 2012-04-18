package model;

import java.sql.Timestamp;
import java.util.Date;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.ForeignKeyAction;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(objectIdClass=ProductOrderCompositeIdKey.class)
public class PurchaseOrderLine extends AbstractModel{

	/** The product that this order is for */
	@PrimaryKey
	@Column(name="productId")
	@ForeignKey(name="fk_productId_in_ProductOrder", deleteAction=ForeignKeyAction.CASCADE, updateAction=ForeignKeyAction.CASCADE)
	private Product product;
	
	/** The order that contains this orderline*/
	@PrimaryKey
	@Column(name="purchaseOrderId")
	@ForeignKey(name="fk_purchaseOrderId_in_ProductOrder", deleteAction=ForeignKeyAction.CASCADE, updateAction=ForeignKeyAction.CASCADE)
	private PurchaseOrder purchaseOrder;
	
	/** The quantity sold */
	@Persistent
	@Index(name="index_for_quantity_in_ProductOrder")
	private Double quantity;
	
	/** The price at which this item is sold */
	@Persistent
	@Index(name="index_for_salePrice_in_ProductOrder")
	private Double salePrice;
	
	/** Sales costs may include discounts, labor, packaging, and other costs */
	@Persistent
	@Index(name="index_for_totalSaleCost_in_ProductOrder")
	private Double totalSaleCost;
	
	/** The data on which this product was delivered */
	@Persistent
	private Date dateProductDelivered;
	
	@Persistent
	private Timestamp dateCreated;
	
	@Persistent
	private Timestamp dateModified;
	
	public PurchaseOrderLine(){
		super();
	}
	
	public PurchaseOrderLine(Product product, PurchaseOrder purchaseOrder, Double quantity,
			Double salePrice, Double totalSaleCost, Date dateProductDelivered,
			Timestamp dateCreated, Timestamp dateModified) {
		super();
		this.product = product;
		this.purchaseOrder = purchaseOrder;
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

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder order) {
		this.purchaseOrder = order;
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
