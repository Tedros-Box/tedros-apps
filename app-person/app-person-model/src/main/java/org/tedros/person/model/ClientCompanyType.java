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
@Table(name = DomainTables.clicompany_type, schema = DomainSchema.schema)
@DiscriminatorValue("CLNTCMPNYTYP")
public class ClientCompanyType extends LegalType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5001557168053002383L;


}
