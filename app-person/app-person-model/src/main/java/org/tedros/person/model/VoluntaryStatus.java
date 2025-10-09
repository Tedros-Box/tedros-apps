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
@Table(name = DomainTables.voluntary_status, schema = DomainSchema.schema)
@DiscriminatorValue("VLNTRYSTTS")
public class VoluntaryStatus extends NaturalStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4257103036419644647L;


}
