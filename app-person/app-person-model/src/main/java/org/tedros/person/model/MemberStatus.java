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
@Table(name = DomainTables.member_status, schema = DomainSchema.schema)
@DiscriminatorValue("MMBRSTTS")
public class MemberStatus extends PersonStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3720869483239988845L;


}
