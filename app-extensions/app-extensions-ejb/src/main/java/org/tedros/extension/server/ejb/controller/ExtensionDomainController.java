/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.tedros.extension.server.ejb.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.extension.domain.DomainApp;
import org.tedros.extension.ejb.controller.IExtensionDomainController;
import org.tedros.extension.model.ExtensionDomain;
import org.tedros.extension.server.ejb.service.TExtensionService;
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
@Stateless(name="IExtensionDomainController")
@TBeanSecurity({
	@TBeanPolicie(id = DomainApp.DOCUMENT_STATUS_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.DOCUMENT_TYPE_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.PLACE_TYPE_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS }),
	@TBeanPolicie(id = DomainApp.STREET_TYPE_FORM_ID, 
	policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })
})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ExtensionDomainController extends TSecureEjbController<ExtensionDomain> 
implements IExtensionDomainController, ITSecurity {
	
	@EJB
	private TExtensionService<ExtensionDomain> serv;
	
	@EJB
	private ITSecurityController securityController;
	
	@Override
	public ITEjbService<ExtensionDomain> getService() {
		return serv;
	}
	
	@Override
	public ITSecurityController getSecurityController() {
		return securityController;
	}
		

}
