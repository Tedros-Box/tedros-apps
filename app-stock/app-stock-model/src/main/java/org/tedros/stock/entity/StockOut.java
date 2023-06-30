/**
 * 
 */
package org.tedros.stock.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.stock.domain.DomainSchema;
import org.tedros.stock.domain.DomainTables;

import com.fasterxml.jackson.annotation.JsonClassDescription;

/**
 * @author Davis Gordon
 *
 */
@Entity
@Table(name = DomainTables.stock_out, schema = DomainSchema.schema)
@DiscriminatorValue("OUT")
@JsonClassDescription("Product stock output event")
public class StockOut extends StockEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7868020953892550318L;
}