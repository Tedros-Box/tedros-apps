/**
 * 
 */
package org.tedros.person.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.customer_type, schema = DomainSchema.schema)
@DiscriminatorValue("CSTMRTYP")
public class CustomerType extends NaturalType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3565519448846211793L;

}
