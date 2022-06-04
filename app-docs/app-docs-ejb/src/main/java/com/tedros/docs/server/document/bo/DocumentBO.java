/**
 * 
 */
package com.tedros.docs.server.document.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.tedros.docs.model.Document;
import com.tedros.docs.server.document.eao.DocumentEAO;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

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
