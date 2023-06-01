/**
 * 
 */
package org.tedros.extension.module.doc.model;

import org.tedros.extension.model.DocumentType;
import org.tedros.server.model.TJsonModel;

/**
 * @author Davis Gordon
 *
 */
public class DocumentTypeJsonModel extends TJsonModel<DocumentType> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6057980819109301196L;

	public DocumentTypeJsonModel() {
		super.addData(new DocumentType());
	}

	@Override
	public Class<DocumentType> getModelType() {
		return DocumentType.class;
	}

}
