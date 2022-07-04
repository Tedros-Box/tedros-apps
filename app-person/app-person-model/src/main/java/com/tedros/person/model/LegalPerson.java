/**
 * 
 */
package com.tedros.person.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tedros.person.domain.DomainSchema;
import com.tedros.person.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.legal_person, schema = DomainSchema.schema)
@DiscriminatorValue("L")
public class LegalPerson extends Person {

	private static final long serialVersionUID = 2331186566521960594L;

	@Column(length=120)
	private String otherName;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="legal_type_id")
	private LegalType type;
	
	@Column()
	@Temporal(TemporalType.DATE)
	private Date startActivities;

	@Column()
	@Temporal(TemporalType.DATE)
	private Date endActivities;
	
	@OneToMany(mappedBy="employer", 
			cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public Set<Functionary> staff;

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public LegalType getType() {
		return type;
	}

	public void setType(LegalType type) {
		this.type = type;
	}

	public Set<Functionary> getStaff() {
		return staff;
	}

	public void setStaff(Set<Functionary> staff) {
		this.staff = staff;
	}

	public Date getStartActivities() {
		return startActivities;
	}

	public void setStartActivities(Date startActivities) {
		this.startActivities = startActivities;
	}

	public Date getEndActivities() {
		return endActivities;
	}

	public void setEndActivities(Date endActivities) {
		this.endActivities = endActivities;
	}
	
	
	
}
