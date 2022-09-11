/**
 * 
 */
package org.tedros.person.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;
import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.person_event, schema = DomainSchema.schema)
public class PersonEvent extends TEntity{

	private static final long serialVersionUID = 1805152664539511413L;

	@Column(length=120, nullable = false)
	private String name;
	
	@Column(length=1024)
	private String description;
	
	public PersonEvent() {
		if(super.getInsertDate()==null) {
			super.setInsertDate(new Date());
		}
	}

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

	@Override
	public String toString() {
		return (name != null ?  name : "");
	}

}
