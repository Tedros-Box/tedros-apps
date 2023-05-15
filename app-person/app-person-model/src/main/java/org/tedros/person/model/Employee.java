/**
 * 
 */
package org.tedros.person.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = DomainTables.employee, schema = DomainSchema.schema)
@DiscriminatorValue("EMPLY")
public class Employee extends NaturalPerson {

	private static final long serialVersionUID = -2752532386208736142L;
	
	@Column()
	@Temporal(TemporalType.DATE)
	private Date hiringDate;
	
	@Column()
	@Temporal(TemporalType.DATE)
	private Date resignationDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_employer")
	private LegalPerson employer;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cc")
	private CostCenter costCenter;

	public Date getHiringDate() {
		return hiringDate;
	}

	public void setHiringDate(Date hiringDate) {
		this.hiringDate = hiringDate;
	}

	public Date getResignationDate() {
		return resignationDate;
	}

	public void setResignationDate(Date resignationDate) {
		this.resignationDate = resignationDate;
	}

	public LegalPerson getEmployer() {
		return employer;
	}

	public void setEmployer(LegalPerson employer) {
		if(this.employer!=null)
			this.employer.removeEmployee(this);
		this.employer = employer;
		if(this.employer!=null)
			this.employer.addEmployee(this);
		
	}

	@Override
	public String getDiscriminatorDesc() {
		return "#{label.employee}";
	}

	@Override
	public String toString() {
		return (getType() != null ? getType().getName() + ", " : "")
				+ (employer != null ? employer.getName() + ", " : "")
				+ (getName() != null ?  getName() : "")
				+ (getLastName() != null ?  " " + getLastName() : "");
	}

	/**
	 * @return the costCenter
	 */
	public CostCenter getCostCenter() {
		return costCenter;
	}

	/**
	 * @param costCenter the costCenter to set
	 */
	public void setCostCenter(CostCenter costCenter) {
		this.costCenter = costCenter;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((costCenter == null) ? 0 : costCenter.hashCode());
		result = prime * result + ((employer == null) ? 0 : employer.hashCode());
		result = prime * result + ((hiringDate == null) ? 0 : hiringDate.hashCode());
		result = prime * result + ((resignationDate == null) ? 0 : resignationDate.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (costCenter == null) {
			if (other.costCenter != null)
				return false;
		} else if (!costCenter.equals(other.costCenter))
			return false;
		if (employer == null) {
			if (other.employer != null)
				return false;
		} else if (!employer.equals(other.employer))
			return false;
		if (hiringDate == null) {
			if (other.hiringDate != null)
				return false;
		} else if (!hiringDate.equals(other.hiringDate))
			return false;
		if (resignationDate == null) {
			if (other.resignationDate != null)
				return false;
		} else if (!resignationDate.equals(other.resignationDate))
			return false;
		return true;
	}

}
