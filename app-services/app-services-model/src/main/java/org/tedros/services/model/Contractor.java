/**
 * 
 */
package com.tedros.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tedros.ejb.base.entity.TEntity;
import com.tedros.person.model.Person;
import com.tedros.services.domain.DomainSchema;
import com.tedros.services.domain.DomainTables;
import com.tedros.services.domain.Status;

/**
 * @author Davis Gordon
 *
 */

@Entity
@Table(name=DomainTables.contractor, schema=DomainSchema.schema)
public class Contractor extends TEntity {

	private static final long serialVersionUID = -7826491025033676510L;

	@ManyToOne
	@JoinColumn(name="person_id", nullable=false, updatable=false)
	private Person person;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Status status;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
