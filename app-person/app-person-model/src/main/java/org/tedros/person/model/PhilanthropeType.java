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
@Table(name = DomainTables.philanthrope_type, schema = DomainSchema.schema)
@DiscriminatorValue("PHLNTHRPTYP")
public class PhilanthropeType extends NaturalType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3451002268930842210L;

}
