/**
 * 
 */
package com.tedros.docs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tedros.docs.domain.DomainSchema;
import com.tedros.docs.domain.DomainTables;
import com.tedros.ejb.base.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.document_state, schema = DomainSchema.schema)
public class DocumentState extends TEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5506983771656816156L;

	@Column(length=60, nullable = false)
	private String name;
	
	@Column(length=250)
	private String description;
	
	
	public DocumentState() {
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
