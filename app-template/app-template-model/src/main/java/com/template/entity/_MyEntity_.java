/**
 * 
 */
package com.template.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tedros.ejb.base.entity.TEntity;
import com.template.domain.DomainSchema;
import com.template.domain.DomainTables;

/**
 * @author myname
 *
 */
@Entity
@Table(name=DomainTables.mytable, schema=DomainSchema.schema)
public class _MyEntity_ extends TEntity {

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
