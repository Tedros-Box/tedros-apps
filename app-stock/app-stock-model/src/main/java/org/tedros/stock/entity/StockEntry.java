/**
 * 
 */
package org.tedros.stock.entity;

import jakarta.persistence.Cacheable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.tedros.stock.domain.DomainSchema;
import org.tedros.stock.domain.DomainTables;

import com.fasterxml.jackson.annotation.JsonClassDescription;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Cacheable(false)
@Table(name = DomainTables.stock_entry, schema = DomainSchema.schema)
@DiscriminatorValue("ENTRY")
@JsonClassDescription("Product stock entry event")
public class StockEntry extends StockEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7730299150782823053L;


}