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
@Table(name = DomainTables.clicompany_status, schema = DomainSchema.schema)
@DiscriminatorValue("CLNTCMPNYSTTS")
public class ClientCompanyStatus extends LegalStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3250162002978513620L;


}
