/**
 * 
 */
package org.tedros.person.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.legal_person, schema = DomainSchema.schema)
@DiscriminatorValue("LGL_PRSN")
public class LegalPerson extends Person {

	private static final long serialVersionUID = 2331186566521960594L;

	@Column(length=120)
	private String otherName;
	
	@Column()
	@Temporal(TemporalType.DATE)
	private Date startActivities;

	@Column()
	@Temporal(TemporalType.DATE)
	private Date endActivities;
	
	@JsonIgnore
	@OneToMany(mappedBy="legalPerson", 
			cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public Set<Employee> staff;
	
	public void addEmployee(Employee e) {
		if(staff==null)
			staff = new HashSet<>();
		if(!staff.contains(e))
			staff.add(e);
	}
	
	public void removeEmployee(Employee e) {
		if(staff==null)
			return;
		if(staff.contains(e))
			staff.remove(e);
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	
	public Set<Employee> getStaff() {
		return staff;
	}

	public void setStaff(Set<Employee> staff) {
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

	@Override
	public String getDiscriminatorDesc() {
		return "#{label.legal.person}";
	}

	@Override
	public String toString() {
		return (getName() != null ? getName() : "");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((endActivities == null) ? 0 : endActivities.hashCode());
		result = prime * result + ((otherName == null) ? 0 : otherName.hashCode());
		//result = prime * result + ((staff == null) ? 0 : staff.hashCode());
		result = prime * result + ((startActivities == null) ? 0 : startActivities.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof LegalPerson))
			return false;
		LegalPerson other = (LegalPerson) obj;
		if (endActivities == null) {
			if (other.endActivities != null)
				return false;
		} else if (!endActivities.equals(other.endActivities))
			return false;
		if (otherName == null) {
			if (other.otherName != null)
				return false;
		} else if (!otherName.equals(other.otherName))
			return false;
		if (startActivities == null) {
			if (other.startActivities != null)
				return false;
		} else if (!startActivities.equals(other.startActivities))
			return false;
		return true;
	}

}
