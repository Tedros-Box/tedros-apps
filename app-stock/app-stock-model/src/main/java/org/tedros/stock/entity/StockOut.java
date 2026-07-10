/**
 * 
 */
package org.tedros.stock.entity;

import org.tedros.stock.domain.DomainSchema;
import org.tedros.stock.domain.DomainTables;

import com.fasterxml.jackson.annotation.JsonClassDescription;

import jakarta.persistence.Cacheable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Cacheable(false)
@Table(name = DomainTables.stock_out, schema = DomainSchema.schema)
@DiscriminatorValue("OUT")
@JsonClassDescription("Product stock output event")
public class StockOut extends StockEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7868020953892550318L;
}