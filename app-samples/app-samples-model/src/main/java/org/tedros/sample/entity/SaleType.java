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
@TModelInfo("#{model.info.sale.type}")
@Table(name=DomainTables.sale_type, schema=DomainSchema.schema)
@DiscriminatorValue("sale_type")
public class SaleType extends GenericDomain {

	private static final long serialVersionUID = -8008690210025662586L;

	
}
