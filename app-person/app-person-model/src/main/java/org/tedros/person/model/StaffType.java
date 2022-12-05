/**
 * 
 */
package org.tedros.person.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.staff_type, schema = DomainSchema.schema)
public class StaffType extends TVersionEntity {

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
