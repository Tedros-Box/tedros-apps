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
@Table(name = DomainTables.client_company, schema = DomainSchema.schema)
@DiscriminatorValue("CLNTCMPNY")
public final class ClientCompany extends LegalPerson {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8922445186954429983L;

	@Override
	public String getDiscriminatorDesc() {
		return "#{label.client.company}";
	}

}
