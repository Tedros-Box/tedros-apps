/**
 * 
 */
package com.tedros.location.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tedros.ejb.base.entity.TEntity;
import com.tedros.location.domain.DomainSchema;
import com.tedros.location.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.streetType, schema = DomainSchema.schema)
public class StreetType extends TEntity {

	private static final long serialVersionUID = 1L;

	@Column(length=30, nullable=false)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
