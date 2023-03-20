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
@Table(name = DomainTables.clicompany_status, schema = DomainSchema.schema)
@DiscriminatorValue("CLNTCMPNYSTTS")
public class ClientCompanyStatus extends PersonStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3250162002978513620L;


}
