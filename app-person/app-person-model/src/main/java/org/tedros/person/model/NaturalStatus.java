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
@Table(name = DomainTables.natural_status, schema = DomainSchema.schema)
@DiscriminatorValue("NTRLPRSNSTTS")
public class NaturalStatus extends PersonStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 332871784836741619L;

}
