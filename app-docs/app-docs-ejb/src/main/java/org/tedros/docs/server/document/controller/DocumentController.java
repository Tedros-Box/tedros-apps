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
import org.tedros.docs.ejb.controller.IDocumentController;
import org.tedros.docs.model.Document;
import org.tedros.docs.server.document.service.DocumentService;

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
@Stateless(name="IDocumentController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.DOCUMENT_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class DocumentController extends TSecureEjbController<Document> implements IDocumentController, ITSecurity {
	
	@EJB
	private DocumentService serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<Document> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
		

}
