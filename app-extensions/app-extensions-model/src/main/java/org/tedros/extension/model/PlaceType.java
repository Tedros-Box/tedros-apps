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
@TModelInfo("#{model.info.place.type}")
@Table(name = DomainTables.placeType, schema = DomainSchema.schema)
@DiscriminatorValue("place_type")
public class PlaceType extends ExtensionDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 350466797331410692L;

}
