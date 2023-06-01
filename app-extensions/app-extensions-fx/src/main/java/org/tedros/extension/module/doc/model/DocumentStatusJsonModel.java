/**
 * 
 */
package org.tedros.extension.module.doc.model;

import org.tedros.extension.model.DocumentStatus;
import org.tedros.server.model.TJsonModel;

/**
 * @author Davis Gordon
 *
 */
public class DocumentStatusJsonModel extends TJsonModel<DocumentStatus> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7223685712737943986L;

	public DocumentStatusJsonModel() {
		super.addData(new DocumentStatus());
	}

	@Override
	public Class<DocumentStatus> getModelType() {
		return DocumentStatus.class;
	}

}
