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
@Table(name = DomainTables.philanthrope_type, schema = DomainSchema.schema)
@DiscriminatorValue("PHLNTHRPTYP")
public class PhilanthropeType extends PersonType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3451002268930842210L;

}
