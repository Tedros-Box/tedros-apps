/**
 * 
 */
package org.tedros.stock.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.server.annotation.TModelInfo;
import org.tedros.stock.domain.DomainSchema;
import org.tedros.stock.domain.DomainTables;

import com.fasterxml.jackson.annotation.JsonClassDescription;

/**
 * @author Davis Gordon
 *
 */
@Entity
@TModelInfo("#{stck.info.outtype}")
@Table(name = DomainTables.out_type, schema = DomainSchema.schema)
@DiscriminatorValue("OUT")
@JsonClassDescription("Stock output type")
public class OutType extends EventType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3447539335515905286L;


}
