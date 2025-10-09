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
@Table(name = DomainTables.member_status, schema = DomainSchema.schema)
@DiscriminatorValue("MMBRSTTS")
public class MemberStatus extends NaturalStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3720869483239988845L;


}
