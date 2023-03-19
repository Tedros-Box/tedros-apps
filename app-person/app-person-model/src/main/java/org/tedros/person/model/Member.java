/**
 * 
 */
package org.tedros.person.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;
import org.tedros.person.model.NaturalPerson;


/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.member, schema = DomainSchema.schema)
@DiscriminatorValue("MMBR")
public final class Member extends NaturalPerson {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8922445186954429983L;

	@Override
	public String getDiscriminatorDesc() {
		return "#{label.member}";
	}

}
