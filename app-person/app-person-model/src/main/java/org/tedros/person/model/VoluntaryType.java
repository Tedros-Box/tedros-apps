/**
 * 
 */
package org.tedros.person.model;

import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.voluntary_type, schema = DomainSchema.schema)
@DiscriminatorValue("VLNTRYTYP")
public class VoluntaryType extends NaturalType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6750740847813670470L;


}
