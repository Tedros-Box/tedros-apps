/**
 * 
 */
package org.tedros.extension.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.tedros.extension.domain.DomainSchema;
import org.tedros.extension.domain.DomainTables;
import org.tedros.server.annotation.TModelInfo;

/**
 * @author Davis Gordon
 *
 */
@Entity
@TModelInfo("#{model.info.street.type}")
@Table(name = DomainTables.streetType, schema = DomainSchema.schema)
@DiscriminatorValue("street_type")
public class StreetType extends ExtensionDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3233023708817506852L;


}
