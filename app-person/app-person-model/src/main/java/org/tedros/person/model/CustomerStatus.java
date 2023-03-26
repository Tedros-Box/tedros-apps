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
@Table(name = DomainTables.customer_status, schema = DomainSchema.schema)
@DiscriminatorValue("CSTMRSTTS")
public class CustomerStatus extends NaturalStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1930547549655586916L;

}
