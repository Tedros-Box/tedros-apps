/**
 * 
 */
package com.tedros.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tedros.ejb.base.entity.TEntity;
import com.tedros.services.domain.DomainSchema;
import com.tedros.services.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name=DomainTables.service_type, schema=DomainSchema.schema)
public class ServiceType extends TEntity {

	private static final long serialVersionUID = -8008690210025662586L;

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

	@Override
	public String toString() {
		return  (name != null ?  name : "");
	}
	
	
	
}
