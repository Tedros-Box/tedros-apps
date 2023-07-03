/**
 * 
 */
package org.tedros.person.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;

import com.fasterxml.jackson.annotation.JsonClassDescription;


/**
 * @author Davis Gordon
 *
 */
@Entity
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
