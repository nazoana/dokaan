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
import controller.CstmrCtrllr;

@PersistenceCapable(identityType=IdentityType.DATASTORE)
@DatastoreIdentity(strategy=IdGeneratorStrategy.INCREMENT)
public class Customer extends AbstractModel{
	
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	@PrimaryKey
	private int id;
	
	@Persistent
	@Column(name="name", jdbcType="VARCHAR", length=60)
	@Index(name="index_for_name_in_Customer")
	private String name;
	
	@Persistent
	@Column(name="phone", jdbcType="VARCHAR", length=40)
	@Index(name="index_for_phone_in_Customer", unique="true")
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
	
	public Customer(int id, String name, String phone, String address,
			Date dateCreated, Date dateModified) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
	    int oldId = this.id;
		this.id = id;
		firePropertyChange(CstmrCtrllr.ELEMENT_ID_PROPERTY, oldId, id);
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
		firePropertyChange(CstmrCtrllr.ELEMENT_NAME_PROPERTY, oldName, name);
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
		firePropertyChange(CstmrCtrllr.ELEMENT_PHONE_PROPERTY, oldPhone, phone);
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
		firePropertyChange(CstmrCtrllr.ELEMENT_ADDRESS_PROPERTY, oldAddress, address);
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
