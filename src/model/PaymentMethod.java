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
 * @version 2012-04-28
 *
 */
@PersistenceCapable(identityType=IdentityType.DATASTORE)
@DatastoreIdentity(strategy=IdGeneratorStrategy.INCREMENT)
public class PaymentMethod extends AbstractModel{

    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    @PrimaryKey
	private long id;
    
    @Column(name="method", jdbcType="VARCHAR", allowsNull="false")
	@Index(name="index_for_method_in_PaymentMethod", unique="true")
    private String method;
    
    @Column(name="description", jdbcType="VARCHAR", allowsNull="false")
	@Index(name="index_for_description_in_PaymentMethod", unique="true")
    private String description;
    
	@Persistent
	private Timestamp dateCreated;
	
	@Persistent
	private Timestamp dateModified;

	public PaymentMethod(long id, String method, String description,
			Timestamp dateCreated, Timestamp dateModified) {
		super();
		this.id = id;
		this.method = method;
		this.description = description;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
