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
@Table(name = DomainTables.voluntary_type, schema = DomainSchema.schema)
@DiscriminatorValue("VLNTRYTYP")
public class VoluntaryType extends NaturalType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6750740847813670470L;


}
