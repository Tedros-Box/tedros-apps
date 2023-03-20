/**
 * 
 */
package org.tedros.person.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.customer_type, schema = DomainSchema.schema)
@DiscriminatorValue("CSTMRTYP")
public class CustomerType extends PersonType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3565519448846211793L;

}
