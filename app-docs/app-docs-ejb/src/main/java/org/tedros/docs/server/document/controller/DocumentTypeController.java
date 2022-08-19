/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.docs.server.document.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.docs.domain.DomainApp;
import org.tedros.docs.ejb.controller.IDocumentTypeController;
import org.tedros.docs.model.DocumentType;
import org.tedros.docs.server.base.service.TDocsService;

import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;
import org.tedros.server.service.ITEjbService;

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
