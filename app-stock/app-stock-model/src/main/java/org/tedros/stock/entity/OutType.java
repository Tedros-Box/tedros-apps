/**
 * 
 */
package org.tedros.stock.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.stock.domain.DomainSchema;
import org.tedros.stock.domain.DomainTables;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.out_type, schema = DomainSchema.schema)
@DiscriminatorValue("OUT")
public class OutType extends EventType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3447539335515905286L;


}
