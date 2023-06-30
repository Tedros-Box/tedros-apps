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
@TModelInfo("#{stck.info.entrytype}")
@Table(name = DomainTables.entry_type, schema = DomainSchema.schema)
@DiscriminatorValue("ENTRY")
@JsonClassDescription("Stock entry type")
public class EntryType extends EventType {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4968489492101791151L;


}
