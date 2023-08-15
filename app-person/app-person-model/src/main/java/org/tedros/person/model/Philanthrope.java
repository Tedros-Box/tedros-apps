/**
 * 
 */
package org.tedros.person.model;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.person.domain.DomainSchema;
import org.tedros.person.domain.DomainTables;

import com.fasterxml.jackson.annotation.JsonClassDescription;


/**
 * @author Davis Gordon
 *
 */
@Entity
@Cacheable(false)
@Table(name = DomainTables.philanthrope, schema = DomainSchema.schema)
@DiscriminatorValue("PHLNTHRP")
@JsonClassDescription("A Philanthrope")
public final class Philanthrope extends NaturalPerson {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8922445186954429983L;

	@Override
	public String getDiscriminatorDesc() {
		return "#{label.philanthrope}";
	}

}
