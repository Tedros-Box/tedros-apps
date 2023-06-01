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
@TModelInfo("#{model.info.document.status}")
@Table(name=DomainTables.document_status, schema=DomainSchema.schema)
@DiscriminatorValue("doc_status")
public class DocumentStatus extends ExtensionDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6796733242070537047L;
	

}
