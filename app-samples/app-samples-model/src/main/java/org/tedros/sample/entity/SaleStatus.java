/**
 * 
 */
package org.tedros.sample.entity;

import org.tedros.sample.domain.DomainSchema;
import org.tedros.sample.domain.DomainTables;
import org.tedros.server.annotation.TModelInfo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Davis
 *
 */
@Entity
@TModelInfo("#{model.info.sale.status}")
@Table(name=DomainTables.sale_status, schema=DomainSchema.schema)
@DiscriminatorValue("sale_status")
public class SaleStatus extends GenericDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3213899935179470933L;


}
