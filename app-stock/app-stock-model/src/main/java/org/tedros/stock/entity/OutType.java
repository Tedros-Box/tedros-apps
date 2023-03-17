/**
 * 
 */
package org.tedros.stock.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.server.entity.TVersionEntity;
import org.tedros.stock.domain.DomainSchema;
import org.tedros.stock.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.out_type, schema = DomainSchema.schema)
public class OutType extends TVersionEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7550169881094984766L;

	@Column(length=40, nullable = false)
	private String name;
	
	@Column(length=200)
	private String description;
	
	
	/**
	 * 
	 */
	public OutType() {
	}

	/**
	 * @param id
	 * @param versionNum
	 * @param lastUpdate
	 * @param insertDate
	 */
	public OutType(Long id, Integer versionNum, Date lastUpdate, Date insertDate) {
		super(id, versionNum, lastUpdate, insertDate);
		// TODO Auto-generated constructor stub
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof OutType))
			return false;
		if (super.equals(obj))
			return true;
		OutType other = (OutType) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return (name != null ? name: "");
	}

}
