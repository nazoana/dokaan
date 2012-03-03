package model;

import java.util.Date;

import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
@DatastoreIdentity(strategy=IdGeneratorStrategy.INCREMENT)
public class Order extends AbstractModel{

	@PrimaryKey
	private int id;
	
	@Persistent
	private Integer billNumber;
	
	@Persistent
	private Date orderDate;
	
	@Persistent
	private Integer totalNumOfProducts;
	
	@Persistent
	private Customer customer;
	
	@Persistent
	private Boolean isOrderCompleted;
	
	@Persistent
	private String notes;
	
	@Persistent
	private Date dateCreated;
	
	@Persistent
	private Date dateModified;
	
	public Order(){
		super();
	}
	
	public Order(int id, Integer billNumber, Date orderDate,
			Integer totalNumOfProducts, Customer customer,
			Boolean isOrderCompleted, String notes,
			Date dateCreated, Date dateModified) {
		super();
		this.id = id;
		this.billNumber = billNumber;
		this.orderDate = orderDate;
		this.totalNumOfProducts = totalNumOfProducts;
		this.customer = customer;
		this.isOrderCompleted = isOrderCompleted;
		this.notes = notes;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(Integer billNumber) {
		this.billNumber = billNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getTotalNumOfProducts() {
		return totalNumOfProducts;
	}

	public void setTotalNumOfProducts(Integer totalNumOfProducts) {
		this.totalNumOfProducts = totalNumOfProducts;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Boolean getIsOrderCompleted() {
		return isOrderCompleted;
	}

	public void setIsOrderCompleted(Boolean isOrderCompleted) {
		this.isOrderCompleted = isOrderCompleted;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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
