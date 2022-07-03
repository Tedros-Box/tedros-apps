/**
 * 
 */
package com.tedros.person.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tedros.ejb.base.entity.TEntity;
import com.tedros.person.domain.DomainSchema;
import com.tedros.person.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.person_attributes, schema = DomainSchema.schema)
public class PersonAttributes extends TEntity {

	private static final long serialVersionUID = -3295912313615959243L;

	@Column(length=120, nullable = false)
	private String name;
	
	@Column(length=120, nullable = false)
	private String value;
	
	@Column(length=1024)
	private String description;
	
	@ManyToOne
	@JoinColumn(name="id_person")
	private Person person;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
}
