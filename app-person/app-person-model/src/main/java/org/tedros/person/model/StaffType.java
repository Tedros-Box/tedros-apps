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
@Table(name = DomainTables.staff_type, schema = DomainSchema.schema)
@DiscriminatorValue("STFFTYP")
public class StaffType extends NaturalType {

	private static final long serialVersionUID = 1572494036678538055L;

}
