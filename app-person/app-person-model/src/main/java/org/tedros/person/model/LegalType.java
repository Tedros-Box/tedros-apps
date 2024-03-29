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
@Table(name = DomainTables.legal_type, schema = DomainSchema.schema)
@DiscriminatorValue("LGLPRSNTYP")
public class LegalType extends PersonType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1455608288839677755L;


}
