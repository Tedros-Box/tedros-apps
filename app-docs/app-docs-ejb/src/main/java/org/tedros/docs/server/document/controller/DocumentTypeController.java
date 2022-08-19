/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.tedros.docs.server.document.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.docs.domain.DomainApp;
import com.tedros.docs.ejb.controller.IDocumentTypeController;
import com.tedros.docs.model.DocumentType;
import com.tedros.docs.server.base.service.TDocsService;
import com.tedros.ejb.base.controller.ITSecurityController;
import com.tedros.ejb.base.controller.TSecureEjbController;
import com.tedros.ejb.base.security.ITSecurity;
import com.tedros.ejb.base.security.TAccessPolicie;
import com.tedros.ejb.base.security.TBeanPolicie;
import com.tedros.ejb.base.security.TBeanSecurity;
import com.tedros.ejb.base.security.TSecurityInterceptor;
import com.tedros.ejb.base.service.ITEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@TSecurityInterceptor
@Stateless(name="IDocumentTypeController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.DOCUMENT_TYPE_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class DocumentTypeController extends TSecureEjbController<DocumentType> implements IDocumentTypeController, ITSecurity {
	
	@EJB
	private TDocsService<DocumentType> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<DocumentType> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
		

}
