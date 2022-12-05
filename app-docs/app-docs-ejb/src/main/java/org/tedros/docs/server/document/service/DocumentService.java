/**
 * 
 */
package org.tedros.docs.server.document.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.docs.model.Document;
import org.tedros.docs.server.document.bo.DocumentBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@LocalBean
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class DocumentService extends TEjbService<Document> {

	@Inject
	private DocumentBO bo;
	
	@Override
	public ITGenericBO<Document> getBussinesObject() {
		return bo;
	}

}
