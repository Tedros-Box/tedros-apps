/**
 * 
 */
package com.tedros.person.model;

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

import com.tedros.person.domain.DomainSchema;
import com.tedros.person.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.employee, schema = DomainSchema.schema)
@DiscriminatorValue("F")
public class Functionary extends NaturalPerson {

	private static final long serialVersionUID = -2752532386208736142L;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_staff_type")
	private StaffType type;
	
	@Column()
	@Temporal(TemporalType.DATE)
	private Date hiringDate;
	
	@Column()
	@Temporal(TemporalType.DATE)
	private Date resignationDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_employer")
	private LegalPerson employer;

	public StaffType getType() {
		return type;
	}

	public void setType(StaffType type) {
		this.type = type;
	}

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
		this.employer = employer;
	}

}
