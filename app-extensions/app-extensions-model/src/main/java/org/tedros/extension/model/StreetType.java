/**
 * 
 */
package org.tedros.extension.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

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
