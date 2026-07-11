/**
 * 
 */
package org.tedros.person.model;

import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;

import com.fasterxml.jackson.annotation.JsonClassDescription;

import jakarta.persistence.Cacheable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


/**
 * @author Davis Gordon
 *
 */
@Entity
@Cacheable(false)
@Table(name = DomainTables.customer, schema = DomainSchema.schema)
@DiscriminatorValue("CSTMR")
@JsonClassDescription("A customer")
public class Customer extends NaturalPerson {

	private static final long serialVersionUID = -4307441287541207752L;

	@Override
	public String getDiscriminatorDesc() {
		return "#{label.customer}";
	}

}
