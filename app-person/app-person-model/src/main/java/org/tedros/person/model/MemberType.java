/**
 * 
 */
package org.tedros.person.model;

import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.member_type, schema = DomainSchema.schema)
@DiscriminatorValue("MMBRTYP")
public class MemberType extends NaturalType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2104702092468421228L;


}
