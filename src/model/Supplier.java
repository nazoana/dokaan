package model;

import java.util.Date;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
@DatastoreIdentity(strategy=IdGeneratorStrategy.IDENTITY)
public class Supplier {
	
	@PrimaryKey
	@Column(allowsNull="false")
	private long id;
	
	@Persistent
	@Column(name="name", jdbcType="VARCHAR", length=60)
	@Index(name="index_for_name_in_Supplier", unique="true")
	private String name;
	
	@Persistent
	@Column(name="address", jdbcType="VARCHAR", length=254)
	private String address;
	
	@Persistent
	@Column(name="phone", jdbcType="VARCHAR", length=40)
	private String phone;
	
	@Persistent
	@Column(name="email", jdbcType="VARCHAR", length=60)
	private String email;
	
	@Persistent
	private Date dateCreated;
	
	@Persistent
	private Date dateModified;
	
	public Supplier(){
		super();
	}
	
	public Supplier(long id, String name, String address, String phone,
			String email, Date dateCreated, Date dateModified) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
