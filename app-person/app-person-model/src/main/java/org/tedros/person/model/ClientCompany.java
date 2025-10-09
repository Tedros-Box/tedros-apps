/**
 * 
 */
package org.tedros.person.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;

import com.fasterxml.jackson.annotation.JsonClassDescription;


/**
 * @author Davis Gordon
 *
 */
@Entity
@Cacheable(false)
@Table(name = DomainTables.client_company, schema = DomainSchema.schema)
@DiscriminatorValue("CLNTCMPNY")
@JsonClassDescription("A client company")
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
