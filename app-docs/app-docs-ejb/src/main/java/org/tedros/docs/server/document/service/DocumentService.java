/**
 * 
 */
package com.tedros.docs.server.document.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.docs.model.Document;
import com.tedros.docs.server.document.bo.DocumentBO;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;

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
