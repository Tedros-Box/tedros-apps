/**
 * 
 */
package org.tedros.sample.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.tedros.sample.domain.DomainSchema;
import org.tedros.sample.domain.DomainTables;
import org.tedros.server.annotation.TModelInfo;

/**
 * @author Davis
 *
 */
@Entity
@TModelInfo("#{model.info.order.status}")
@Table(name=DomainTables.order_status, schema=DomainSchema.schema)
@DiscriminatorValue("order_status")
public class OrderStatus extends GenericDomain {

	private static final long serialVersionUID = -8008690210025662586L;

}
