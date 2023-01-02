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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;

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
	
	@ManyToOne(fetch=FetchType.EAGER)
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

	public LegalType getType() {
		return type;
	}

	public void setType(LegalType type) {
		this.type = type;
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
	
	
	
}
