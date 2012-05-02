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

/**
 * 
 * @author Mahmood Khan
 * @version 2012-04-30
 *
 */
@PersistenceCapable(identityType=IdentityType.DATASTORE)
@DatastoreIdentity(strategy=IdGeneratorStrategy.INCREMENT)
public class Warehouse extends AbstractModel{

    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    @PrimaryKey
    private long id;
	
    @Column(name="name", jdbcType="VARCHAR", length=80)
    @Index(name="index_for_name_in_Warehouse")
    private String name;
    
    @Column(name="location", jdbcType="VARCHAR")
    private String location;
    
    @Column(name="contactPerson", jdbcType="VARCHAR", length=80)
    private String contactPerson;
    
    @Column(name="phone", jdbcType="VARCHAR")
    private String phone;
    
    @Column(name="email", jdbcType="VARCHAR")
    private String email;
    
    @Persistent
    private Timestamp dateCreated;
    
    @Persistent
    private Timestamp dateModified;

	public Warehouse(long id, String name, String location,
			String contactPerson, String phone, String email,
			Timestamp dateCreated, Timestamp dateModified) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.contactPerson = contactPerson;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
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
