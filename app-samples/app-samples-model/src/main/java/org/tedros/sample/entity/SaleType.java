/**
 * 
 */
package org.tedros.sample.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

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
