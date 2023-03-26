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
@Table(name = DomainTables.employee_status, schema = DomainSchema.schema)
@DiscriminatorValue("EMPLYSTTS")
public class EmployeeStatus extends NaturalStatus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4491068382903598771L;

}
