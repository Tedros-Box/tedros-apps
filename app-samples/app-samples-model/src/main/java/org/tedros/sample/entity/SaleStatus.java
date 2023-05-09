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
@TModelInfo("#{model.info.sale.status}")
@Table(name=DomainTables.sale_status, schema=DomainSchema.schema)
@DiscriminatorValue("sale_status")
public class SaleStatus extends GenericDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3213899935179470933L;


}
