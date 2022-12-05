/**
 * 
 */
package org.tedros.docs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.docs.domain.DomainSchema;
import org.tedros.docs.domain.DomainTables;
import org.tedros.server.entity.TVersionEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.document_type, schema = DomainSchema.schema)
public class DocumentType extends TVersionEntity {
	
	private static final long serialVersionUID = 8489348034424401175L;

	@Column(length=60, nullable = false)
	private String name;
	
	@Column(length=250)
	private String description;
	
	
	public DocumentType() {
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
		return (name != null ? name : "");
	}
	

}
