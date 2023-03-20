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
@Table(name = DomainTables.clicompany_type, schema = DomainSchema.schema)
@DiscriminatorValue("CLNTCMPNYTYP")
public class ClientCompanyType extends PersonType {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5001557168053002383L;


}
