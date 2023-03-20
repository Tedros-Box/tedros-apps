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
@Table(name = DomainTables.voluntary_status, schema = DomainSchema.schema)
@DiscriminatorValue("VLNTRYSTTS")
public class VoluntaryStatus extends PersonStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4257103036419644647L;


}
