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
@Table(name = DomainTables.customer, schema = DomainSchema.schema)
@DiscriminatorValue("C")
public class Customer extends NaturalPerson {

	private static final long serialVersionUID = -2752532386208736142L;

	private String preference;

}
