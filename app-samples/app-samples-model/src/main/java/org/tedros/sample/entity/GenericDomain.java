/**
 * 
 */
package org.tedros.sample.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.tedros.sample.domain.DomainSchema;
import org.tedros.sample.domain.DomainTables;
import org.tedros.server.annotation.TField;
import org.tedros.server.entity.TVersionEntity;

/**
 * @author Davis
 *
 */
@Entity
@Table(name=DomainTables.generic_domain, schema=DomainSchema.schema)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE", discriminatorType=DiscriminatorType.STRING, length=15)
@DiscriminatorValue("domain")
public class GenericDomain extends TVersionEntity {

	private static final long serialVersionUID = -8008690210025662586L;

	@Column(length=60, nullable = false)
	@TField(label = "#{label.name}", 
	maxLength=60, required=true)
	private String name;
	
	@Column(length=250)
	@TField(label = "#{label.description}", 
	maxLength=250)
	private String description;

	@Column(length=15)
	private String dType;

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

	/**
	 * @return the dType
	 */
	public String getdType() {
		return dType;
	}

	/**
	 * @param dType the dType to set
	 */
	public void setdType(String dType) {
		this.dType = dType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dType == null) ? 0 : dType.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof GenericDomain))
			return false;
		GenericDomain other = (GenericDomain) obj;
		if (dType == null) {
			if (other.dType != null)
				return false;
		} else if (!dType.equals(other.dType))
			return false;
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
	
	
	
}
