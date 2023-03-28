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
@Table(name = DomainTables.entry_type, schema = DomainSchema.schema)
@DiscriminatorValue("ENTRY")
public class EntryType extends EventType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4968489492101791151L;


}
