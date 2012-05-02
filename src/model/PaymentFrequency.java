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
public class PaymentFrequency extends AbstractModel{

    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    @PrimaryKey
	private long id;
    
    @Column(name="frequency", jdbcType="VARCHAR", length=3, allowsNull="false")
	@Index(name="index_for_frequency_in_PaymentFrequency", unique="true")
    private String frequency;
    
    @Column(name="description", jdbcType="VARCHAR", allowsNull="false")
	@Index(name="index_for_description_in_PaymentFrequency", unique="true")
    private String description;
    
	@Persistent
	private Timestamp dateCreated;
	
	@Persistent
	private Timestamp dateModified;

	public PaymentFrequency(long id, String frequency, String description,
			Timestamp dateCreated, Timestamp dateModified) {
		super();
		this.id = id;
		this.frequency = frequency;
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

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
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
