/**
 * 
 */
package org.tedros.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.server.entity.TVersionEntity;
import org.tedros.services.domain.DomainSchema;
import org.tedros.services.domain.DomainTables;

import com.fasterxml.jackson.annotation.JsonClassDescription;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name=DomainTables.service_type, schema=DomainSchema.schema)
@JsonClassDescription("service type")
public class ServiceType extends TVersionEntity {

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
