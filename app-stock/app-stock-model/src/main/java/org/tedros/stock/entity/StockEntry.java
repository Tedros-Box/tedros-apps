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
@Table(name = DomainTables.stock_entry, schema = DomainSchema.schema)
@DiscriminatorValue("ENTRY")
public class StockEntry extends StockEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7730299150782823053L;


}