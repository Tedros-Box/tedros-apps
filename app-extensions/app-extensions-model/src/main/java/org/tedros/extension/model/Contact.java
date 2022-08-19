/**
 * 
 */
package org.tedros.extension.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.tedros.extension.domain.DomainSchema;
import org.tedros.extension.domain.DomainTables;

import org.tedros.server.entity.TEntity;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.contact, schema = DomainSchema.schema)
public class Contact extends TEntity {
	
	private static final long serialVersionUID = -5856982120333629714L;

	@Column(length=60, nullable=false)
	private String name;

	@Column(length=10, nullable = false)
	@Enumerated(EnumType.STRING)
	private ContactType type;
	
	@Column(length=120, nullable = false)
	private String value;
	
	@Column(length=250, nullable = true)
	private String observation;
	
	public static String toStringList(Collection<Contact> contacts) {
		StringBuilder sb = new StringBuilder("");
		if(contacts!=null)
			contacts.forEach(e->{
				if(!"".equals(sb.toString()))
					sb.append(", ");
				sb.append(e.getValue());
			});
		return sb.toString();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((observation == null) ? 0 : observation.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Contact))
			return false;
		Contact other = (Contact) obj;
		if (observation == null) {
			if (other.observation != null)
				return false;
		} else if (!observation.equals(other.observation))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("[%s] %s", type, value);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ContactType getType() {
		return type;
	}

	public void setType(ContactType type) {
		this.type = type;
	}
	

}
