package model;

import java.util.Date;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import utilities.Validator;
import controller.CustomerController;

@PersistenceCapable(identityType=IdentityType.DATASTORE)
@DatastoreIdentity(strategy=IdGeneratorStrategy.INCREMENT)
public class Customer extends AbstractModel{
	
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	@PrimaryKey
	private long id;
	
	@Persistent
	@Column(name="name", jdbcType="VARCHAR", length=60)
	@Index(name="index_for_name_in_Customer")
	private String name;
	
	@Persistent
	@Column(name="phone", jdbcType="VARCHAR", length=40)
	@Index(name="index_for_phone_in_Customer", unique="false")
	private String phone;
	
	@Persistent
	@Column(name="address", jdbcType="VARCHAR", length=254)
	private String address;
	
	@Persistent
	private Date dateCreated;
	
	@Persistent
	private Date dateModified;

	
	public Customer(){
		super();
	}
	
	public Customer(long id, String name, String phone, String address,
			Date dateCreated, Date dateModified) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
	    long oldId = this.id;
		this.id = id;
		firePropertyChange(CustomerController.ELEMENT_ID_PROPERTY, oldId, id);
	}

	public String getName() {
		return name;
	}

	public boolean setName(String name) {
	    if (Validator.isBlankOrNull(name)){
	        return false;
	    }
	    if (!Validator.isAlphaSpace(name)){
	        return false;
	    }
	    String oldName = this.name;
		this.name = name;
		firePropertyChange(CustomerController.ELEMENT_NAME_PROPERTY, oldName, name);
		return true;
	}

	public String getPhone() {
		return phone;
	}

	public boolean setPhone(String phone) {
	    if (Validator.isBlankOrNull(phone)){
            return false;
        }
	    String oldPhone = this.phone;
		this.phone = phone;
		firePropertyChange(CustomerController.ELEMENT_PHONE_PROPERTY, oldPhone, phone);
		return true;
	}

	public String getAddress() {
		return address;
	}

	public boolean setAddress(String address) {
	    if (Validator.isBlankOrNull(address)){
            return false;
        }
	    String oldAddress = this.address;
		this.address = address;
		firePropertyChange(CustomerController.ELEMENT_ADDRESS_PROPERTY, oldAddress, address);
		return true;
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
