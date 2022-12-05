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
@Table(name = DomainTables.person_attributes, schema = DomainSchema.schema)
public class PersonAttributes extends TVersionEntity {

	private static final long serialVersionUID = -3295912313615959243L;

	@Column(length=120, nullable = false)
	private String name;
	
	@Column(length=120, nullable = false)
	private String value;
	
	@Column(length=1024)
	private String description;
	
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

	@Override
	public String toString() {
		return  (name != null ?  name : "");
	}
}
