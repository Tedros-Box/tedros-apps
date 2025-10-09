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
@Table(name = DomainTables.natural_type, schema = DomainSchema.schema)
@DiscriminatorValue("NTRLPRSNTYP")
public class NaturalType extends PersonType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7215107534795128719L;

}
