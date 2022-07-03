/**
 * 
 */
package com.tedros.person.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tedros.ejb.base.entity.TEntity;
import com.tedros.person.domain.DomainSchema;
import com.tedros.person.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.juridical_type, schema = DomainSchema.schema)
public class JuridicalType extends TEntity {

	private static final long serialVersionUID = -3295912313615959243L;

	@Column(length=120, nullable = false)
	private String name;
	
	@Column(length=1024)
	private String description;
	

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

	
}
