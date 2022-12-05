/**
 * 
 */
package org.tedros.docs.server.document.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.tedros.docs.model.Document;
import org.tedros.docs.server.document.eao.DocumentEAO;
import org.tedros.server.cdi.bo.TGenericBO;
import org.tedros.server.cdi.eao.ITGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class DocumentBO extends TGenericBO<Document> {

	@Inject
	private DocumentEAO eao;
	
	@Override
	public ITGenericEAO<Document> getEao() {
		return eao;
	}

}
