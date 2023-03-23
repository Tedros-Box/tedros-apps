/**
 * 
 */
package org.tedros.person.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.person_category, schema = DomainSchema.schema)
public class PersonCategory extends TVersionEntity {

	private static final long serialVersionUID = 5195417822033633534L;

	@Column(length=20)
	private String code;
	
	@Column(length=60, nullable = false)
	private String name;
	
	@Column(length=250)
	private String description;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name=DomainTables.personcateg_person, 
	schema=DomainSchema.schema,
	joinColumns=@JoinColumn(name="categ_id"), 
	inverseJoinColumns=@JoinColumn(name="person_id"),
	uniqueConstraints=@UniqueConstraint(name="PersonCategoriestUK", 
	columnNames = { "person_id","categ_id"}))
	private Set<Person> persons;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<Person> getPersons() {
		return persons;
	}

	public void setPersons(Set<Person> persons) {
		this.persons = persons;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((persons == null) ? 0 : persons.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PersonCategory))
			return false;
		if (super.equals(obj))
			return true;
		PersonCategory other = (PersonCategory) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (persons == null) {
			if (other.persons != null)
				return false;
		} else if (!persons.equals(other.persons))
			return false;
		return true;
	}


	
}
