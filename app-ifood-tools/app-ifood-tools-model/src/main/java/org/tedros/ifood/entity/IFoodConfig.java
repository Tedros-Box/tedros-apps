/**
 * 
 */
package org.tedros.ifood.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.tedros.ifood.domain.DomainSchema;
import org.tedros.ifood.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;

/**
 * @author Davis Dun
 *
 */
@Entity
@Table(name=DomainTables.ifood_config, schema=DomainSchema.schema)
public class IFoodConfig extends TVersionEntity {

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
