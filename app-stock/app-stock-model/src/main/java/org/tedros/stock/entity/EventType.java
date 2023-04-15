/**
 * 
 */
package org.tedros.stock.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.tedros.server.entity.TVersionEntity;
import org.tedros.stock.domain.DomainSchema;
import org.tedros.stock.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.event_type, schema = DomainSchema.schema)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE", length=10, 
discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("EVENT")
public class EventType extends TVersionEntity {

	private static final long serialVersionUID = 7550169881094984766L;

	@Column(length=20)
	private String code;
	
	@Column(length=40, nullable = false)
	private String name;
	
	@Column(length=200)
	private String description;
	
	/**
	 * 
	 */
	public EventType() {
	}

	/**
	 * @param id
	 * @param versionNum
	 * @param lastUpdate
	 * @param insertDate
	 */
	public EventType(Long id, Integer versionNum, Date lastUpdate, Date insertDate) {
		super(id, versionNum, lastUpdate, insertDate);
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return (name != null ? name: "");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof EventType))
			return false;
		EventType other = (EventType) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
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
