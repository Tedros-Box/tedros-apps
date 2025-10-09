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
@Table(name = DomainTables.philanthrope_status, schema = DomainSchema.schema)
@DiscriminatorValue("PHLNTHRPSTTS")
public class PhilanthropeStatus extends NaturalStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4181603982827756350L;

}
