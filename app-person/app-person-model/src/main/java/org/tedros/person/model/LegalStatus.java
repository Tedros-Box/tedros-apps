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
@Table(name = DomainTables.legal_status, schema = DomainSchema.schema)
@DiscriminatorValue("LGLPRSNSTTS")
public class LegalStatus extends PersonStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7398263682827281734L;

}
